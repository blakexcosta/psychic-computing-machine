/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */

public class Office
{
   //Attributes
   private String userName;
   private String officeNumber;

   /**
    * Purpose: Default constructor
    */
   public Office()
   {
      userName = "unknown";
      officeNumber = "unknown";
   }

   /**
    * Purpose: Parameterized constructor - userName
    * @param userName String
    */
   public Office(String userName)
   {
      this.userName = userName;
      officeNumber = "unknown";
   }

    /**
     * Purpose: Parameterized constructor
     * @param userName String
     * @param officeNumber string
     */
   public Office(String userName, String officeNumber)
   {
      this.userName = userName;
      this.officeNumber = officeNumber;
   }

    /**
     * Purpose: Accessor
     * @return userName String
     * @return officeNumber String
     */
   public String getUserName() { return userName; }
   public String getOfficeNumber() { return officeNumber; }

    /**
     * Purpose: Mutators
     * @param userName String
     * @param officeNumber String
     */
   public void setUserName(String userName) { this.userName = userName; }
   public void setOfficeNumber(String officeNumber) { this.officeNumber = officeNumber; }

    /**
     * Purpose: Fetches  a row from the office table
     * @return boolean
     */
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
    /**
     * Purpose: Updates a row in the office table
     * @return boolean
     */
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
    /**
     * Purpose: Inserts a row into the office table
     * @return boolean
     */
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
    /**
     * Purpose: Deletes a row in the office table
     * @return boolean
     */
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