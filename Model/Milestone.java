package Model;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

public class Milestone
{
   //Attributes
   private int milestoneID;
   private int milestoneStatusCode;
   private String milestoneName;
   private int milestoneNumber;
   private String milestoneDueDate;
   private boolean milestoneApproved;
   MySQLDatabase dbClass = MySQLDatabase.getInstance();
   
   //Default Constructor
   public Milestone()
   {
      milestoneID = -9999;
      milestoneStatusCode = -9999;
      milestoneName = "unknown";
      milestoneNumber = -9999;
      milestoneDueDate = "unknown";
      milestoneApproved = false;
   }
   
   /**
     * Purpose: Parameterized Constructor - ID
     * @param milestoneID int
     */
   public Milestone(int milestoneID)
   {
      this.milestoneID = milestoneID;
      milestoneStatusCode = -9999;
      milestoneName = "unknown";
      milestoneNumber = -9999;
      milestoneDueDate = "unknown";
      milestoneApproved = false;
   }
   
   /**
     * Purpose: Parameterized Constructor - Everything
     * @param milestoneID int
     * @param milestoneStatusCode int
     * @param milestoneName String
     * @param milestoneNumber int
     * @param milestoneDueDate String
     * @param milestoneApproved boolean
     */
   public Milestone(int milestoneID, int milestoneStatusCode, String milestoneName, int milestoneNumber, String milestoneDueDate, boolean milestoneApproved)
   {
      this.milestoneID = milestoneID;
      this.milestoneStatusCode = milestoneStatusCode;
      this.milestoneName = milestoneName;
      this.milestoneNumber = milestoneNumber;
      this.milestoneDueDate = milestoneDueDate;
      this.milestoneApproved = milestoneApproved;
   }
   //Accessors
   public int getMilestoneID() { return milestoneID; }
   public int getMilestoneStatusCode() { return milestoneStatusCode; }
   public String getMilestoneName() { return milestoneName; }
   public int getMilestoneNumber() { return milestoneNumber; }
   public String getMilestoneDueDate() { return milestoneDueDate; }
   public boolean getMilestoneApproved() { return milestoneApproved; }
   
   //Mutators
   public void setMilestoneID(int milestoneID) { this.milestoneID = milestoneID; }
   public void setMilestoneStatusCode(int milestoneStatusCode) { this.milestoneStatusCode = milestoneStatusCode; }
   public void setMilestoneName(String milestoneName) { this.milestoneName = milestoneName; }
   public void setMilestoneNumber(int milestoneNumber) { this.milestoneNumber = milestoneNumber; }
   public void setMilestoneDueDate(String milestoneDueDate) { this.milestoneDueDate = milestoneDueDate; }
   public void setMilestoneApproved(boolean milestoneApproved) { this.milestoneApproved = milestoneApproved; }

   /**
     * Purpose: Executes a SELECT SQL Statement on the milestone table and returns a 2D array of the data retrieved
     * @return boolean depending on the success of the execution
     */
   public boolean fetch()
   {
      try
      {
         String[] params = { Integer.toString(getMilestoneID()) };
         String[][] resultSet = dbClass.getData("SELECT * FROM milestone WHERE ID = ?", params);
         
         setMilestoneID(Integer.parseInt(resultSet[0][1]));
         setMilestoneStatusCode(Integer.parseInt(resultSet[0][2]));
         setMilestoneName(resultSet[0][3]);
         setMilestoneNumber(Integer.parseInt(resultSet[0][4]));
         setMilestoneDueDate(resultSet[0][5]);
         setMilestoneApproved(Boolean.parseBoolean(resultSet[0][6]));
         
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes an UPDATE SQL Statement on the milestone table and it will update new values that reflect the attributes
     * @return boolean depending on the success of the execution
     */
   public boolean put()
   {
      try
      {
         String[] params = { Integer.toString(getMilestoneID()), Integer.toString(getMilestoneStatusCode()), getMilestoneName(), Integer.toString(getMilestoneNumber()), getMilestoneDueDate(), Boolean.toString(getMilestoneApproved()), Integer.toString(getMilestoneID()) };
         dbClass.setData("UPDATE milestone SET ID = ?, StatusCode = ?, Name = ?, Number = ?, DueDate = ?, Approved = ?  WHERE ID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes an INSERT SQL Statement on the email table and it will insert new values that reflect the attributes 
     * @return boolean depending on the success of the execution
     */
   public boolean post()
   {
      try
      {
         String[] params = { Integer.toString(getMilestoneID()), Integer.toString(getMilestoneStatusCode()), getMilestoneName(), Integer.toString(getMilestoneNumber()), getMilestoneDueDate(), Boolean.toString(getMilestoneApproved()) };
         dbClass.setData("INSERT INTO milestone (ID, StatusCode, Name, Number, DueDate, Approved) VALUES (?, ?, ?, ?, ?, ?)", params);
         return true; 
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   /**
     * Purpose: Executes a DELETE SQL Statement on the email table and will delete the data from the selected ID attribute
     * @return boolean depending on the success of the execution
     */
   public boolean delete()
   {
      try
      {
         String[] params = { Integer.toString(getMilestoneID()) };
         dbClass.setData("DELETE FROM milestone WHERE ID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }

}