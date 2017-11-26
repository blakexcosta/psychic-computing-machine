import java.util.*;
import java.sql.*;

public class practical2Practice{

   public static void main(String[] args){
   
      System.out.println("hello");
      Connection conn = null;
      try{
         String url = "jdbc:mysql://127.0.0.1/travel";
         String username ="root";
         String pw = "student";
         Class.forName("com.mysql.jdbc.Driver");
         conn = DriverManager.getConnection(url, username, pw);
         conn.setAutoCommit(false);
         function1(conn); 
         function2(conn);
         conn.rollback();
         conn.setAutoCommit(true);
         function1(conn);
         function2(conn);
         function1(conn);
      }catch(SQLException e){    
         e.printStackTrace();       
      }
      catch(ClassNotFoundException e){
         e.printStackTrace();   
      }
   }
   
      public static void function1(Connection _conn){
      try{
         PreparedStatement stmt = _conn.prepareStatement("SELECT EquipmentDescription FROM equipment WHERE EquipId = ?;");
         stmt.setInt(1,1256);
         ResultSet rs = stmt.executeQuery();
         ResultSetMetaData rsmd = rs.getMetaData();
         int numFields = rsmd.getColumnCount();
         while(rs.next()){
            for(int i=1; i<=numFields; i++){
               System.out.println(rs.getString(i));
            }//end for  
         }//end while        
      }catch(Exception e){
         e.printStackTrace();
      }
      }//end function 1

   
   //function does an update
   public static void function2(Connection _conn){
      try{
         PreparedStatement stmt = _conn.prepareStatement("UPDATE equipment SET EquipmentDescription=? WHERE EquipId = ?;");
         stmt.setString(1,"3423");
         stmt.setString(2,"1256");
         int rowCount = stmt.executeUpdate();
      }catch(Exception e){
         e.printStackTrace();
      }
      }//end function 1

}