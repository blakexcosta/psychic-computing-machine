package View;

import Controller.Controller;
import Model.MySQLDatabase;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
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

    public StudentView(){}

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
        gp.add(controllerActionButton,1,4);
        //setting a new action for the button
        controllerActionButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                //creating a new controller instance
                new Controller().actionPerformed(actionEvent);
// TODO: 11/29/17 so I can get this to work, but am not sure now the method cleanup really works. will have to explore more. maybe make an attribute instance -Blake 
            }
        });

        Scene sc = new Scene(gp,600,400);

        return sc;
    }
}
