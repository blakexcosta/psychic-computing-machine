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
// TODO: 12/8/17 Implement the DataLayer table classes, and determine where to place them in respect to this class -Blake 
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
            //System.out.println("Loaded Driver");
        } catch (ClassNotFoundException cnfe) {
            System.out.println("Cannot load driver: " + driver_);
            return false;
        }
        //connect to db
        try {
            this.conn = DriverManager.getConnection(uri_, user_, password_);
            //System.out.println("Connected to database");
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
            //System.out.println("DB closed");
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
    // TODO: 12/8/17 After thinking more on this, I do not believe we need static instances of the methods, remove the static tag -Blake 
    public PreparedStatement prepare(String sql, String[] strvals) {
        PreparedStatement ps = null;
        String preparedStr = sql; //sql string must contain ?
        //strvals list must contain values in order of ?
        try {
            //makeConnection();
            ps = conn.prepareStatement(preparedStr);
            //int j = 1;
            //TODO: same issue as getAllData todo -Blake
            for (int i = 1; i <= strvals.length; i++) {
                for (int j = 0; j <= strvals.length - 1; j++) {
                    ps.setString(i, strvals[j]);
                    //System.out.println("i: " + i + " j: " + j);
                }
            }
            //System.out.println(ps);
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
    public String[][] getData(String sql, String[] strvals) {
        try {
            PreparedStatement stmt = prepare(sql, strvals);
            ResultSet rs = stmt.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            //boolean yesNo = colnames;                           ///NEW
            int columnCount = rsmd.getColumnCount();
            String headers[] = new String[columnCount];
            //new
            for (int i = 1; i <= columnCount; i++) {          //loops through and collects headings and their lengths
                headers[i - 1] = rsmd.getColumnName(i);     //creates the column heading for chart
            }
            while (rs.next()) {
                sqlArr = new String[headers.length][columnCount];
                for (int i = 0; i < 2; i++) {
                    for (int j = 1; j <= columnCount; j++) {
                        if (i == 0) {
                            sqlArr[i][j - 1] = headers[j - 1]; //loop through column data from list(?) and populate this row with column headers
                        } else {
                            sqlArr[i][j - 1] = rs.getString(j);
                        }
                    }
                }
            }
        } catch (SQLException sqle) {
            System.out.println(sqle);
            //System.out.println("Error in getData(): SQL Statement not valid (?) ");
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        return sqlArr;
    } // end getData();

    /**
     * Purpose: This method will update, insert, or delete data
     *
     * @param sql     Statement String
     * @param strvals values String[]
     * @return boolean depending on the success of the update, insert, or delete
     */
    public boolean setData(String sql, String[] strvals) {
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
    public int executeStmt(String sql, String[] strvals) {
        int rc = 0;
        try {
            PreparedStatement stmt = prepare(sql, strvals);
            //ResultSet rs = stmt.executeUpdate();
            rc = stmt.executeUpdate();
        } catch (SQLException sqle) {
        } catch (NullPointerException npe) {
        }
        return rc;
    }//end executeStmt


    /**
     * Uses the parameters passed in from the view. If it is correct then it sets the database variables of the username and role.
     * @param username
     * @param password
     * @return
     */
    public Boolean login(String username, String password) {
        //creating new string array for the username
        String[] userNameAL = new String[1];
        try {
            userNameAL[0] = username; //setting the string array to the username.
            msdb.makeConnection(); //making a connection
            String[][] rs = msdb.getData("SELECT Password, Role FROM user WHERE UserName in (?);", userNameAL); //getting the values from the database
            String dbPassword = rs[0][0]; //getting the password
            String usrRole = rs[1][1]; //getting the user role
            //if the password fields text that was inputed from the user equals the databases password,
            // then set loginSuccess = true, then continue to the next block.
            if (password.equals(dbPassword)) {
                this.userName = username;
                this.role = usrRole;
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }
} // end program