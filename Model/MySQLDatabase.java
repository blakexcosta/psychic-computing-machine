package Model;

import View.MasterView;

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
public class MySQLDatabase extends Observable{
    private String uri_, driver_, user_, password_, conn_;
    private String loginUser = null;
    private static Connection conn;
    private static String[][] sqlArr;
    private static String[][] userInfo = null;
    private static String usrRole = null;
    private static MySQLDatabase msdb = new MySQLDatabase();


    /**
     * Purpose: Default Constructor
     */
   private MySQLDatabase() {
      uri_    = "jdbc:mysql://localhost/project_tracker?autoReconnect=true&useSSL=false";
      driver_ = "com.mysql.jdbc.Driver";
      user_   = "root";
      password_ = "student";
      conn_ = null;
    }

    /**
     * get the MySQLDatabase instance
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

    /**
     * Purpose: This method will prepare a SQL Statement
     *
     * @param sql values String[]
     * @param strvals       Statement String
     * @return SQL Statement PreparedStatement
     */
    // TODO: 12/8/17 After thinking more on this, I do not believe we need static instances of the methods, remove the static tag -Blake 
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
      } 
      catch (NullPointerException npe) {
      }
      
      return rc;
   }//end executeStmt

   /**
    * a private setter to set the user info, used as a private
    * helper by login.
    * @param _info
    */
   private static void setUserInfo(String[][] _info) {
      MySQLDatabase.userInfo = _info;
   }

   /**
    * public getter to return user info, it may
    * return null if the login failed, so check to make
    * sure of that. used by the view that needs it.
    * @return
    */
   public String[][] getUserInfo() {
      return userInfo;
   }

   /**
    * setter
    * @param usrRole
    */
   private static void setUserRole(String usrRole) {
      MySQLDatabase.usrRole = usrRole;
   }

   /**
    * get user role
    * @return
    */
   public String getUserRole() {
      return usrRole;
   }

   //get the login information passed.


   public void login(String username, String password){
      //creating new string array for the username
      String[] vals = new String[1];
      boolean loginSuccess = false;
      try {
         vals[0] = username; //setting the string array to the username.
         msdb.makeConnection(); //making a connection
         String[][] rs = msdb.getData("SELECT Password, Role FROM user WHERE UserName in (?);", vals); //getting the values from the database
         String dbPassword = rs[0][0]; //getting the password
         String usrRole = rs[1][1]; //getting the user role
         setUserRole(usrRole); //setting the user role.
         //if the password fields text that was inputed from the user equals the databases password,
         // then set loginSuccess = true, then continue to the next block.
         if (password.equals(dbPassword)) {
            loginSuccess = true;
         }
         if (loginSuccess) {
            setChanged();
            userInfo = msdb.getData("SELECT CONCAT(FirstName,' ', LastName) as 'Name', " +
               "UserName, Department, GraduationDate, major, role, Image FROM user WHERE UserName in (?);", vals);
            setUserInfo(userInfo); //setting the user info, again this functions as a private setter
            //calling setUserInfo, setting the new values.
            notifyObservers();//"StudentView,SomeMessage".split(","));
            new MasterView().setWindow();//set the window
         } else {
            setChanged();
            notifyObservers("Login Unsuccessful.");
         }

      } catch (Exception e) {
         e.printStackTrace();
      }
   }
} // end program