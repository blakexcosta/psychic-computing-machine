package View;
import Model.MySQLDatabase;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.util.Observable;
import java.util.Observer;

/**
 * LoginView class serves as the default template that holds all the functionality of the jpanels
 * individual components
 */
public class LoginView extends Application implements Observer{

    Stage window = new Stage();
    TextField userNameField;
    PasswordField passwordField;
    GridPane gp;
    MySQLDatabase msdb = new MySQLDatabase(); //there is only one instance of a database.
    String usrRole;

    //These classes will have all of the functionality to make each scene.
    FacultyView facultyView = new FacultyView();
    StaffView staffView = new StaffView();
    StudentView studentView = new StudentView();

    @Override
    public void update(Observable observableObject, Object arg) {
        if( observableObject != msdb ) { //a quick check to make sure observable object is an instance of the database
            System.out.println("Observable object is not the object that is being observed, returning...");
            return;
        }
        if (arg instanceof MySQLDatabase) { //should be instance of this class
//            arg = (MySQLDatabase) arg;
//            userNameField.setPromptText(((MySQLDatabase) arg).getLoginUser());
            //use this to update your panels and shit.
            //to do that you need to cast it as an object.
        }
    }

    @Override
    public void start(Stage myStage) throws Exception {
        //Window title
        window.setTitle("Capstone Tracker - Login");
        //make gridpane
        gp = new GridPane();
        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );
        //make user name text field
        userNameField = new TextField();
        userNameField.setPromptText("User Name");
        //make password text field
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        //Add User Name label / Textfield to grid pane
        gp.add( new Label("User Name:"), 0,0);
        gp.add(userNameField, 1, 0);
        //Add Password label / Textfield to grid pane
        gp.add( new Label ("Password:"), 0, 1);
        gp.add(passwordField, 1, 1);
        //add button to grid pane
        Button loginButton = new Button("Log In");
        gp.add(loginButton, 1, 3);
        gp.setHalignment(loginButton, HPos.LEFT);//position to the left
        //Login button click functionality
        loginButton.setOnAction(e -> {
            //When button is clicked it will check to make sure the username and password are the same as the database.
            String[] vals = new String[1];
            boolean loginSuccess = false;
            try{
                vals[0] = userNameField.getText();
                msdb.makeConnection();
                String[][] rs = msdb.getData("SELECT Password, Role FROM user WHERE UserName in (?);", vals); //getting the values from the database
                String dbPassword = rs[0][0]; //getting the password
                usrRole = rs[1][1]; //getting the user role
                //if the password fields text that was inputed from the user equals the databases password,
                // then set loginSuccess = true, then continue to the next block.
                if (passwordField.getText().equals(dbPassword)){
                    loginSuccess = true;
                }
                if (loginSuccess){
                    //Make home view (either student, staff, or faculty by opening that class)
                    System.out.println("User Role: "+ usrRole);
                    Scene sc = null;
                    //we need a way to pass the user information to the view. otherwise it's useless to make these separate views.
                    String[] userName = new String[1];
                    userName[0] = userNameField.getText(); //CHANGE THIS, hardcoded string
                    //getting information from the db.
                    String[][] userInfo = msdb.getData("SELECT CONCAT(FirstName,' ', LastName) as 'Name', " +
                            "UserName, Department, GraduationDate, major, role, Image FROM user WHERE UserName in (?);",userName);
                    //generating the associated views.
                    if (usrRole.equals("student")){
                        sc = studentView.makeUserView(userInfo);//this one uses the controller. others will eventually
                    }
                    if (usrRole.equals("staff")){
                        sc = staffView.makeUserView();
                    }
                    if (usrRole.equals("faculty")){
                        sc = facultyView.makeUserView();
                    }
                    window.setTitle("Capstone Tracker - User LoginView");
                    window.setScene(sc); // setting the scene to whatever field was populated
                }
            }
            catch (Exception ee) {
                System.out.println("Incorrect Login");
            }
        });
        //make the view (scene
        // 5f4dcc3b5aa765d61d8327deb882cf99) that will go into the window.
        //Made up of the gridpane (that contains all the elements) and dimensions
        Scene loginScene = new Scene( gp, 1366, 768 );
        //Put view into the window
        // TODO: 11/29/17 Should we be using stages instead of scenes? Everything I have seen in regards to example code has been with stage usage -Blake
        // TODO: There is one stage. The stage is the global window (line 19). the scene go inside the stage. fx is kinda odd with the naming -Gavin

        // the global wi
        window.setScene(loginScene);
        //show the window
        window.show();
    }

    //Quick Question, Gavin, can you explain why there needs to be two mains? is this just a javafx thing? and does instances for the view need
    //to be included in here, or can they be placed normally into the normal main method?
    public static void main(String[] args) {
        launch(args);
    }
}