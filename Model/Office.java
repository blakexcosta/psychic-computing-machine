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
      try
      {
         String[] params = { getUserName() };
         resultSet = MySQLDatabase.getData("SELECT * FROM office WHERE UserName = ?", params);
         
         setUserName(resultSet[0][0]);
         setOfficeNumber(resultSet[0][1]);
         
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
         String[] params = { getUserName(), getOfficeNumber (), getUserName() };
         MySQLDatabase.setData("UPDATE office SET UserName = ?, OfficeNumber = ? WHERE UserName = ?", params);
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
         String[] params = { getUserName(), getOfficeNumber() };
         MySQLDatabase.setData("INSERT INTO office (UserName, OfficeNumber) VALUES (?, ?)", params);
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
         MySQLDatabase.setData("DELETE FROM office WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }

}