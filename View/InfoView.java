package View;

import java.util.*;
import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;



import java.util.Observable;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

public class InfoView extends Observable {
    //both of these are in ALL the view classes
    private MySQLDatabase msdb; //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private String[][] sqlData;
    private Label userInfoHeaderLabel,labName, labUserName, labDepartment, labGradDate, labMajor, labRole;
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

    //TODO: Build out student info view (make it look nice. also add the information in loadStudentDBInfo)
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

        labDepartment = new Label("Department: ");
        labDepartment.getStyleClass().add("infoLabel");

        labGradDate = new Label("Graduation Date: ");
        labGradDate.getStyleClass().add("infoLabel");

        labMajor = new Label("Major: ");
        labMajor.getStyleClass().add("infoLabel");

        labRole = new Label("Role: ");
        labRole.getStyleClass().add("infoLabel");
        
        editProfileButton = new Button();
        editProfileButton.setText("Edit User Profile");
        
        editProfileButton.setOnAction(e -> {
            makeEditPopup("s");
        });
        
        gp.add(labName, 0, 1);
        gp.add(labUserName, 0, 2);
        gp.add(labDepartment, 0, 3);
        gp.add(labGradDate, 0, 4);
        gp.add(labMajor, 0, 5);
        gp.add(labRole, 0, 6);
        gp.add(editProfileButton, 0, 7);
        
        gp.gridLinesVisibleProperty().setValue(true);

        loadStudentDBInfo();//this sets the labels to the proper info using the local class instance of MSDB
        //loop through each of the user fields that we have and put them next to the label. They come in in the correct order
        setChanged();
        notifyObservers(sc);
    }

    /**
     * gets all of the data to be put next to the labels in the grid.
     */
    public void loadStudentDBInfo() {
        ArrayList<String> userNameAL = new ArrayList<String>();
        userNameAL.add(mv.getCurrUserName());
        //ArrayList<String> userNameAL = {mv.getCurrUserName()};
        returnData = msdb.getData("SELECT CONCAT(FirstName, ' ',  LastName) as 'Name' , UserName, Department, GraduationDate,Major, Role FROM user where UserName in (?)", userNameAL);
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
        labGradDate = new Label("Graduation Date: ");
        labGradDate.getStyleClass().add("infoLabel");
        labRole = new Label("Role: ");
        labRole.getStyleClass().add("infoLabel");
        
        editProfileButton.setOnAction(e -> {
            makeEditPopup("stf");
        });
        
        //adding to the gridpane
        gp.add(labName, 0, 1);
        gp.add(labUserName, 0, 2);
        gp.add(labDepartment, 0, 3);
        gp.add(labGradDate, 0, 4);
        gp.add(labRole, 0, 5);
        gp.add(editProfileButton, 0, 6);

        gp.gridLinesVisibleProperty().setValue(true);
        //load the student information
        // TODO: 12/12/17 Found my error, its the line below me. gotta implement a loadStaffView method, not gonna mess with making that method generic -Blake 
        loadStudentDBInfo();//this sets the labels to the proper info using the local class instance of MSDB
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

        labRole = new Label("Role: ");
        labRole.getStyleClass().add("infoLabel");
        
        editProfileButton.setOnAction(e -> {
            makeEditPopup("f");
        });
        
        gp.add(labName, 0, 1);
        gp.add(labUserName, 0, 2);
        gp.add(labDepartment, 0, 3);
        gp.add(labRole, 0, 4);
        gp.add(editProfileButton, 0, 5); //

        gp.gridLinesVisibleProperty().setValue(true);

        loadStudentDBInfo();//this sets the labels to the proper info using the local class instance of MSDB
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
      // ArrayList<String> roleOptions;
//       //rs = msdb.getData("SELECT CONCAT(FirstName, ' ', LastName) as 'Name' FROM user WHERE role IN ('staff','faculty')",new ArrayList<>());
//       //String[] roles = new String[]{"student", "advisor", "professor"};
//       roleOptions.add("Student");
//       roleOptions.add("Advisor");
//       roleOptions.add("Professor");
      
      // boolean header = true;
//       for (ArrayList<String> curr : roleOptions){
//          if (header){
//             header = false;
//          }
//          else{
//             roleDropBox.getItems().add(curr.get(0));
//          }
//       }
      roleDropBox.getItems().addAll(
         "Student",
         "Faculty",
         "Adjunct",
         "Staff"
      );
      return roleDropBox;
   }
   
   
   private void makeEditPopup(String userTypeIdentifier) {
        //userTypeIdentifiers: s = student, stf = staff, f = faculty
        
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Input new user information");
        Label nameLab = new Label("New Name: ");
        Label usernLab = new Label("New Username: ");
        Label deptLab = new Label("New Department: ");
		Label gradDateLab = new Label("New Graduation Date: ");
		Label majLab = new Label("New Major: ");
		Label roleLab = new Label("New Role: ");

        TextField inputName = new TextField();
        TextField inputUsrName = new TextField();
        TextField inputDept = new TextField();
		TextField inputGradDate = new TextField();
		TextField inputMaj = new TextField();
		//also drop down

		


        Button submitButton = new Button("Submit Changes");

        gp.addColumn(0, header, nameLab, usernLab, deptLab, gradDateLab,majLab,roleLab, submitButton); //THIS WILL CHANGE DEPENDING ON USER TYPE
        gp.addColumn(1, new Label(), inputName, inputUsrName, inputDept, inputGradDate, inputMaj, makeRoleOptionDropdown());
		
		//gp.add(makeRoleOptionDropdown(), 0, 2);


        popupWindow.show();

        submitButton.setOnAction(e -> {
            // ArrayList<String> projectIDAL = new ArrayList<>(Arrays.asList(mv.getCurrProjectID()));
// 
//             if (!inputName.getText().isEmpty()) {
//                 msdb.setData("UPDATE project set Name='" + inputName.getText() + "' where ID in (?)", projectIDAL);
//             }
//             if (!inputUsrName.getText().isEmpty()) {
//                 msdb.setData("UPDATE project set Summary='" + inputSumm.getText() + "' where ID in (?)", projectIDAL);
//             }
//             if (!inputDept.getText().isEmpty()) {
//                 msdb.setData("UPDATE project set Topic='" + inputTopic.getText() + "' where ID in (?)", projectIDAL);
//             }
//             if (!inputGradDae.getText().isEmpty()) {
//                 msdb.setData("UPDATE project set Topic='" + inputTopic.getText() + "' where ID in (?)", projectIDAL);
//             }
//             if (!inputMaj.getText().isEmpty()) {
//                 msdb.setData("UPDATE project set Topic='" + inputTopic.getText() + "' where ID in (?)", projectIDAL);
//             }


            System.out.println("you did it, you submitted it. Good Job");
            makeStudentView();
            popupWindow.close();
        });


   }

}