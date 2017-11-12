/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */

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
   private String projectDueDate;
   private boolean projectCompleted;
   private boolean projectProposalApproved;
   private String projectFinalDefenseDate;
   private String projectPlagiarismPercentage;
   private String projectGrade;
   private int committeeID;
   private String[][] resultSet;
   private MySQLDatabase databaseClass = new MySQLDatabase();


   /**
    * Purpose: default constructor
    */
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

   /**
    * Purpose: Parameterized constructor
    * @param projectID int
    */
   public Project(int projectID)
   {
      this.projectID = projectID;
      projectName = "unknown";
      projectSummary = "unknown";
      projectTopic = "unknown";
      projectType = "unknown";
      projectStartDate = "unknown";
      projectEndDate = "unknown";
      projectDueDate = "unknown";
      projectCompleted = false;
      projectProposalApproved = false;
      projectFinalDefenseDate = "unknown";
      projectPlagiarismPercentage = "unknown";
      projectGrade = "unknown";
      committeeID = -9999;
   }

    /**
     * Purpose: parameterized constructor
     * @param projectID int
     * @param projectName string
     * @param projectSummary string
     * @param projectTopic string
     * @param ProjectStartDate string
     * @param ProjectEndDate String
     * @param projectDueDate string
     * @param projectCompleted boolean
     * @param projectProposalApproved boolean
     * @param projectFinalDefenseDate String
     * @param projectPlagiarismPercentage String
     * @param projectGrade String
     * @param committeeID int
    {
     */
   public Project(int projectID, String projectName, String projectSummary, String projectTopic, String projectStartDate, String projectEndDate, String projectDueDate, boolean projectCompleted, boolean projectProposalApproved, String projectFinalDefenseDate, String projectPlagiarismPercentage, String projectGrade, int committeeID)
   {
      this.projectID = projectID;
      this.projectName = projectName;
      this.projectSummary = projectSummary;
      this.projectTopic = projectTopic;
      this.projectType = projectType;
      this.projectStartDate = projectStartDate;
      this.projectEndDate = projectEndDate;
      this.projectDueDate = projectDueDate;
      this.projectCompleted = projectCompleted;
      this.projectProposalApproved = projectProposalApproved;
      this.projectFinalDefenseDate = projectFinalDefenseDate;
      this.projectPlagiarismPercentage = projectPlagiarismPercentage;
      this.projectGrade = projectGrade;
      this.committeeID = committeeID;
   }

    /**
     * Purpose: Accessors
     * @return projectID int
     * @return proejctSummary String
     * @return projectTopic String
     * @return projectType String
     * @return projectStartDate String
     * @return projctEndDate String
     * @return projectDueDate String
     * @return projectCompleted boolean
     * @return proejctProposalApproved boolean
     */
   public int getProjectID() { return projectID; }
   public String getProjectName() { return projectName; }
   public String getProjectSummary() { return projectSummary; }
   public String getProjectTopic() { return projectTopic; }
   public String getProjectType() { return projectType; }
   public String getProjectStartDate() { return projectStartDate; }
   public String getProjectEndDate() { return projectEndDate; }
   public String getProjectDueDate() { return projectDueDate; }
   public boolean getProjectCompleted() { return projectCompleted; }
   public boolean getProjectProposalApproved() { return projectProposalApproved; }
   public String getProjectPlagiarismPercentage() { return projectPlagiarismPercentage; }
   public String getProjectGrade() { return projectGrade; }
   public int getCommitteeID() { return committeeID; }


    /**
     * Purpose: Mutators
     * @param projectID int
     * @param proejctSummary String
     * @param projectTopic String
     * @param projectType String
     * @param projectStartDate String
     * @param projctEndDate String
     * @param projectDueDate String
     * @param projectCompleted boolean
     * @param proejctProposalApproved boolean
     */
   public void setProjectID(int projectID) { this.projectID = projectID; }
   public void setProjectName(String projectName) { this.projectName = projectName; }
   public void setProjectSummary(String projectSummary){ this.projectSummary = projectSummary; }
   public void setProjectTopic(String projectTopic) { this.projectTopic = projectTopic; }
   public void getProjectType(String projectType) { this.projectType = projectType; }
   public void setProjectStartDate(String projectStartDate) { this.projectStartDate = projectStartDate; }
   public void setProjectEndDate(String projectEndDate) { this.projectEndDate = projectEndDate; }
   public void setProjectDueDate(String projectDueDate) { this.projectDueDate = projectDueDate; }
   public void setProjectCompleted(boolean projectCompleted) { this.projectCompleted = projectCompleted; }
   public void setProjectProposalApproved(boolean projectProposalApproved) { this.projectProposalApproved = projectProposalApproved; }
   public void setProjectPlagiarismPercentage(String projectPlagiarismPercentage) { this.projectFinalDefenseDate = projectFinalDefenseDate; }
   public void setProjectGrade(String projectGrade) { this.projectGrade = projectGrade; }
   public void setCommitteeID(int committeeID) { this.committeeID = committeeID; }

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
         databaseClass.makeConnection();
      
         resultSet = databaseClass.getAllData(tableName);
         
         //Close MySQL:
         databaseClass.closeConnection();

      
      }catch (Exception ex){
         ex.printStackTrace();
      }
      return resultSet;
   }//end fetchAll

   // public boolean fetch()
