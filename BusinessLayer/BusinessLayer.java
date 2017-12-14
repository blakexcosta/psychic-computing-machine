package BusinessLayer;

import java.util.*;

public class BusinessLayer {

   public boolean checkNewProject(ArrayList<String> newProjectVals) {
      try {
         int projID = Integer.parseInt(newProjectVals.get(0));
         System.out.println("ID: " + projID);
         String projNm = newProjectVals.get(1).toString();
         System.out.println("Name: " + projNm);
         String projSum = newProjectVals.get(2).toString();
         System.out.println("Summary: " + projSum);
         String projTopic = newProjectVals.get(3).toString();
         System.out.println("Topic: " + projTopic);
         String projType = newProjectVals.get(4).toString();
         projType = projType.toUpperCase();
         System.out.println("Type: " + projType);
         String projStartDate = newProjectVals.get(5).toString();
         System.out.println("Start Date: " + projStartDate);
         String projDueDate = newProjectVals.get(6).toString();
         System.out.println("Due Date: " + projDueDate);
         
         if (projNm.equals("") || projSum.equals("") || projTopic.equals("") || projType.equals("") || projStartDate.equals("") || projDueDate.equals("")) {
            System.out.println("Incomplete Form");
            return false;
         } else {
            java.sql.Date validStartDate = java.sql.Date.valueOf(projStartDate);
            java.sql.Date validDueDate = java.sql.Date.valueOf(projDueDate);
         
            System.out.println("Start: " + validStartDate);
            System.out.println("End: " + validDueDate);
            
            if (projType.equals("CAPSTONE") || projType.equals("THESIS"))
            {
               System.out.println("Valid Project");
               return true;
            }
            else
            {
               System.out.println("Invalid Project");
               return false;
            }
         }
      }
      catch (Exception E)
      {
         System.out.println("Invalid Project");
         return false;
      }
   }
   
   public boolean checkEditProject(ArrayList<String> newProjectVals)
   {
      try {
         String projNm = newProjectVals.get(0).toString();
         System.out.println("Name: " + projNm);
         String projSum = newProjectVals.get(1).toString();
         System.out.println("Summary: " + projSum);
         String projTopic = newProjectVals.get(2).toString();
         System.out.println("Topic: " + projTopic);
         
         if (projNm.equals("")|| projSum.equals("") || projTopic.equals(""))
         {
            System.out.println("Incomplete Form");
            return false;
         }
         else 
         {
            System.out.println("Project Valid.");
            return true;
         } 
      }
      catch (Exception E)
      {
         System.out.println("Invalid Project");
         return false;
      }
      
   }   
}

