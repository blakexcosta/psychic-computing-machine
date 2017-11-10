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
      String uri_ = "jdbc:mysql://localhost/project_tracker?autoReconnect=true&useSSL=false";
      String driver_ = "com.mysql.jdbc.Driver";
      String user_ = "root";
      String password_ = "student";
      Connection conn_ = null;
      
    

      MySQLDatabase mySqlDB = new MySQLDatabase( uri_, driver_, user_, password_);

         //Connect MySQL:
      if(mySqlDB.makeConnection() == true){
         System.out.println(" **** Connection Success: mySQL ****\n\n");
      }
      else{
         System.out.println(" **** ERROR: mySQL connection FAILED ****");
      }
      
      User user = new User();
      Project proj = new Project();
      
      System.out.println(Arrays.deepToString(user.fetchAll("User") ) );
      System.out.println(Arrays.deepToString(proj.fetchAll("Project") ) );
      System.out.println(Arrays.deepToString(user.fetchAll("Email") ) );
      System.out.println(Arrays.deepToString(proj.fetchAll("Office") ) );



         //Close MySQL:
      if(mySqlDB.closeConnection() == true){
         System.out.println(" \n\n**** MySQL Connection Successfully CLOSED ****");
      }
      else{
         System.out.println(" **** ERROR: MySQL connection FAILED TO CLOSE ****");      }


      System.out.println("\n * * * * * * * * * * * * * * * * * * * * * * ");
      System.out.println(" -- Hit Enter To Exit --");

      Scanner scanner = new Scanner(System.in);
      scanner.nextLine();

   }
}