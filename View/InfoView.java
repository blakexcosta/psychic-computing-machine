/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

package View;

import java.util.*;
import Model.MySQLDatabase;
import BusinessLayer.BusinessLayer;
import javafx.geometry.Pos;


import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;



import java.util.Observable;

public class InfoView extends Observable {
    //both of these are in ALL the view classes
    private MySQLDatabase msdb; //there is only one instance of the database.
    private BusinessLayer busLayer = new BusinessLayer();
    private MasterView mv;//this is passed in through the constructor
    private String[][] sqlData;
    private Label userInfoHeaderLabel,labName, labUserName, labDepartment, labGradDate, labMajor, labRole, labPhone, labOffice, labEmail;
    private GridPane gp;
    private ArrayList<ArrayList<String>> returnData;
    private TextField inputName, inputuserName, dept, gradDate, role;
    private Button editProfileButton;
    private ComboBox roleDropBox;
    private ArrayList<ArrayList<String>> rs;


   
    public InfoView(MasterView _mv) {
        this.mv = _mv;
        msdb = mv.getMsdb();
    }

    //TODO: Build out student info view (make it look nice. also add the information in loadUserDBInfo)
    public void makeStudentView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        //add the style sheet to the Scene
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        BorderPane bp = (BorderPane) sc.getRoot();
        gp = new GridPane();
        //add the grid pane to the border pane
        bp.setCenter(gp);

        //set margins between cells
        gp.setHgap(5);
        gp.setVgap(10);
        //center the grid pane on the page
        gp.setAlignment(Pos.CENTER);

        //make the main header
        userInfoHeaderLabel = new Label("User Information");//Bigger header on top
        //style the header with css
        userInfoHeaderLabel.getStyleClass().add("mainHeader");
        userInfoHeaderLabel.setAlignment(Pos.CENTER);

        //add the header to the gird pane
        gp.add(userInfoHeaderLabel, 0, 0, 2, 1);
        labName = new Label("Name: ");
        labName.getStyleClass().add("infoLabel");

        labUserName = new Label("User Name: ");
        labUserName.getStyleClass().add("infoLabel");

        labGradDate = new Label("Graduation Date: ");
        labGradDate.getStyleClass().add("infoLabel");

        labMajor = new Label("Major: ");
        labMajor.getStyleClass().add("infoLabel");

        labEmail = new Label("Email: ");
        labEmail.getStyleClass().add("infoLabel");

        labPhone = new Label("Phone: ");
        labPhone.getStyleClass().add("infoLabel");
        
        editProfileButton = new Button();
        editProfileButton.setText("Edit User Profile");
        
        editProfileButton.setOnAction(e -> {
            makeEditPopup("s");
        });

        //testing out the new button
        Button sendEmailButton = new Button();
        sendEmailButton.setText("Send Email");
        //setting the action, Enter the email you want to send to here.
        sendEmailButton.setOnAction(e ->
            mv.sendEmail("armykids117@gmail.com","Test message")
        );

        gp.add(labName, 0, 1);
        gp.add(labUserName, 0, 2);
        gp.add(labGradDate, 0, 3);
        gp.add(labMajor, 0, 4);
        gp.add(labEmail,0,5);
        gp.add(labPhone,0,6);
        gp.add(editProfileButton, 0, 7);
        //adding the new button

        gp.gridLinesVisibleProperty().setValue(false);

