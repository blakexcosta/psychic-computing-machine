import java.sql.*;
import java.util.*;

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
   MySQLDatabase databaseClass = new MySQLDatabase();
   
   //Default Constructor
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
   
   //Parameterized Constructor - ID
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
   
   //Parameterized Constructor - Everything
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
   
   //Accessors
   public String getUserName() { return userName; }
   public String getFirstName() { return firstName; }
   public String getLastName() { return lastName; }
   public String getPassword() { return password; }
   public String getImageURL() { return imageURL; }
   public String getGraduationDate() { return graduationDate; }
   public String getDepartment() { return department; }
   public String getMajor() { return major; }
   public String getRole() { return role; }
   
   //Mutators
   public void setUserName(String userName) { this.userName = userName; }
   public void setFirstName(String firstName) { this.firstName = firstName; }
   public void setLastName(String lastName) { this.lastName = lastName; }
   public void setPassword(String password) { this.password = password; }
   public void setImageURL(String imageURL) { this.imageURL = imageURL; }
   public void setGraduationDate(String graduationDate) { this.graduationDate = graduationDate; }
   public void setDepartment(String department) { this.department = department; }
   public void setMajor(String major) { this.major = major; }
   public void setRole(String role) { this.role = role; }
   
   //Database Transaction Stubbs
   public boolean fetch()
   {
      try
      {
         String[] params = { getUserName() };
         String[][] resultSet = databaseClass.getData("SELECT * FROM user WHERE UserName = ?", params);
         
         setUserName(resultSet[0][0]);
         setFirstName(resultSet[0][1]);
         setLastName(resultSet[0][2]);
         setPassword(resultSet[0][3]);
         setImageURL(resultSet[0][4]);
         setGraduationDate(resultSet[0][5]);
         setDepartment(resultSet[0][6]);
         setMajor(resultSet[0][7]);
         setRole(resultSet[0][8]);
         
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   public boolean put()
   {
      try
      {
         String[] params = { getUserName(), getFirstName(), getLastName(), getPassword(), getImageURL(), getGraduationDate(), getDepartment(), getMajor(), getRole(), getUserName() };
         databaseClass.setData("UPDATE user SET UserName = ?, FirstName = ?, LastName = ?, Password = ?, Image = ?, GraduationDate = ?, Department = ?, Major = ?, Role = ? WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   public boolean post()
   {
      try
      {
         String[] params = { getUserName(), getFirstName(), getLastName(), getPassword(), getImageURL(), getGraduationDate(), getDepartment(), getMajor(), getRole()};
         databaseClass.setData("INSERT INTO user (UserName, FirstName, LastName, Password, Image, GraduationDate, Department, Major, Role) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", params);
         return true; 
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
   public boolean delete()
   {
      try
      {
         String[] params = { getUserName() };
         databaseClass.setData("DELETE FROM user WHERE UserName = ?", params);
         return true;
      }
      catch (Exception ex)
      {
         ex.printStackTrace();
         return false;
      }
   }
   
}