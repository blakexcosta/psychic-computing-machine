public class Committee 
{
   //Attributes
   private int committeeID;
   private int projectID;
   private String projectGrade;
   
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
      return true;
   }
   
   public boolean put()
   {
      return true;
   }
   
   public boolean post()
   {
      return true;
   }
   
   public boolean delete()
   {
      return true;
   }
      
}
