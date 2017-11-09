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
      return true;
   }
   
   public boolean put()
   {
      return true;
   }
   
   public boolean post()
   {
      return true;
   }
   
   public boolean delete()
   {
      return true;
   }

}