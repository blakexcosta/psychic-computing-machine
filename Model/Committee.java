package Model;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */
 
public class Committee 
{
   //Attributes
   private int committeeID;
   private int projectID;
   private String projectGrade;
   MySQLDatabase dbClass;
   
   //Default Constructor
   public Committee()
   {
      committeeID = -9999;
      projectID = -9999;
      projectGrade = "unknown";
   }
   
   /**
     * Purpose: Parameterized Constructor - ID
     * @param committeeID int
     */
   public Committee(int committeeID)
   {
      this.committeeID = committeeID;
      projectID = -9999;
      projectGrade = "unknown";
   }
   
   /**
     * Purpose: Parameterized Constructor - Everything
     * @param committeeID int
     * @param projectID int
     * @param projectGrade String
     */  
   public Committee(int committeeID, int projectID, String projectGrade)
   {
      this.committeeID = committeeID;
      this.projectID = projectID;
      this.projectGrade = projectGrade;
   }
   
   //Accessors
   public int getCommitteeID() { return committeeID; }
   public int getProjectID() { return projectID; }
   public String getProjectGrade() { return projectGrade; }
   
   //Mutators
   public void setCommitteeID(int committeeID) { this.committeeID = committeeID; }
   public void setProjectID(int projectID) { this.projectID = projectID; }
   public void setProjectGrade(String projectGrade) { this.projectGrade = projectGrade; }
   
   /**
     * Purpose: Executes a SELECT SQL Statement on the committee table and returns a 2D array of the data retrieved
     * @return boolean depending on the success of the execution
     */
   public boolean fetch()
   {
      try
      {
         String[] params = { Integer.toString(getCommitteeID()) };
         String[][] resultSet = dbClass.getData("SELECT * FROM committee WHERE ID = ?", params);
         
         setCommitteeID(Integer.parseInt(resultSet[0][0]));
         setProjectID(Integer.parseInt(resultSet[0][1]));
         setProjectGrade(resultSet[0][2]);
         
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes an UPDATE SQL Statement on the committee table and it will update new values that reflect the attributes
     * @return boolean depending on the success of the execution
     */
   public boolean put()
   {
      try
      {
         String[] params = { Integer.toString(getCommitteeID()), Integer.toString(getProjectID()), getProjectGrade(), Integer.toString(getCommitteeID()) };
         dbClass.setData("UPDATE committee SET ID = ?, ProjectID = ?, ProjectGrade = ? WHERE ID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes an INSERT SQL Statement on the committee table and it will insert new values that reflect the attributes 
     * @return boolean depending on the success of the execution
     */
   public boolean post()
   {
      try
      {
         String[] params = { Integer.toString(getCommitteeID()), Integer.toString(getProjectID()), getProjectGrade() };
         dbClass.setData("INSERT INTO committee (ID, ProjectID, ProjectGrade) VALUES (?, ?, ?)", params);
         return true; 
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes a DELETE SQL Statement on the committee table and will delete the data from the selected ID attribute
     * @return boolean depending on the success of the execution
     */
   public boolean delete()
   {
      try
      {
         String[] params = {Integer.toString(getCommitteeID()) };
         dbClass.setData("DELETE FROM committee WHERE ID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
      
}
