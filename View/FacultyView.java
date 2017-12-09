package View;

import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

public class FacultyView {
    private String[][] sqlData;
    private TextField name, userName, department, gradDate, major, role;
    private GridPane gp = new GridPane();
    private String[][] resultSet;

    public FacultyView(){}

    // TODO: 12/8/17 Document and fix indentation -Blake
    public Scene makeUserView(){
        BorderPane root = new BorderPane(); //Main layout is a border pane
        Scene sc = new Scene(root,1366,768);//put main pane is a scene
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files

        HBox menuButtons = makeMenuButtons("User");//Use function to make the menu buttons. Decides which to show as clicked.

        root.setTop(menuButtons);//Put menu buttons at top of page
        root.setCenter(gp);//set center of page to grid pane (class attribute)

        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );

        gp.add( new Label("Name: "), 0,0);
        gp.add( new Label("User Name: "), 0,1);
        gp.add( new Label("Department: "), 0,2);
        gp.add( new Label("Role: "), 0,3);
        gp.add( new Label("THIS ONE IS FACULTY: "), 0,0);

        //Scene sc = new Scene(gp,600,400);

        return sc;
    }
    
    public HBox makeMenuButtons(String viewName){
        HBox returnMenu = new HBox();
        Button userInfoButton = new Button("User Information");
        Button projectInfoButton = new Button("Project Information");
        Button logOutButton = new Button("Log Out");
        returnMenu.getChildren().addAll(userInfoButton,projectInfoButton,logOutButton);
 
        if (viewName.equals("User")){
            System.out.println("user view");
        }
        if (viewName.equals("Project")){
            System.out.println("Project view");


        }

        return returnMenu;
    }
}
