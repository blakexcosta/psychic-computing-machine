package Model;

import java.sql.*;
import java.util.*;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */
public class MySQLDatabase extends Observable{
    private String uri_, driver_, user_, password_, conn_;
    private String loginUser = null;
    private static Connection conn;
    private static String[][] sqlArr;
    private static MySQLDatabase msdb = new MySQLDatabase();
    /**
     * Purpose: Default Constructor
     */
    public MySQLDatabase() {
      uri_    = "jdbc:mysql://localhost/project_tracker?autoReconnect=true&useSSL=false";
      driver_ = "com.mysql.jdbc.Driver";
      user_   = "root";
      password_ = "student";
      conn_ = null;
      
    }

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
     * gets the login user. set from getData
     * @return
     */
    public String getLoginUser() {
        return this.loginUser;
    }

    /**
     * Purpose: This method execute a SELECT SQL Statement on the table submitted
     *
     * @param tableName name String
     * @return String[][] of all the data
     */
    public String[][] getAllData(String tableName) {
        try {
            //*******************get row count***************
            String sql = "SELECT * FROM " + tableName + ";";
            //String sql = "SELECT * FROM " +
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            System.out.println("\n");
            int rowNum = 0;
            //get row count
            if (rs.last()) {
                rowNum = rs.getRow(); //int representation of the row being selected from the result set
                rs.beforeFirst();
            }
            //************************************************
            ResultSetMetaData rsmd = rs.getMetaData();
            //boolean yesNo = colnames;                           ///NEW
            int columnCount = rsmd.getColumnCount(); //getting columnCount
            String headers[] = new String[columnCount]; //making new String header array based upon the column count
            //new
            for (int i = 1; i <= columnCount; i++) {          //loops through and collects headings and their lengths
                headers[i - 1] = rsmd.getColumnName(i);     //creates the column heading for chart
            }
            while (rs.next()) { //while the resultSet has a next row of data
                sqlArr = new String[rowNum][columnCount]; //sqlArr static attribute = a new String array of the rowNumber and the column count
                for (int i = 0; i < rowNum; i++) { //iterating over the number of rows
                //TODO: edit this line, remove j=1 and make it j=0, adds unneccessary level of confusion with array indexing. -Blake
                    for (int j = 1; j <= columnCount; j++) { //for each row, iterated over the columns
                        if (i == 0) { //if i==0 it must be the header of the row, hence add it to the sqlArr
                            sqlArr[i][j - 1] = headers[j - 1]; //loop through column data from list(?) and populate this row with column headers
                        } else {
                            sqlArr[i][j - 1] = rs.getString(j); //otherwise it's just a normal row of data. set the sql array row
                        }
                    }
                }
            }
            System.out.println("Number of Rows retrieved: " + rowNum);
        } catch (SQLException sqle) {
            System.out.println(sqle);
            //System.out.println("Error in getData(): SQL Statement not valid (?) ");
        } catch (NullPointerException npe) {
            System.out.println(npe);
        }
        loginUser = sqlArr[0][0];
        notifyObservers(this);
        return sqlArr;
    } // end getData();

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

//TODO: Please document this. Ty :) -Blake
//****************************************** descTable() ***************************************
    /**
     * Purpose: Describe the table
     *
     * @param statement SQL Statement String
     */
    public void descTable(String statement) {
        try {
            Statement stmt = conn.createStatement(); //creating a new statement
            //TODO: Why is this select being used here? Why not remove it and just use statement? -Blake
            ResultSet rs = stmt.executeQuery("SELECT " + statement); //executing select query
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            int rowCount = 0;
            String colType = "";
            String colName = "";
            //get row count
            if (rs.last()) {
                rowCount = rs.getRow();
                rs.beforeFirst();
            }
            sqlArr = new String[rowCount][columnCount];
            int row = 0;
            //System.out.println("COLUM COUNT: " + columnCount);
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    sqlArr[row][i - 1] = rs.getString(i);
                }
                row++;
            }

            //*******************************************META DATA***********************************************
            System.out.println("+---------------------+--------------------+");
            System.out.println("| Field               | Type               |");
            System.out.println("+------------------------------------------+");
            for (int i = 1; i <= columnCount; i++) {
                System.out.printf("| %-20s| %19s|\n", rsmd.getColumnName(i), rsmd.getColumnTypeName(i));
            }
            System.out.println("+---------------------+--------------------+\n\n");

            //*******************************************SELECT STATEMENT***********************************************
            //figure out which row has the longest column, scale each row accordingly, cut off at 50(?)--47 and append...
            //get longest column
            String longestColVal[] = new String[columnCount];
            String colHeading[] = new String[columnCount]; //moved to global
            int colLongest[] = new int[columnCount];
            String colHeadingIndex = "";
            String longest = "";
            //String numofStr = "";
            String headings = "";

            //                   **********************Acquire meta data for table******************************
            for (int i = 1; i <= columnCount; i++) {          //loops through and collects headings and their lengths
                colHeading[i - 1] = rsmd.getColumnName(i);     //creates the column heading for chart
                longestColVal[i - 1] = rsmd.getColumnName(i);  //adds column headings to array as default length for columns
            }

