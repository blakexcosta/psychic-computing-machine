package Model;


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
   private String resultSet[][];
   private MySQLDatabase msdb= MySQLDatabase.getInstance();

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
     * Purpose: Fetches  a row from the office table
     * @return boolean
     */
   public boolean fetch()
   {
      try
      {
         msdb.makeConnection();
         String[] params = { getUserName() };
         resultSet = msdb.getData("SELECT * FROM office WHERE UserName = ?", params);
         
         setUserName(resultSet[0][0]);
         setOfficeNumber(resultSet[0][1]);
         msdb.closeConnection();
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex);
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
         msdb.makeConnection();
         String[] params = { getUserName(), getOfficeNumber (), getUserName() };
         msdb.setData("UPDATE office SET UserName = ?, OfficeNumber = ? WHERE UserName = ?", params);
         msdb.closeConnection();
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex);
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
         msdb.makeConnection();
         String[] params = { getUserName(), getOfficeNumber() };
         msdb.setData("INSERT INTO office (UserName, OfficeNumber) VALUES (?, ?)", params);
         msdb.closeConnection();
         return true; 
      }
      catch (Exception ex)
      {
         System.out.println(ex);
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
         msdb.makeConnection();
         String[] params = { getUserName() };
         msdb.setData("DELETE FROM office WHERE UserName = ?", params);
         msdb.closeConnection();
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex);
         return false;
      }
   }

}