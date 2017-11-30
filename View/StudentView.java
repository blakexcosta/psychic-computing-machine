package View;

import Controller.LogController;
import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

public class StudentView {
    private String[][] sqlData;
    private TextField name, userName, department, gradDate, major, role;
    private GridPane gp = new GridPane();
    private String[][] rs;
    private LogController logController = null; //creating new private instance of the controller, so don't have to create new controller instance every time.

    public StudentView(){
        logController = new LogController();
    }

    public Scene makeUserView(){
        BorderPane root = new BorderPane(); //making a new borderpane
        MenuBar menuBar = new MenuBar(); //making a new menuBar
        root.setTop(menuBar);

        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );
        //TODO: loop through all of the results of selecting * for that user name and add them after the label

        //make the connection to the database
        //MySQLDatabase.getInstance().makeConnection();
        //get the information from the database
        String[] tempAr = new String[1];
        //MySQLDatabase.getInstance().getData("SELECT Name, U FROM user WHERE UserName = '?'", tempAr);
        gp.add( new Label("Name: "), 0,0);
        gp.add( new Label("User Name: "), 0,1);
        gp.add( new Label("Department: "), 0,2);
        gp.add( new Label("Graduation Date: "), 0,3);
        gp.add( new Label("Major: "), 0,4);
        gp.add( new Label("Role: "), 0,5);
        //creating a new button
        Button controllerActionButton = new Button("Accept");
        //adding to pane
        gp.add(controllerActionButton,4,6);
        //lambdas are sexy
        controllerActionButton.setOnAction(e -> {
                //creating a new controller instance
                logController.actionPerformed(e);
        });
        Scene sc = new Scene(gp,600,400);
        //making and addind a new menu.
        // --- Menu File
        Menu menuFile = new Menu("File");
        menuFile.getItems().addAll(
                new CheckMenuItem("Item 1"),
                new CheckMenuItem("Item 2")
        );
        // --- Menu Edit
        Menu menuEdit = new Menu("Edit");
        // --- Menu View
        Menu menuView = new Menu("View");
        //menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        //((VBox) sc.getRoot()).getChildren().addAll(menuBar);
        return sc;
    }
}
