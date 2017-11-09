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