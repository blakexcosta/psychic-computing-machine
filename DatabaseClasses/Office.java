public class Office
{
   //Attributes
   private String userName;
   private String officeNumber;
   
   //Default Constructor
   public Office()
   {
      userName = "unknown";
      officeNumber = "unknown";
   }
   
   //Parameterized Constructor - ID
   public Office(String userName)
   {
      this.userName = userName;
      officeNumber = "unknown";
   }
   
   //Parameterized Constructor - Everything
   public Office(String userName, String officeNumber)
   {
      this.userName = userName;
      this.officeNumber = officeNumber;
   }
   
   //Accessors
   public String getUserName() { return userName; }
   public String getOfficeNumber() { return officeNumber; }
   
   //Mutators
   public void setUserName(String userName) { this.userName = userName; }
   public void setOfficeNumber(String officeNumber) { this.officeNumber = officeNumber; }
   
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