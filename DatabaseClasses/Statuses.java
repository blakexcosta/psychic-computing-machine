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