package Model;

import java.sql.*;
import java.util.*;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */
// TODO: 12/8/17 Documentation and Indentation, clean it up -Blake
// TODO: 12/8/17 all the dl classes minus this one use the same put,post,delete,fetch methods. Consider making an interface -Blake
public class MySQLDatabase extends Observable {
    private String uri_, driver_, user_, password_, conn_;
    private String userName = null;
    private String role = null;
    private static Connection conn;
    private static String[][] sqlArr;
    private static MySQLDatabase msdb = new MySQLDatabase();

    /**
     * Purpose: Default Constructor
     */
    private MySQLDatabase() {
        uri_ = "jdbc:mysql://localhost/project_tracker?autoReconnect=true&useSSL=false";
        driver_ = "com.mysql.jdbc.Driver";
        user_ = "root";
        password_ = "student";
        conn_ = null;
    }

    /**
     * get the MySQLDatabase instance
     *
     * @return
     */
    public static MySQLDatabase getInstance() {
        return msdb;
    }

    /**
     * Purpose: Parameterized Constructor
     *
     * @param uri_      string
     * @param driver_   String
     * @param user_     String
     * @param password_ String
     */
    public MySQLDatabase(String uri_, String driver_, String user_, String password_) {
        this.uri_ = uri_;
        this.driver_ = driver_;
        this.user_ = user_;
        this.password_ = password_;
    }

    /**
     * @return A string of the logged in UserName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * @return A string of the logged in user role
     */
    public String getRole() {
        return role;
    }


    //*************************************** CONNECT *********************************

