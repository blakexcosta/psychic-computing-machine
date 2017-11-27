package DatabaseClasses;

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
   private String[][] resultSet;
   private MySQLDatabase msdb = new MySQLDatabase();

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
     * Purpose: fetches a table from the database
     * @param tableName String[][]
     * @return resultSet String[][]
     */
   public String[][] fetchAll(String tableName)
   {
      try
      {
         //Connect MySQL:
         msdb.makeConnection();
      
         resultSet = msdb.getAllData(tableName);
         
         //Close MySQL:
         msdb.closeConnection();

      
      }catch (Exception ex){
         ex.printStackTrace();
      }
      return resultSet;
   }//end fetchAll
   

   
    /**
     * Purpose: fetches a row from the database
     * @return boolean
     */
   public boolean fetch()
   {
      try
      {
         msdb.makeConnection();

         // String[] params = { getUserName() };
//          resultSet = msdb.getData("SELECT * FROM phone WHERE UserName = ?", params);
//          
//          setUserName(resultSet[0][0]);
//          setPhoneNumber(resultSet[0][1]);
//          setPhoneType(resultSet[0][2]);
         msdb.closeConnection();

         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage());
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
         msdb.makeConnection();

         String[] params = { getUserName(), getPhoneNumber(), getPhoneType(), getUserName() };
         msdb.setData("UPDATE phone SET UserName = ?, PhoneNumber = ?, PhoneType = ? WHERE UserName = ?", params);
         msdb.closeConnection();

         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage());
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
         msdb.makeConnection();

         String[] params = { getUserName(), getPhoneNumber(), getPhoneType() };
         msdb.setData("INSERT INTO phone (UserName, PhoneNumber, PhoneType) VALUES (?, ?, ?)", params);
         msdb.closeConnection();

         return true; 
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage());
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
         msdb.makeConnection();

         String[] params = { getUserName() };
         msdb.setData("DELETE FROM phone WHERE UserName = ?", params);
         msdb.closeConnection();

         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage());
         return false;
      }
   }
}