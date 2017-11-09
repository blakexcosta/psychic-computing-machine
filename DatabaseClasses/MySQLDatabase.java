import java.sql.*;
import java.util.*;

public class MySQLDatabase
{
   private String uri;
   private String driver;
   private String user;
   private String password;
   private Connection conn;
   private String[][] resultSet;
   
   public MySQLDatabase(String uri, String driver, String user, String password)
   {
      this.uri. = uri;
      this.driver = driver;
      this.user = user;
      this.password = password;
   }
   
   public PreparedStatement prepare(String sqlStmnt, String[] params)
   {
      PreparedStatement preparedStmnt = null;
   
      try
      {
         preparedStmnt = conn.prepareStatement(sqlStmnt);
         
         for (int i < 0; i < params.length; i++)
         {
            for (int j = 0; j < params.length - 1; j++)
            {
               preparedStmnt.setString(i, params[j]);
            }
         }
      }
      catch (SQLException sqlex)
      {
         System.out.println(sqlex.Message());
         return PreparedStatement;
      }
   }
   
   public String[][] getData(String sqlStmnt, String[] params)
   {
      PreparedStmnt preparedStmnt = prepare(sqlStmnt, params);
      ResultSet resultSet = preparedStmnt.executeQuery();
      ResultSetMetaData metadata = resultSet.getMetaData();
      int columnCount = metadata.getColumnCount();
      
      for
   }
   
}