    /**
     * Purpose: Connect to the database
     *
     * @return a boolean depending on the success of the connection
     */
    public boolean makeConnection() {
        //load the drivers
        try {
            Class.forName(driver_);
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Cannot load driver: " + driver_);
            return false;
        }
        //connect to db
        try {
            this.conn = DriverManager.getConnection(uri_, user_, password_);
            return true;
        } catch (SQLException sqle) {
            System.out.println("Could not connect to db " + uri_);
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }//end make connection

    //****************************************** CLOSE ***************************************

    /**
     * Purpose: Close the connection to the database
     *
     * @return a boolean depending on the success of closing the connection
     */
    public boolean closeConnection() {
        try {
            conn.close();
            return true;
        } catch (SQLException sqle) {
            System.out.println("Could not close db " + conn);
            return false;
        } catch (NullPointerException npe) {
            System.out.print("Null pointer exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }// end  closeConnection()

    /**
     * Purpose: This method will prepare a SQL Statement
     *
     * @param sql     values String[]
     * @param strvals Statement String
     * @return SQL Statement PreparedStatement
     */
    public PreparedStatement prepare(String sql, ArrayList<String> strvals) {
        PreparedStatement ps = null;
        String preparedStr = sql; //sql string must contain ?
        //strvals list must contain values in order of ?
        try {
            //makeConnection();
            ps = conn.prepareStatement(preparedStr);
            //int j = 1;
            //TODO: same issue as getAllData todo -Blake
            for (int i = 0; i < strvals.size(); i++) {
                //todo: Set to the correct datatype rather than sring every time

                if (Character.isDigit(strvals.get(i).charAt(0)) && strvals.get(i).length() == 1) {
                    ps.setInt(i + 1, Integer.parseInt(strvals.get(i)));
                } else {
                    ps.setString(i + 1, strvals.get(i));
                }
            }
        } catch (SQLException sqle) {
            //System.out.println("Could not connect to db " + uri_);
        }
        return ps;
    }

    /**
     * Purpose: This method execute a SELECT SQL Statement on the table submitted
     *
     * @param sql     Statement String
     * @param strvals values String[]
     * @return String[][] of all the data
     */
    public ArrayList<ArrayList<String>> getData(String sqlCMD, ArrayList<String> vals) {
        ArrayList<ArrayList<String>> dataList = new ArrayList<ArrayList<String>>();
        ArrayList<String> headerRow = new ArrayList<String>();
        PreparedStatement stmt = prepare(sqlCMD, vals);
        try {
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            while (rs.next()) {
                ArrayList<String> row = new ArrayList<String>();
                for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                    row.add(rs.getString(i));
                }
                dataList.add(row);
            }
            for (int i = 1; i <= rsmd.getColumnCount(); i++) {
                headerRow.add(rsmd.getColumnName(i));
            }
            dataList.add(0, headerRow);
            return dataList;
        } catch (SQLException sqle) {
            System.out.println(sqle);
            dataList = null;
            return dataList;
        } catch (NullPointerException npe) {
            System.out.println(npe);
            dataList = null;
            return dataList;
        } catch (Exception E) {
            System.out.println(E);
            dataList = null;
            return dataList;
        }
    } // end getData();

    /**
     * Purpose: This method will update, insert, or delete data
     *
     * @param sql     Statement String
     * @param strvals values String[]
     * @return boolean depending on the success of the update, insert, or delete
     */
    public boolean setData(String sql, ArrayList<String> strvals) {
        boolean flag = true;
        if (executeStmt(sql, strvals) == -1) {
            flag = false;
        } else {
            flag = true;
        }
        return flag;
    }

    /**
     * Purpose: This method will execute a prepared statement
     *
     * @param sql     Statement String
     * @param strvals values String[]
     * @return results affected int
     */
    public int executeStmt(String sql, ArrayList<String> strvals) {
        int rc = 0;
        try {
            PreparedStatement stmt = prepare(sql, strvals);
            rc = stmt.executeUpdate();
        } catch (SQLException sqle) {
        } catch (NullPointerException npe) {
        }
        return rc;
    }//end executeStmt


    /**
     * Uses the parameters passed in from the view. If it is correct then it sets the database variables of the username and role.
     *
     * @param username
     * @param password
     * @return
     */
    public Boolean login(String username, String password) {
        //creating new string array for the username
        ArrayList<String> userNameAL = new ArrayList<String>();
        if (username.isEmpty() || password.isEmpty()) {
            return false;
        }

        try {
            userNameAL.add(username); //setting the string array to the username.
            msdb.makeConnection(); //making a connection
            ArrayList<ArrayList<String>> rs = msdb.getData("SELECT Password, Role FROM user WHERE UserName in (?);", userNameAL); //getting the values from the database

            String dbPassword = null;
            String userRole = null;

            for (ArrayList<String> list : rs) {
                dbPassword = list.get(0);
                userRole = list.get(1);
            }


            //String dbPassword = rs[0][0]; //getting the password
            //String usrRole = rs[1][1]; //getting the user role
            //if the password fields text that was inputed from the user equals the databases password,
            // then set loginSuccess = true, then continue to the next block.
            if (password.equals(dbPassword)) {
                this.userName = username;
                this.role = userRole;
                return true;
            }

        } catch (Exception e) {
            System.out.println("Incorrect username or password");
            return false;
        }
        return false;
    }

    public String getMaxProjectID() {
        try {
            makeConnection();
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("Select Max(ID) from project");
            rs.first();
            return rs.getString("MAX(ID)");
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    /**
     * Returns true if the current user has notifications that they have not yet approved.
     * This checks the NOTIFIED user
     *
     * @param _userID is passed in and will usually be the current user
     * @return
     */
    public Boolean checkUserHasNotifications(String _userID, String type) {
        ArrayList<String> queryVals = new ArrayList<>();
        queryVals.add(_userID);
        queryVals.add(type);
        ArrayList<ArrayList<String>> rs = getData("SELECT * from user_notifications where NotifiedUserName in (?) and NotificationType in (?) and Approved = null", queryVals);
        if (rs.size() == 1) {
            return false;
        }//all it got back was headers
        else if (rs.size() > 1) {
            return true;
        }//got back more than one row means there was data.
        return false;
    }

    /**
     * Will return true if there is request made by the current user that is not marked as yes or no by a faculty / staff
     * This checks the NOTIFIED user
     *
     * @return
     */
    public Boolean checkUserWaitingOnNotifications(String _userID, String type) {
        ArrayList<String> queryVals = new ArrayList<>();
        queryVals.add(_userID);
        queryVals.add(type);
        ArrayList<ArrayList<String>> rs = getData("SELECT * from user_notifications where NotifierUserName in (?) and Approved is null and NotificationType in (?)", queryVals);
        if (rs.size() == 1) {
            return false;
        }//all it got back was headers
        else if (rs.size() > 1) {
            return true;
        }//got back more than one row means there was data.
        return true;
    }

    public Boolean checkProjectApproved(String _projectID) {
        ArrayList<String> projectIDAL = new ArrayList<>(Arrays.asList(_projectID));
        ArrayList<ArrayList<String>> rs = msdb.getData("SELECT ProposalApproved from project where ID in (?)", projectIDAL);

        if (rs.get(1).get(0).equals("1")) {
            return true;
        } else {
            return false;
        }

    }

} // end program