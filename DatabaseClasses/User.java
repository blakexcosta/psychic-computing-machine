import java.sql.*;
import java.util.*;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 */


public class User
{
   //Attributes
   private String userName;
   private String firstName;
   private String lastName;
   private String password;
   private String imageURL;
   private String graduationDate;
   private String department;
   private String major;
   private String role;
   private String[][] resultSet; 
   private MySQLDatabase databaseClass = new MySQLDatabase();


   /**
    * Purpose: default constructor
    */

   public User()
   {
      userName = "unknown";
      firstName = "unknown";
      lastName = "unknown";
      password = "unknown";
      imageURL = "unknown";
      graduationDate = "unknown";
      department = "unknown";
      major = "unknown";
      role = "unknown";
   }

   /**
    * Purpose: parameterized constructor, takes in the userName
    * @param userName String
    */

   public User(String userName)
   {
      this.userName = userName;
      firstName = "unknown";
      lastName = "unknown";
      password = "unknown";
      imageURL = "unknown";
      graduationDate = "unknown";
      department = "unknown";
      major = "unknown";
      role = "unknown";
   }

   /**
    * Purpose: parameterized constructor
    * @param userName String
    * @param firstName String
    * @param lastName String
    * @param password String
    * @param imageURL String
    * @param graduationDate String
    * @param department String
    * @param major String
    * @param role String
    */
   public User(String userName, String firstName, String lastName, String password, String imageURL, String graduationDate, String department, String major, String role)
   {
      this.userName = userName;
      this.firstName = firstName;
      this.lastName = lastName;
      this.password = password;
      this.imageURL = imageURL;
      this.graduationDate = graduationDate;
      this.department = department;
      this.major = major;
      this.role = role;
   }

   /**
    * Purpose: accessors
    */
   public String getUserName() { return userName; }
   public String getFirstName() { return firstName; }
   public String getLastName() { return lastName; }
   public String getPassword() { return password; }
   public String getImageURL() { return imageURL; }
   public String getGraduationDate() { return graduationDate; }
   public String getDepartment() { return department; }
   public String getMajor() { return major; }
   public String getRole() { return role; }

   /**
    * Purpose: mutators
    */
   public void setUserName(String userName) { this.userName = userName; }
   public void setFirstName(String firstName) { this.firstName = firstName; }
   public void setLastName(String lastName) { this.lastName = lastName; }
   public void setPassword(String password) { this.password = password; }
   public void setImageURL(String imageURL) { this.imageURL = imageURL; }
   public void setGraduationDate(String graduationDate) { this.graduationDate = graduationDate; }
   public void setDepartment(String department) { this.department = department; }
   public void setMajor(String major) { this.major = major; }
   public void setRole(String role) { this.role = role; }

    /**
     * Purpose: fetches a 2d array of strings from the database, takes a string to determine what table to fetch
     * @param tableName String
     * @return resultSet String[][] return a result set that holds all of the data extracted from the database
     */
   public String[][] fetchAll(String tableName)
   {
   
      try{
         //Connect MySQL:
         databaseClass.makeConnection();
      
         resultSet = databaseClass.getAllData(tableName);
         
         //Close MySQL:
         databaseClass.closeConnection();

      }catch(Exception e){
         e.getMessage();
      }
      return resultSet;
   }//end fetchAll

    /**
     * Purpose: Updates a table in the database
     * @return boolean
     */
   public boolean put()
   {
      try
      {
         databaseClass.makeConnection();

         String[] params = { getUserName(), getFirstName(), getLastName(), getPassword(), getImageURL(), getGraduationDate(), getDepartment(), getMajor(), getRole(), getUserName() };
         databaseClass.setData("UPDATE user SET UserName = ?, FirstName = ?, LastName = ?, Password = ?, Image = ?, GraduationDate = ?, Department = ?, Major = ?, Role = ? WHERE UserName = ?", params);
         databaseClass.closeConnection();

         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }

    /**
     * Purpose: Inserts a single row into a table
     * @return boolean
     */
   public boolean post()
   {
      try
      {
         databaseClass.makeConnection();

         String[] params = { getUserName(), getFirstName(), getLastName(), getPassword(), getImageURL(), getGraduationDate(), getDepartment(), getMajor(), getRole()};
         databaseClass.setData("INSERT INTO user (UserName, FirstName, LastName, Password, Image, GraduationDate, Department, Major, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
         databaseClass.closeConnection();

         return true; 
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }

    /**
     * Purpose: Deletes a row from the database
     * @return boolean
     */
   public boolean delete()
   {
      try
      {
         databaseClass.makeConnection();

         String[] params = { getUserName() };
         databaseClass.setData("DELETE FROM user WHERE UserName = ?", params);
        databaseClass.closeConnection();

        
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
}