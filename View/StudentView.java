package View;

import Controller.LogController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StudentView {
    String[][] sqlData;
    TextField name, userName, department, gradDate, major, role;
    GridPane gp = new GridPane();
    String[][] rs;
    private LogController logController = null; //creating new private instance of the controller, so don't have to create new controller instance every time.

    public StudentView(){
        logController = new LogController();
    }

    public Scene makeUserView(){

        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );

        //TODO: loop through all of the results of selecting * for that user name and add them after the label

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

        return sc;
    }
}
