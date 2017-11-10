import java.sql.*;
import java.util.*;

public class mainTest {
   
   public static void main(String[] args) {
      String uri_ = "jdbc:mysql://localhost/project_tracker?autoReconnect=true&useSSL=false";
      String driver_ = "com.mysql.jdbc.Driver";
      String user_ = "iste330t01";
      String password_ = "iste330bmgoq";
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
      user.fetch();



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