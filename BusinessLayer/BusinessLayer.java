/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

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
   
   public boolean checkEditInfo(ArrayList<String> newInfoVals)
   {
      try {
         String userDept = newInfoVals.get(0).toString();
         System.out.println("Department: " + userDept);
         String userGradDate = newInfoVals.get(1).toString();
         System.out.println("Graduation Date: " + userGradDate);
         String userMajor = newInfoVals.get(2).toString();
         System.out.println("userMajor: " + userMajor);
         
         if (userDept.equals("")|| userGradDate.equals("") || userMajor.equals(""))
         {
            System.out.println("Incomplete Form");
            return false;
         }
         else 
         {
            java.sql.Date validGradDate = java.sql.Date.valueOf(userGradDate);
         
            System.out.println("Graduation Date: " + validGradDate);
         
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

