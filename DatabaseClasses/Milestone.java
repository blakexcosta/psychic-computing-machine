public class Milestone
{
   //Attributes
   private int milestoneID;
   private int milestoneStatusCode;
   private String milestoneName;
   private int milestoneNumber;
   private String milestoneDueDate;
   private boolean milestoneApproved;
   
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
   
   //Parameterized Constructor - ID
   public Milestone(int milestoneID)
   {
      this.milestoneID = milestoneID;
      milestoneStatusCode = -9999;
      milestoneName = "unknown";
      milestoneNumber = -9999;
      milestoneDueDate = "unknown";
      milestoneApproved = false;
   }
   
   //Parameterized Constructor - Everything
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

   //Database Transaction Stubbs
   public boolean fetch()
   {
      try
      {
         String[] params = { getMilestoneID() };
         resultSet = MySQLDatabase.getData("SELECT * FROM milestone WHERE ID = ?", params);
         
         setMilestoneID(resultSet[0][1]);
         setMilestoneStatusCode(resultSet[0][1]);
         setMilestoneName(resultSet[0][2]);
         setMilestoneNumber(resultSet[0][3]);
         setMilestoneDueDate(resultSet[0][4]);
         setMilestoneApproved(resultSet[0][5]);
         
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
         String[] params = { getMilestoneID(), getMilestoneStatusCode(), getMilestoneName(), getMilestoneNumber(), getMilestoneDueDate(), getMilestoneApproved(), getMilestoneID() };
         MySQLDatabase.setData("UPDATE milestone SET ID = ?, StatusCode = ?, Name = ?, Number = ?, DueDate = ?, Approved = ?  WHERE ID = ?", params);
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
         String[] params = { getMilestoneID(), getMilestoneStatusCode(), getMilestoneName(), getMilestoneNumber(), getMilestoneDueDate(), getMilestoneApproved() };
         MySQLDatabase.setData("INSERT INTO milestone (ID, StatusCode, Name, Number, DueDate, Approved) VALUES (?, ?, ?, ?, ?, ?)", params);
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
         String[] params = { getMilestoneID() };
         MySQLDatabase.setData("DELETE FROM milestone WHERE ID = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         System.out.println(ex.Message());
         return false;
      }
   }

}