            //                   ********************Keeps all column lenth to size:15****************************
            //System.out.println(Arrays.deepToString(sqlArr));
            for (int i = 0; i <= columnCount - 1; i++) {
                //run through array of values from select statement
                //compare lengths from each column && length of header text
                //save value of longest column
                for (int j = 0; j <= rowCount - 1; j++) {
                    if (sqlArr[j][i].length() > 15) {
                        sqlArr[j][i] = sqlArr[j][i].substring(0, 13) + "..";
                    }
                    if ((sqlArr[j][i].length() > longest.length())
                            && (sqlArr[j][i].length()) > colHeading[i].length()) { //0,0
                        longest = sqlArr[j][i];
                        longestColVal[i] = longest;
                    }
                    //trim the length of each column to not exceed over a length of 15
                    if (longestColVal[i].length() > 15) {
                        longestColVal[i] = longestColVal[i].substring(0, 13) + "..";
                    }
                    if (colHeading[i].length() > 15) {
                        colHeading[i] = colHeading[i].substring(0, 13) + "..";
                    }
                }
                longest = "";
                colLongest[i] = longestColVal[i].length();
            }

            //                   ***************************Creates header and label String***********************
            String headerString = "+";
            String headerLabelStr = "";
            for (int i = 0; i <= columnCount - 1; i++) {
                int numDash = colLongest[i] + 2;
                int numSpace = (colLongest[i] - colHeading[i].length()); //compares longest string to column header; finds difference
                headerString += "" + String.join("", Collections.nCopies(numDash, "-")) + "+"; //uses numDash to determine the number of dashes needed for table border
                if (i == 0) {
                    headerLabelStr += "| ";
                }
                headerLabelStr += colHeading[i] + String.join("", Collections.nCopies(numSpace, " ")) + " | "; //creates a string to be used as the header label
            }

            //                   ***************************Prints each row data*********************************
            String rowString = "";
            for (int i = 0; i <= rowCount - 1; i++) {
                if (i == 0) {
                    System.out.println(headerString);            //Header fields added
                    System.out.println(headerLabelStr);
                    System.out.println(headerString);
                }
                for (int j = 0; j <= columnCount - 1; j++) {
                    if (j == 0) {
                        rowString += "| ";
                    }
                    int numSpace = (colLongest[j] - sqlArr[i][j].length());                                     //compares longest column to length of row in data
                    rowString += sqlArr[i][j] + String.join("", Collections.nCopies(numSpace, " ")) + " | ";     //uses difference of longest column and current row data to add spaces
                }
                System.out.println(rowString);
                rowString = "";
                if (i == rowCount - 1) {
                    System.out.println(headerString);
                }
            }
            System.out.println("# rows retrieved: " + rowCount);
        } catch (SQLException sqle) {
            System.out.println("SQL exception");
        } catch (NullPointerException npe) {
            System.out.println("Null pointer exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return sqlArr;
    } // end descTable();

    /**
     * Purpose: This method will prepare a SQL Statement
     *
     * @param sql values String[]
     * @param strvals       Statement String
     * @return SQL Statement PreparedStatement
     */
    public static PreparedStatement prepare(String sql, String[] strvals) {
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

//****************************************** getData() ***************************************
    /**
     * Purpose: This method execute a SELECT SQL Statement on the table submitted
     *
     * @param sql Statement String
     * @return String[][] of all the data
     */
    public static String[][] getData(String sql) {
        try {
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            sqlArr = new String[1][columnCount];
            int row = 0;
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    sqlArr[row][i - 1] = rs.getString(i);
                }
                row++;
            }
        } catch (SQLException sqle) {
            System.out.println("Error in getData(): SQL Statement not valid (?) ");
        } catch (NullPointerException npe) {
            System.out.println("Error in getData() : Null Pointer Exception");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sqlArr;
    } // end getData();

    /**
     * Purpose: This method execute a SELECT SQL Statement on the table submitted
     *
     * @param sql        Statement String
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

//****************************************** setData() ***************************************
    /**
     * Purpose: This method execute a SELECT SQL Statement on the table submitted
     *
     * @param sql Statement String
     * @return Boolean value reflecting the success
     */
    public static boolean setData(String sql) {
        boolean flag;
        try {
            Statement stmt = conn.createStatement();
            int rc = stmt.executeUpdate(sql);
            flag = true;
        } catch (SQLException se) {
            //Handle errors for JDBC
            flag = false;
            se.printStackTrace();
        } catch (NullPointerException npe) {
            flag = false;
            //Handle errors for Class.forName
            System.out.println("Null pointer exceptoin");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return flag;
    }//end setData()

    /**
     * Purpose: This method will update, insert, or delete data
     *
     * @param sql       Statement String
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
     * @param sql       Statement String
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

    @Override
    public synchronized void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
    }

    @Override
    public synchronized boolean hasChanged() {
        return super.hasChanged();
    }

    /**
     * notifys
     */
    @Override
    public void notifyObservers() {
        setChanged();
        super.notifyObservers();
    }
} // end program