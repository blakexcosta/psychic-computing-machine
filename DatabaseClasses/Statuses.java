/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */

public class Statuses
{
   //Attributes
   private int statusCode;
   private String statusDescription;
   
   //Default Constructor
   public Statuses()
   {
      statusCode = -9999;
      statusDescription = "unknown";
   }
   
   //Parameterized Constructor - ID
   public Statuses(int statusCode)
   {
      this.statusCode = statusCode;
      statusDescription = "unknown";
   }
   
   //Parameterized Constructor - Everything
   public Statuses(int statusCode, String statusDescription)
   {
      this.statusCode = statusCode;
      this.statusDescription = statusDescription;
   }
   
   //Accessors
   public int getStatusCode() { return statusCode; }
   public String getStatusDescription() { return statusDescription; }
   
   //Mutators
   public void setStatusCode(int statusCode) { this.statusCode = statusCode; }
   public void setStatusDescription(String statusDescription) { this.statusDescription = statusDescription; }

   //Database Transaction Stubbs
   public boolean fetch()
   {
      try
      {
         String[] params = { getStatusCode() };
         resultSet = MySQLDatabase.getData("SELECT * FROM statuses WHERE Code = ?", params);
         
         setStatusCode(resultSet[0][0]);
         setStatusDescription(resultSet[0][1]);
         
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
         String[] params = { getStatusCode(), getStatusDescription(), getStatusCode() };
         MySQLDatabase.setData("UPDATE statuses SET Code = ?, Description = ? WHERE Code = ?", params);
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
         String[] params = { getStatusCode(), getStatusDescription() };
         MySQLDatabase.setData("INSERT INTO statuses (Code, Description) VALUES (?, ?)", params);
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
         String[] params = { getStatusCode() };
         MySQLDatabase.setData("DELETE FROM statuses WHERE Code = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }

}   