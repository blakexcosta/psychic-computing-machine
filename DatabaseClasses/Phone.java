/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */

public class Phone
{
   //Attributes
   private String userName;
   private String phoneNumber;
   private String phoneType;

   /**
    * Purpose: default constructor
    */
   public Phone()
   {
      userName = "unknown";
      phoneNumber = "unknown";
      phoneType = "unknown";
   }

   /**
    * Purpose: parameterized constructor
    * @param userName String
    */
   public Phone(String userName)
   {
      this.userName = userName;
      phoneNumber = "unknown";
      phoneType = "unknown";
   }

   /**
    * Purpose: parameterized constructor
    * @param userName String
    * @param phoneNumer String
    * @param phoneType String
    */
   public Phone(String userName, String phoneNumber, String phoneType)
   {
      this.userName = userName;
      this.phoneNumber = phoneNumber;
      this.phoneType = phoneType;
   }

   /**
    * Purpose: Accessor
    * @return userName string
    * @return phoneNumber String
    * @return phoneType String
    */
   public String getUserName() { return userName; }
   public String getPhoneNumber() { return phoneNumber; }
   public String getPhoneType() { return phoneType; }

    /**
     * Purpose: Mutators
     * @param userName string
     * @param phoneNumber String
     * @param phoneType String
     */
   public void setUserName(String userName) { this.userName = userName; }
   public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
   public void getPhoneType(String phoneType) { this.phoneType = phoneType; }

    /**
     * Purpose: fetches a row from the database
     * @return boolean
     */
   public boolean fetch()
   {
      try
      {
         String[] params = { getUserName() };
         resultSet = MySQLDatabase.getData("SELECT * FROM phone WHERE UserName = ?", params);
         
         setUserName(resultSet[0][0]);
         setPhoneNumber(resultSet[0][1]);
         setPhoneType(resultSet[0][2]);
         
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
    /**
     * Purpose: Updates a row in the phones table
     * @return boolean
     */
   public boolean put()
   {
      try
      {
         String[] params = { getUserName(), getPhoneNumber(), getPhoneType(), getUserName() };
         MySQLDatabase.setData("UPDATE phone SET UserName = ?, PhoneNumber = ?, PhoneType = ? WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
    /**
     * Purpose: Inserts a new row into the phone table
     * @return userName boolean
     */
   public boolean post()
   {
      try
      {
         String[] params = { getUserName(), getPhoneNumber(), getPhoneType() };
         MySQLDatabase.setData("INSERT INTO phone (UserName, PhoneNumber, PhoneType) VALUES (?, ?, ?)", params);
         return true; 
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
    /**
     * Purpose: deletes a row from the phones table
     * @return boolean
     */
   public boolean delete()
   {
      try
      {
         String[] params = { getUserName() };
         MySQLDatabase.setData("DELETE FROM phone WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }
}