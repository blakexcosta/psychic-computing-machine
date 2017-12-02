package View;
import Controller.BusinessLayerLogin;
import Model.MySQLDatabase;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;

/**
 * LoginView class serves as the default template that holds all the functionality of the jpanels
 * individual components
 */
public class LoginView{

    Stage window = new Stage();
    TextField userNameField;
    PasswordField passwordField;
    GridPane gp;
    MySQLDatabase msdb = new MySQLDatabase(); //there is only one instance of a database.
    String usrRole;
    private BusinessLayerLogin controller = null;

    public LoginView(){
        controller = new BusinessLayerLogin();
    }

    public Scene makeLoginView(){
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

        loginButton.setOnAction(e -> { new BusinessLayerLogin().login(userNameField.getText(), passwordField.getText());});
//        loginButton.setOnAction(e -> {
//            //TODO: All login functionality as well as populating with the user info. THIS SHOULD ALL BE IN THE CONTROLLER....I think -Gavin

        Scene loginScene = new Scene( gp, 1366, 768 );
        return loginScene;

    }

//    String[] vals = new String[1];
//    boolean loginSuccess = false;
//        try{
//        vals[0] = userNameField.getText();
//        msdb.makeConnection();
//        String[][] rs = msdb.getData("SELECT Password, Role FROM user WHERE UserName in (?);", vals); //getting the values from the database
//        String dbPassword = rs[0][0]; //getting the password
//        usrRole = rs[1][1]; //getting the user role
//        //if the password fields text that was inputed from the user equals the databases password,
//        // then set loginSuccess = true, then continue to the next block.
//        if (passwordField.getText().equals(dbPassword)){
//            loginSuccess = true;
//        }
        //                    if (loginSuccess){
        //                        //Make home view (either student, staff, or faculty by opening that class)
        //                        System.out.println("User Role: "+ usrRole);
        //                        Scene sc = null;
        //                        //we need a way to pass the user information to the view. otherwise it's useless to make these separate views.
        //                        String[] userName = new String[1];
        //                        userName[0] = userNameField.getText(); //CHANGE THIS, hardcoded string
        //                        //getting information from the db.
        //                        String[][] userInfo = msdb.getData("SELECT CONCAT(FirstName,' ', LastName) as 'Name', " +
        //                                "UserName, Department, GraduationDate, major, role, Image FROM user WHERE UserName in (?);",userName);
        //                        //generating the associated views.
        //                        if (usrRole.equals("student")){
        //                            sc = studentView.makeUserView(userInfo);//this one uses the controller. others will eventually
        //                        }
        //                        if (usrRole.equals("staff")){
        //                            sc = staffView.makeUserView();
        //                        }
        //                        if (usrRole.equals("faculty")){
        //                            sc = facultyView.makeUserView();
        //                        }
        //                        window.setTitle("Capstone Tracker - User LoginView");
        //                        window.setScene(sc); // setting the scene to whatever field was populated
        //                    }
        //                }
        //                catch (Exception ee) {
        //                    System.out.println("Incorrect Login");
        //                }
        //            });

}