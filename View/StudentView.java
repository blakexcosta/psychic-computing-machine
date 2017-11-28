package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class StudentView {

    String[][] sqlData;
    TextField name, userName, department, gradDate, major, role;
    GridPane gp = new GridPane();

    public StudentView(){}

    public Scene makeUserView(){
        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );
        gp.add( new Label("Name: "), 0,0);
        gp.add( new Label("User Name: "), 0,1);
        gp.add( new Label("Department: "), 0,2);
        gp.add( new Label("Graduation Date: "), 0,3);
        gp.add( new Label("Major: "), 0,4);
        gp.add( new Label("Role: "), 0,5);


        Scene sc = new Scene(gp,600,400);

        return sc;
    }
}
