package DatabaseClasses;

import java.sql.*;
import java.util.*;
/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */

public class mainTest {
   
   public static void main(String[] args) {
      
      
      User user = new User();
      Project proj = new Project();
      Email email = new Email();
      Office office = new Office();
   
   
      Phone phone = new Phone();
      Statuses stat = new Statuses();
      
      System.out.println(Arrays.deepToString(user.fetchAll("User") ) );
      System.out.println(Arrays.deepToString(proj.fetchAll("Project") ) );
      System.out.println(Arrays.deepToString(email.fetchAll("Email") ) );
      System.out.println(Arrays.deepToString(office.fetchAll("Office") ) );  
      System.out.println(Arrays.deepToString(phone.fetchAll("Phone") ) );  
      System.out.println(Arrays.deepToString(stat.fetchAll("Statuses") ) );  

          
      



      System.out.println("\n * * * * * * * * * * * * * * * * * * * * * * ");
      System.out.println(" -- Hit Enter To Exit --");

      Scanner scanner = new Scanner(System.in);
      scanner.nextLine();

   }
}