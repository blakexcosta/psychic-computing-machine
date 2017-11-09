public class Phone
{
   //Attributes
   private String userName;
   private String phoneNumber;
   private String phoneType;
   
   //Default Constructor
   public Phone()
   {
      userName = "unknown";
      phoneNumber = "unknown";
      phoneType = "unknown";
   }
   
   //Parameterized Constructor - ID
   public Phone(String userName)
   {
      this.userName = userName;
      phoneNumber = "unknown";
      phoneType = "unknown";
   }
   
   //Parameterized Constructor - ID
   public Phone(String userName, String phoneNumber, String phoneType)
   {
      this.userName = userName;
      this.phoneNumber = phoneNumber;
      this.phoneType = phoneType;
   }
   
   //Accessors
   public String getUserName() { return userName; }
   public String getPhoneNumber() { return phoneNumber; }
   public String getPhoneType() { return phoneType; }
   
   //Mutators
   public void setUserName(String userName) { this.userName = userName; }
   public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
   public void getPhoneType(String phoneType) { this.phoneType = phoneType; }
   
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