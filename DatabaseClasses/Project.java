public class Project
{
   //Attributes
   private int projectID;
   private String projectName;
   private String projectSummary;
   private String projectTopic;
   private String projectType;
   private String projectStartDate;
   private String projectEndDate;
   private boolean projectCompleted;
   private boolean projectProposalApproved;
   private String projectFinalDefenseDate;
   private String projectPlagiarismPercentage;
   private String projectGrade;
   private int committeeID;
   
   //Default Constructor
   public Project()
   {
      projectID = -9999;
      projectName = "unknown";
      projectSummary = "unknown";
      projectTopic = "unknown";
      projectType = "unknown";
      projectStartDate = "unknown";
      projectEndDate = "unknown";
      projectCompleted = false;
      projectProposalApproved = false;
      projectFinalDefenseDate = "unknown";
      projectPlagiarismPercentage = "unknown";
      projectGrade = "unknown";
      committeeID = -9999;
   }
   
   //Parameterized Constructor - ID
   public Project(int projectID)
   {
      this.projectID = projectID;
      projectName = "unknown";
      projectSummary = "unknown";
      projectTopic = "unknown";
      projectType = "unknown";
      projectStartDate = "unknown";
      projectEndDate = "unknown";
      projectCompleted = false;
      projectProposalApproved = false;
      projectFinalDefenseDate = "unknown";
      projectPlagiarismPercentage = "unknown";
      projectGrade = "unknown";
      committeeID = -9999;
   }
   
   //Parameterized Constructor - Everything
   public Project(int projectID, String projectName, String projectSummary, String projectTopic, String projectStartDate, String projectEndDate, boolean projectCompleted, boolean projectProposalApproved, String projectFinalDefenseDate, String projectPlagiarismPercentage, String projectGrade, int committeeID)
   {
      this.projectID = projectID;
      this.projectName = projectName;
      this.projectSummary = projectSummary;
      this.projectTopic = projectTopic;
      this.projectType = projectType;
      this.projectStartDate = projectStartDate;
      this.projectEndDate = projectEndDate;
      this.projectCompleted = projectCompleted;
      this.projectProposalApproved = projectProposalApproved;
      this.projectFinalDefenseDate = projectFinalDefenseDate;
      this.projectPlagiarismPercentage = projectPlagiarismPercentage;
      this.projectGrade = projectGrade;
      this.committeeID = committeeID;
   }
   
   //Accessors
   public int getProjectID() { return projectID; }
   public String getProjectName() { return projectName; }
   public String getProjectSummary() { return projectSummary; }
   public String getProjectTopic() { return projectTopic; }
   public String getProjectType() { return projectType; }
   public String getProjectStartDate() { return projectStartDate; }
   public String getProjectEndDate() { return projectEndDate; }
   public boolean getProjectCompleted() { return projectCompleted; }
   public boolean getProjectProposalApproved() { return projectProposalApproved; }
   public String getProjectPlagiarismPercentage() { return projectPlagiarismPercentage; }
   public String getProjectGrade() { return projectGrade; }
   public int getCommitteeID() { return committeeID; }
   
   
   //Mutators
   public void setProjectID(int projectID) { this.projectID = projectID; }
   public void setProjectName(String projectName) { this.projectName = projectName; }
   public void setProjectSummary(String projectSummary){ this.projectSummary = projectSummary; }
   public void setProjectTopic(String projectTopic) { this.projectTopic = projectTopic; }
   public void getProjectType(String projectType) { this.projectType = projectType; }
   public void setProjectStartDate(String projectStartDate) { this.projectStartDate = projectStartDate; }
   public void setProjectEndDate(String projectEndDate) { this.projectEndDate = projectEndDate; }
   public void setProjectCompleted(boolean projectCompleted) { this.projectCompleted = projectCompleted; }
   public void setProjectProposalApproved(boolean projectProposalApproved) { this.projectProposalApproved = projectProposalApproved; }
   public void setProjectPlagiarismPercentage(String projectPlagiarismPercentage) { this.projectFinalDefenseDate = projectFinalDefenseDate; }
   public void setProjectGrade(String projectGrade) { this.projectGrade = projectGrade; }
   public void setCommitteeID(int committeeID) { this.committeeID = committeeID; }
   
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