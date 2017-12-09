package View;

import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

// TODO: 12/8/17 Add Documentation and Fix Indentation -Blake
public class StaffView {
    // TODO: 12/8/17 Make these attributes private -Blake
    String[][] sqlData;
    TextField name, userName, department, gradDate, major, role;
    GridPane gp = new GridPane();
    String[][] rs;

    public StaffView(){}

    public Scene makeUserView(){

        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );


        gp.add( new Label("Name: "), 0,0);
        gp.add( new Label("User Name: "), 0,1);
        gp.add( new Label("Department: "), 0,2);
        gp.add( new Label("Role: "), 0,3);


        Scene sc = new Scene(gp,600,400);

        return sc;
    }
}
