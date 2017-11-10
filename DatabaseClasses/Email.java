/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */

public class Email
{
   //Attributes
   private String userName;
   private String emailAddress;
   private String emailType;
   MySQLDatabase dbClass = new MySQLDatabase();
   
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
     * Purpose: Executes a SELECT SQL Statement on the email table and returns a 2D array of the data retrieved
     * @return boolean depending on the success of the execution
     */
   public boolean fetch()
   {
      try
      {
         String[] params = { getUserName() };
         String[][] resultSet = dbClass.getData("SELECT * FROM email WHERE UserName = ?", params);
         
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
         String[] params = { getUserName(), getEmailAddress(), getEmailType(), getUserName() };
         dbClass.setData("UPDATE email SET UserName = ?, EmailAddress = ?, EmailType = ? WHERE UserName = ?", params);
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
         String[] params = { getUserName(), getEmailAddress(), getEmailType() };
         dbClass.setData("INSERT INTO email (UserName, EmailAddress, EmailType) VALUES (?, ?, ?)", params);
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
         String[] params = { getUserName() };
         dbClass.setData("DELETE FROM email WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }

}