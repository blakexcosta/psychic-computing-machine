package View;
import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.geometry.*;
import javafx.event.*;



import java.util.Observable;
import java.util.Observer;

/**
 * View class serves as the default template that holds all the functionality of the jpanels
 * individual components
 */
public class View extends Application implements Observer{

    @Override
    public void update(Observable o, Object arg) {


    }


    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {
        //Window title
        myStage.setTitle("Capstone Tracker - Login");

        //make gridpane
        GridPane gp = new GridPane();
        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );

        //make user name text field
        TextField userNameField = new TextField();
        userNameField.setPromptText("User Name");

        //make password text field
        TextField passwordField = new TextField();
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
            System.out.println("Username: " + userNameField.getText());
            System.out.println("Password: " + passwordField.getText());
            System.out.println("if Hashed("+passwordField.getText()+") == 'SELECT Password FROM user WHERE UserName = "+userNameField.getText()+"' THEN LOGIN WAS  A SUCCESS");
            boolean loginSuccess = true;

            if (loginSuccess){
                //Make home view (either student, staff, or faculty)
                System.out.println("'SELECT Role FROM User where UserName = " + userNameField.getText() + "' after successful login to get role");
                GridPane homePageGP = new GridPane();
                Scene homePageScene = new Scene(homePageGP, 600,400);


                myStage.setTitle("Capstone Tracker - Project View");
                myStage.setScene(homePageScene);
            }
        });



        //make the view (scene) that will go into the window.
        //Made up of the gridpane (that contains all the elements) and dimensions
        Scene loginScene = new Scene( gp, 600, 400 );

        //Put view into the window
        myStage.setScene(loginScene);

        //show the window
        myStage.show();
    }



}