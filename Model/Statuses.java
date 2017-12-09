package Model;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

public class Statuses
{
   //Attributes
   private int statusCode;
   private String statusDescription;
   private MySQLDatabase msdb = MySQLDatabase.getInstance();
   private String[][] resultSet;

   /**
    * Purpose: default constructor
    */
   public Statuses()
   {
      statusCode = -9999;
      statusDescription = "unknown";
   }

   /**
    * Purpose: parameterized constructor
    * @param statusCode int
    */
   public Statuses(int statusCode)
   {
      this.statusCode = statusCode;
      statusDescription = "unknown";
   }

    /**
     * Purpose: parameterized constructor
     * @param statusCode int
     * @param statusDescription String
     */
   public Statuses(int statusCode, String statusDescription)
   {
      this.statusCode = statusCode;
      this.statusDescription = statusDescription;
   }

    /**
     * Purpose: Accessors
     * @return statusCode int
     * @return statusDescription String
     */
   public int getStatusCode() { return statusCode; }
   public String getStatusDescription() { return statusDescription; }

    /**
     * Purpose: Mutators
     * @param statusCode int
     * @param statusDescription String
     */
   public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
   public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

     /**
     * Purpose: fetches a 2d array of strings from the database, takes a string to determine what table to fetch
     * @param tableName String
     * @return resultSet String[][] return a result set that holds all of the data extracted from the database
     */
     //TODO: why not consolidate this into MySQLDatabase? if that handles it for us.
   public String[][] fetchAll(String tableName)
   {
   
      try{
         //Connect MySQL:
         msdb.makeConnection();
      
         resultSet = msdb.getAllData(tableName);
         
         //Close MySQL:
         msdb.closeConnection();

      }catch(Exception e){
         e.getMessage();
      }
      
      return resultSet;
   }//end fetchAll


  
  
  
    /**
     * Purpose: Gets a row of data after taking in the code number
     * @return boolean
     */
   public boolean fetch()
   {
      try
      {
         msdb.makeConnection();

         //String[] params = { getStatusCode() };
         //resultSet = MySQLDatabase.getData("SELECT * FROM statuses WHERE Code = " +Integer.toString(getStatusCode()) +";");
         
         setStatusCode(Integer.parseInt(resultSet[0][0]));
         setStatusDescription(resultSet[0][1]);
         msdb.closeConnection();

         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage() );
         return false;
      }
   }
    /**
     * Purpose: Updates a row in the database
     * @return boolean
     */
   public boolean put()
   {
      try
      {
         msdb.makeConnection();

         String[] params = { getStatusDescription()};
         msdb.setData("UPDATE statuses SET Code = " + Integer.toString(getStatusCode() ) + ", Description = ? WHERE Code = " + Integer.toString(getStatusCode()) + ";", params);
         msdb.closeConnection();

         return true;
      }
      catch (Exception ex)
      {
         ex.getMessage();
         return false;
      }
   }

    /**
     * Purpose: Inserts a new row into the database
     * @return boolean
     */
   public boolean post()
   {
      try
      {
         msdb.makeConnection();

         String[] params = { getStatusDescription() };
         msdb.setData("INSERT INTO statuses (Code, Description) VALUES ("+Integer.toString(getStatusCode() )+", ?)", params);
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
     * Purpose: deletes a row in a database
     * @return boolean
     */
   public boolean delete()
   {
      try
      {
         msdb.makeConnection();
         //String[] params = { getStatusCode() };
         //msdb.setData("DELETE FROM statuses WHERE Code = "+Integer.toString(getStatusCode() ) +";");
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