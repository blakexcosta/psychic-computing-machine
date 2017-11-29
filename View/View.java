package View;
import Controller.Controller;
import Model.MySQLDatabase;
import javafx.application.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

import java.util.Observable;
import java.util.Observer;

/**
 * View class serves as the default template that holds all the functionality of the jpanels
 * individual components
 */
public class View extends Application implements Observer{

    Stage window = new Stage();
    TextField userNameField, passwordField;
    GridPane gp;
    MySQLDatabase msdb = new MySQLDatabase();
    String usrRole;

    //These classes will have all of the functionality to make each scene.
    FacultyView facultyView = new FacultyView();
    StaffView staffView = new StaffView();
    StudentView studentView = new StudentView();

    @Override
    public void update(Observable o, Object arg) {
        if (arg instanceof MySQLDatabase) { //should be instance of this class
            arg = (MySQLDatabase) arg;
            userNameField.setPromptText(((MySQLDatabase) arg).getLoginUser());
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
        passwordField = new TextField();
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
                String[][] rs = msdb.getData("SELECT Password, Role FROM user WHERE UserName in (?)", vals);
                String dbPassword = rs[0][0];
                usrRole = rs[1][1];
                if (passwordField.getText().equals(dbPassword)){
                    loginSuccess = true;
                }
            }
            catch (Exception ee) {
                System.out.println("Incorrect Login");
            }
            System.out.println("Username: " + userNameField.getText());
            System.out.println("Password: " + passwordField.getText());
            System.out.println("if Hashted("+passwordField.getText()+") == 'SELECT Password FROM user WHERE UserName = "+userNameField.getText()+"' THEN LOGIN WAS  A SUCCESS");
            loginSuccess = true;

            if (loginSuccess){
                //Make home view (either student, staff, or faculty by opening that class)
                System.out.println(usrRole);
                Scene sc = null;
                if (usrRole.equals("student")){

                    sc = studentView.makeUserView();
                }
                if (usrRole.equals("staff")){
                    sc = staffView.makeUserView();
                }
                if (usrRole.equals("faculty")){
                    sc = facultyView.makeUserView();

                }
                window.setTitle("Capstone Tracker - User View");
                window.setScene(sc);
            }

        });



        //make the view (scene) that will go into the window.
        //Made up of the gridpane (that contains all the elements) and dimensions
        Scene loginScene = new Scene( gp, 600, 400 );

        //Put view into the window
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