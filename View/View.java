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

    Button loginButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage myStage) throws Exception {
        TextField userNameField = new TextField();
        userNameField.setPromptText("User Name");
        TextField passwordField = new TextField();
        passwordField.setPromptText("Password");

        myStage.setTitle("Calculator");

        GridPane rootNode= new GridPane();
        rootNode.setPadding( new Insets( 15 ) );
        rootNode.setHgap( 5 );
        rootNode.setVgap( 5 );
        rootNode.setAlignment( Pos.CENTER );

        Scene myScene = new Scene( rootNode, 300, 200 );

        rootNode.add( new Label("User Name:"), 0,0); rootNode.add(userNameField, 1, 0);
        rootNode.add( new Label ("Password:"), 0, 1); rootNode.add(passwordField, 1, 1);
        Button loginButton = new Button("Log In"); rootNode.add(loginButton, 1, 3);
        rootNode.setHalignment(loginButton, HPos.LEFT);

        myStage.setScene( myScene);

        myStage.show();
    }
}