//    {
//       try
//       {
//          String[] params = { Integer.toString(getProjectID() ) };
//          resultSet = MySQLDatabase.getData("SELECT * FROM project WHERE ID = ?", params);
//          
//          setProjectID(resultSet[0][0]);
//          setProjectName(resultSet[0][1]);
//          setProjectSummary(resultSet[0][2]);
//          setProjectTopic(resultSet[0][3]);
//          setProjectType(resultSet[0][4]);
//          setProjectStartDate(resultSet[0][5]);
//          setProjectEndDate(resultSet[0][6]);
//          setProjectDueDate(resultSet[0][7]);
//          setProjectCompleted(resultSet[0][8]);
//          setProjectProposalApproved(resultSet[0][9]);
//          setProjectPlagiarismPercentage(resultSet[0][10]);
//          setProjectGrade(resultSet[0][11]);
//          setCommitteeID(resultSet[0][12]);
//          
//          return true;
//       }
//       catch (Exception ex)
//       {
//          System.out.println(ex.Message());
//          return false;
//       }
//    }
    /**
     * Purpose: updates a row into a database
     * @return boolean
     */
   public boolean put()
   {
      try
      {
         // String[] params = { getProjectID(), getProjectName(), getProjectSummary(), getProjectTopic(), getProjectType(), getProjectStartDate(), getProjectEndDate(), getProjectDueDate(), getProjectCompleted(), getProjectProposalApproved(), getProjectFinalDefenseDate(), getProjectPlagiarismPercentage(), getProjectGrade(), getCommitteeID(), getProjectID() };
//          MySQLDatabase.setData("UPDATE project SET ID =?, Name = ?, Summary = ?, Topic = ?, Type = ?, StartDate = ?, EndDate = ?, DueDate = ?, Completed = ?, ProposalApproved = ?, FinalDefenseDate = ?, PlagiarismPercentage = ?, ProjectGrade = ?, CommitteeID = ? WHERE ID = ?", params);
         return true;
      }
      catch (Exception e)
      {
         System.out.println(e.getMessage());
         return false;
      }
   }
    /**
     * Purpose: Inserts a new row into a database
     * @return boolean
     */
   public boolean post()
   {
      try
      {
         // String[] params = { getProjectID(), getProjectName(), getProjectSummary(), getProjectTopic(), getProjectType(), getProjectStartDate(), getProjectEndDate(), getProjectDueDate(), getProjectCompleted(), getProjectProposalApproved(), getProjectFinalDefenseDate(), getProjectPlagiarismPercentage(), getProjectGrade(), getCommitteeID() };
//          MySQLDatabase.setData("INSERT INTO project (ID, Name, Summary, Topic, Type, StartDate, EndDate, DueDate, Competed, ProposalApproved, FinalDefenseDate, PlagiarismPercentage, ProjectGrade, CommitteeID) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
         return true; 
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage());
         return false;
      }
   }
    /**
     * Purpose: Deletes a row from the database
     * @return boolean
     */
   public boolean delete()
   {
      try
      {
         // String[] params = { getProjectID() };
//          MySQLDatabase.setData("DELETE FROM project WHERE ID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.getMessage());
         return false;
      }
   }

}