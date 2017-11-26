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
   
   //Parameterized Constructor - ID
   public Committee(int committeeID)
   {
      this.committeeID = committeeID;
      projectID = -9999;
      projectGrade = "unknown";
   }
   
   //Parameterized Constructor - Everything
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
   
   //Database Transaction Stubbs
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
