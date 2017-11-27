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
        myStage.setTitle("Project Tracker");

        //make and position view elements in the grid pane (button and text fields)
        GridPane gp = new GridPane();
        //gp.setPadding( new Insets( 15 ) );
        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );


        //Add label and text field for User Name
        TextField userNameField = new TextField();
        userNameField.setPromptText("User Name");

        //add to the grid pane
        gp.add( new Label("User Name:"), 0,0);
        gp.add(userNameField, 1, 0);

        //Add label and text field for Password
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        gp.add( new Label ("Password:"), 0, 1);
        gp.add(passwordField, 1, 1);

        //add button to grid pane
        Button loginButton = new Button("Log In");
        gp.add(loginButton, 1, 3);

        //position button to the left
        gp.setHalignment(loginButton, HPos.LEFT);

        //Login button click functionality
        loginButton.setOnAction(e -> {
            System.out.println("log a person in!");
        });



        //make the view (scene) that will go into the window.
        //Made up of the gridpane (that contains all the elements) and dimensions
        Scene myScene = new Scene( gp, 600, 400 );

        //Put view into the window
        myStage.setScene( myScene);

        //show the window
        myStage.show();
    }
}  