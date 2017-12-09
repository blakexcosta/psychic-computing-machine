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
   private String userName;
   private int projectID;
   private String role;
   MySQLDatabase dbClass;
   
   //Default Constructor
   public Committee()
   {
      userName = "unknown";
      projectID = -9999;
      role = "unknown";
   }
   
   /**
     * Purpose: Parameterized Constructor - ID
     * @param committeeID int
     */
   public Committee(int projectID)
   {
      this.projectID = projectID;
      userName = "unknown";
      role = "unknown";
   }
   
   /**
     * Purpose: Parameterized Constructor - Everything
     * @param committeeID int
     * @param projectID int
     * @param projectGrade String
     */  
   public Committee(String userName, int projectID, String role)
   {
      this.userName = userName;
      this.projectID = projectID;
      this.role = role;
   }
   
   //Accessors
   public String getUserName() { return userName; }
   public int getProjectID() { return projectID; }
   public String getRole() { return role; }
   
   //Mutators
   public void setUserName(String userName) { this.userName = userName; }
   public void setProjectID(int projectID) { this.projectID = projectID; }
   public void setRole(String role) { this.role = role; }
   
   /**
     * Purpose: Executes a SELECT SQL Statement on the committee table and returns a 2D array of the data retrieved
     * @return boolean depending on the success of the execution
     */
   public boolean fetch()
   {
      try
      {
         String[] params = { Integer.toString(getProjectID()) };
         String[][] resultSet = dbClass.getData("SELECT * FROM committee WHERE ProjectID = ?", params);
         
         setUserName(resultSet[0][0]);
         setProjectID(Integer.parseInt(resultSet[0][1]));
         setRole(resultSet[0][2]);
         
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
         String[] params = { getUserName(), Integer.toString(getProjectID()), getRole(), Integer.toString(getProjectID()) };
         dbClass.setData("UPDATE committee SET UserName = ?, ProjectID = ?, Role = ? WHERE ProjectID = ?", params);
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
         String[] params = { getUserName(), Integer.toString(getProjectID()), getRole() };
         dbClass.setData("INSERT INTO committee (UserName, ProjectID, Role) VALUES (?, ?, ?)", params);
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
         String[] params = { Integer.toString(getProjectID()) };
         dbClass.setData("DELETE FROM committee WHERE ProjectID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }   
}
