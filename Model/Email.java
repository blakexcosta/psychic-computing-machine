public class Email
{
   //Attributes
   private String userName;
   private String emailAddress;
   private String emailType;
   
   //Default Constructor
   public Email()
   {
      userName = "unknown";
      emailAddress = "unknown";
      emailType = "unknown";
   }
   
   //Parameterized Constructor - ID
   public Email(String userName)
   {
      this.userName = userName;
      emailAddress = "unknown";
      emailType = "unknown";
   }
   
   //Parameterized Constructor - Everything
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

   //Database Transaction Stubbs
   public boolean fetch()
   {
      try
      {
         String[] params = { getUserName() };
         resultSet = MySQLDatabase.getData("SELECT * FROM email WHERE UserName = ?", params);
         
         setUserName(resultSet[0][0]);
         setEmailAddress(resultSet[0][1]);
         setEmailType(resultSet[0][2]);
         
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
   
   public boolean put()
   {
      try
      {
         String[] params = { getUserName(), getEmailAddress(), getEmailType(), getUserName() };
         MySQLDatabase.setData("UPDATE email SET UserName = ?, EmailAddress = ?, EmailType = ? WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
   
   public boolean post()
   {
      try
      {
         String[] params = { getUserName(), getEmailAddress(), getEmailType() };
         MySQLDatabase.setData("INSERT INTO email (UserName, EmailAddress, EmailType) VALUES (?, ?, ?)", params);
         return true; 
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
   
   public boolean delete()
   {
      try
      {
         String[] params = { getUserName() };
         MySQLDatabase.setData("DELETE FROM email WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }

}