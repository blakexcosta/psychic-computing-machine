package DB_Files;
// import com.sun.corba.se.pept.transport.ConnectionCache;

import Model.MySQLDatabase;

import java.sql.Connection;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

public class Email
{
   //Attributes
   private String userName;
   private String emailAddress;
   private String emailType;
   private String[][] resultSet;
   private MySQLDatabase dbClass = MySQLDatabase.getInstance();
   
   //Default Constructor
   public Email()
   {
      userName = "unknown";
      emailAddress = "unknown";
      emailType = "unknown";
   }
   
   /**
     * Purpose: Parameterized Constructor - ID
     * @param userName String
     */
   public Email(String userName)
   {
      this.userName = userName;
      emailAddress = "unknown";
      emailType = "unknown";
   }
 
   /**
     * Purpose: Parameterized Constructor - Everything
     * @param userName String
     * @param emailAddress String
     * @param emailType String
     */
   public Email(String userName, String emailAddress, String emailType)
   {
      this.userName = userName;
      this.emailAddress = emailAddress;
      this.emailType = emailType;
   }
   
   //Accessors
   public String getUserName() { return userName; }
   public String getEmailAddress() { return emailAddress; }
   public String getEmailType() { return emailType; }
   
   //Mutators
   public void setUserName(String userName) { this.userName = userName; }
   public void setEmailAddress(String emailAdress) { this.emailAddress = emailAddress; }
   public void setEmailType(String emailType) { this.emailType = emailType; }

    /**
     * Purpose: fetches a 2d array of strings from the database, takes a string to determine what table to fetch
     * @param tableName String
     * @return resultSet String[][] return a result set that holds all of the data extracted from the database
     */
   public String[][] fetchAll(String tableName)
   {
   
      try{
         //Connect MySQL:
         dbClass.makeConnection();
      
         resultSet = dbClass.getAllData(tableName);
         
         //Close MySQL:
         dbClass.closeConnection();

      }catch(Exception e){
         e.getMessage();
      }
      return resultSet;
   }//end fetchAll








   /**
     * Purpose: Executes a SELECT SQL Statement on the email table and returns a 2D array of the data retrieved
     * @return boolean depending on the success of the execution
     */
   public boolean fetch()
   {
      try
      {

         String[] params = { getUserName() };
         resultSet = dbClass.getData("SELECT * FROM email WHERE UserName = ?", params);
         
         setUserName(resultSet[0][0]);
         setEmailAddress(resultSet[0][1]);
         setEmailType(resultSet[0][2]);
         
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes an UPDATE SQL Statement on the email table and it will update new values that reflect the attributes
     * @return boolean depending on the success of the execution
     */
   public boolean put()
   {
      try
      {
         dbClass.makeConnection();
         String[] params = { getUserName(), getEmailAddress(), getEmailType(), getUserName() };
         dbClass.setData("UPDATE email SET UserName = ?, EmailAddress = ?, EmailType = ? WHERE UserName = ?", params);
         dbClass.closeConnection();
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes an INSERT SQL Statement on the email table and it will insert new values that reflect the attributes 
     * @return boolean depending on the success of the execution
     */
   public boolean post()
   {
      try
      {
         dbClass.makeConnection();
         String[] params = { getUserName(), getEmailAddress(), getEmailType() };
         dbClass.setData("INSERT INTO email (UserName, EmailAddress, EmailType) VALUES (?, ?, ?)", params);
         dbClass.closeConnection();
         return true; 
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes a DELETE SQL Statement on the email table and will delete the data from the selected ID attribute
     * @return boolean depending on the success of the execution
     */
   public boolean delete()
   {
      try
      {
         dbClass.makeConnection();
         String[] params = { getUserName() };
         dbClass.setData("DELETE FROM email WHERE UserName = ?", params);
         dbClass.closeConnection();
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }

}