        loadUserDBInfo("s");//this sets the labels to the proper info using the local class instance of MSDB
        //loop through each of the user fields that we have and put them next to the label. They come in in the correct order
        setChanged();
        notifyObservers(sc);
    }

    /**
     * gets all of the data to be put next to the labels in the grid.
     * @params String usertype --  s = student, stf = staff, f = faculty
     */
    public void loadUserDBInfo(String userType) {
        ArrayList<String> userNameAL = new ArrayList<String>();
        userNameAL.add(mv.getCurrUserName());
        //ArrayList<String> userNameAL = {mv.getCurrUserName()};
        if(userType == "s"){
            returnData = msdb.getData("SELECT CONCAT(FirstName, ' ',  LastName) as 'Name' , UserName, GraduationDate, Major, EmailAddress, PhoneNumber FROM user JOIN email USING(UserName) JOIN phone USING(UserName) JOIN office USING(UserName) where UserName in (?)", userNameAL);
        }
        if(userType == "stf"){
            returnData = msdb.getData("SELECT CONCAT(FirstName, ' ',  LastName) as 'Name', UserName, Department, PhoneNumber, EmailAddress, OfficeNumber FROM user JOIN email USING(UserName) JOIN phone USING(UserName) JOIN office USING(UserName) where UserName in (?)", userNameAL);
        }
        if(userType == "f"){
            returnData = msdb.getData("SELECT CONCAT(FirstName, ' ',  LastName) as 'Name', UserName, Department, PhoneNumber, EmailAddress, OfficeNumber FROM user JOIN email USING(UserName) JOIN phone USING(UserName) JOIN office USING(UserName) where UserName in (?)", userNameAL);
        }
   
        int rowCount = 0;
        
         for (int i = 0; i < returnData.get(1).size(); i++)
         {
            Label lab = new Label(returnData.get(1).get(i));
            lab.getStyleClass().add("infoDataLabel");
            gp.add(lab,1,++rowCount);
         }
    }

    /**
     * makes the staff view.
     */
    public void makeStaffView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        //add the style sheet to the Scene
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        BorderPane bp = (BorderPane) sc.getRoot();
        gp = new GridPane();
        //add the grid pane to the border pane
        bp.setCenter(gp);
        //set margins between cells
        gp.setHgap(5);
        gp.setVgap(10);
        //center the grid pane on the page
        gp.setAlignment(Pos.CENTER);
        //make the main header
        userInfoHeaderLabel = new Label("Staff Information");//Bigger header on top
        //style the header with css
        userInfoHeaderLabel.getStyleClass().add("mainHeader");
        userInfoHeaderLabel.setAlignment(Pos.CENTER);
        //add the header to the gird pane
        gp.add(userInfoHeaderLabel, 0, 0, 2, 1);
        //populating labels to the screen.
        labName = new Label("Name: ");
        labName.getStyleClass().add("infoLabel");
        
        labUserName = new Label("User Name: ");
        labUserName.getStyleClass().add("infoLabel");
        
        labDepartment = new Label("Department: ");
        labDepartment.getStyleClass().add("infoLabel");
        
        labPhone = new Label("Phone: ");
        labPhone.getStyleClass().add("infoLabel");
        
        labEmail = new Label("Email: ");
        labEmail.getStyleClass().add("infoLabel");
        
        labOffice = new Label("Office: ");
        labOffice.getStyleClass().add("infoLabel");
        
        editProfileButton = new Button();
        editProfileButton.setText("Edit User Profile");
        
        editProfileButton.setOnAction(e -> {
            makeEditPopup("stf");
        });
        
        //adding to the gridpane
        gp.add(labName, 0, 1);
        gp.add(labUserName, 0, 2);
        gp.add(labDepartment, 0, 3);
        gp.add(labPhone, 0, 4);
        gp.add(labEmail, 0, 5);
        gp.add(labOffice, 0, 6);
        gp.add(editProfileButton, 0, 7);

        gp.gridLinesVisibleProperty().setValue(false);
        //load the student information
        // TODO: 12/12/17 Found my error, its the line below me. gotta implement a loadStaffView method, not gonna mess with making that method generic -Blake 
        loadUserDBInfo("stf");//this sets the labels to the proper info using the local class instance of MSDB
        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    //TODO: Build out faculty info view
    public void makeFacultyView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        //add the style sheet to the Scene
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        BorderPane bp = (BorderPane) sc.getRoot();
        gp = new GridPane();
        //add the grid pane to the border pane
        bp.setCenter(gp);

        //set margins between cells
        gp.setHgap(5);
        gp.setVgap(10);
        //center the grid pane on the page
        gp.setAlignment(Pos.CENTER);

        //make the main header
        userInfoHeaderLabel = new Label("Faculty Information");//Bigger header on top
        //style the header with css
        userInfoHeaderLabel.getStyleClass().add("mainHeader");
        userInfoHeaderLabel.setAlignment(Pos.CENTER);

        //add the header to the gird pane
        gp.add(userInfoHeaderLabel, 0, 0, 2, 1);
        labName = new Label("Name: ");
        labName.getStyleClass().add("infoLabel");
        
        labUserName = new Label("User Name: ");
        labUserName.getStyleClass().add("infoLabel");
        
        labDepartment = new Label("Department: ");
        labDepartment.getStyleClass().add("infoLabel");
        
        labPhone = new Label("Phone: ");
        labPhone.getStyleClass().add("infoLabel");
        
        labEmail = new Label("Email: ");
        labEmail.getStyleClass().add("infoLabel");
        
        labOffice = new Label("Office: ");
        labOffice.getStyleClass().add("infoLabel");
        
        editProfileButton = new Button();
        editProfileButton.setText("Edit User Profile");
        
        editProfileButton.setOnAction(e -> {
            makeEditPopup("f");
        });
        
        gp.add(labName, 0, 1);
        gp.add(labUserName, 0, 2);
        gp.add(labDepartment, 0, 3);
        gp.add(labPhone, 0, 4);
        gp.add(labEmail, 0, 5);
        gp.add(labOffice, 0, 6);
        gp.add(editProfileButton, 0, 7);

        gp.gridLinesVisibleProperty().setValue(false);
        
        loadUserDBInfo("f");//this sets the labels to the proper info using the local class instance of MSDB
                            //takes in identifier to specifiy which data to pull for each indiviaul type of user
        //loop through each of the user fields that we have and put them next to the label. They come in in the correct order
        setChanged();
        notifyObservers(sc);
    }


   /**
   * Makes the combo box that is put at the top when adding a staff or faculty member to your committee.
   * @return
   */
   
   private ComboBox makeRoleOptionDropdown() {
      roleDropBox = new ComboBox<String>();

      roleDropBox.getItems().addAll(
         "student",
         "faculty",
         "staff"
      );
      return roleDropBox;
   }
   
   
   private void makeEditPopup(String userType) {
        //userTypeIdentifiers: s = student, stf = staff, f = faculty
        
        ArrayList<String> userNameAL = new ArrayList<String>();
        userNameAL.add(mv.getCurrUserName());
        
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Input new user information");
        Label deptLab = new Label("New Department: ");
		  Label gradDateLab = new Label("New Graduation Date: ");
		  
        Label majLab = new Label("New Major: ");
		  Label roleLab = new Label("New Role: ");

        TextField inputDept = new TextField();
		  TextField inputGradDate = new TextField();
		  TextField inputMaj = new TextField();	

        

        Button submitButton = new Button("Submit Changes");
         
        if (userType == "s"){
            gp.addColumn(0, header, deptLab, gradDateLab,majLab,roleLab, submitButton); //Student
            gp.addColumn(1, new Label(), inputDept, inputGradDate, inputMaj, makeRoleOptionDropdown());
            roleDropBox.getItems().remove("faculty");
            roleDropBox.getItems().remove("staff");
        }
        if (userType == "stf"){
            gp.addColumn(0, header, deptLab, gradDateLab, roleLab, submitButton); //Staff
            gp.addColumn(1, new Label(), inputDept, inputGradDate, makeRoleOptionDropdown());

        }
        if (userType == "f"){
            gp.addColumn(0, header, deptLab, roleLab, submitButton); //Staff
            gp.addColumn(1, new Label(), inputDept, makeRoleOptionDropdown());
            roleDropBox.getItems().remove("student");

        }
        
        //Autofill textFields with current data:
        rs = msdb.getData("SELECT * FROM user WHERE UserName=(?) ", userNameAL); //retrieves current user's role

        inputDept.setText( rs.get(1).get(6) );
        inputGradDate.setText( rs.get(1).get(5) );
        inputMaj.setText( rs.get(1).get(7) );
        roleDropBox.setValue( rs.get(1).get(8) );
        String originalRole = roleDropBox.getValue().toString();

        popupWindow.show();

        submitButton.setOnAction(e -> {
            // ArrayList<String> projectIDAL = new ArrayList<>(Arrays.asList(mv.getCurrProjectID()));
            
            String selected = roleDropBox.getValue().toString();
            
            ArrayList<String> newInfoVals = new ArrayList<>(Arrays.asList(inputDept.getText(), inputGradDate.getText(), inputMaj.getText()));
            
            //Business layer checks the values
            boolean checkResult = busLayer.checkUserEditInfo(newInfoVals, userType);
            System.out.println("Business Layer Check: " + checkResult);
            
            if (checkResult) {
                       
               if (!inputDept.getText().isEmpty()) {
                   msdb.setData("UPDATE user set Department='" + inputDept.getText() + "' where UserName= (?)", userNameAL );//mv.getCurrUserName() );
               }
               if (!inputGradDate.getText().isEmpty()) {
                   msdb.setData("UPDATE user set GraduationDate='" + inputGradDate.getText() + "' where UserName= (?)", userNameAL );// mv.getCurrUserName() );

               }
               if (!inputMaj.getText().isEmpty()) {
                   msdb.setData("UPDATE user set Major='" + inputMaj.getText() + "' where UserName=(?)", userNameAL );// mv.getCurrUserName() );
               }
               if (originalRole != selected ) {
                
                   System.out.println("Inside combobox if statement");
   
                   msdb.setData("UPDATE user set Role='" + selected + "' where UserName=(?)", userNameAL );// mv.getCurrUserName() );
                   System.out.println("you changed the role!: " + selected );
               }
       
               produceUserView( userType );
               popupWindow.close();
            } else {
               produceUserView( userType );
               popupWindow.close();
               popupWindow.show();
            }
        });


   }
   
   /**
   * Creates a user view depending on the parameters given. Helper function for makeEditPopup()
   * @return
   */
   public void produceUserView(String user){ //"s": student -- "stf": staff -- "f": Faculty
      switch(user){
         case "s"   : makeStudentView();
                      break;
         case "stf" : makeStaffView();
                      break;
         case "f"   : makeFacultyView();
                      break;          
      }  
   }
   
   private String nameToUserName(String _name) {
        //Arraylist is the first name and last name split
        ArrayList<String> queryVals = new ArrayList<>(Arrays.asList(_name.split(" ")));
        //Get the username for that first and last name
        rs = msdb.getData("SELECT UserName from user where FirstName in (?) and LastName in (?)", queryVals);
        //return the username
        return rs.get(1).get(0);

    }

}