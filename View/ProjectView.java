package View;

import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Observable;

public class ProjectView extends Observable {
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private GridPane gp;
    private Label mainHeader, labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade;


    public ProjectView(MasterView _mv){
        this.mv = _mv;
    }
    //TODO: Build out student project view
    public void makeStudentView(){
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        BorderPane bp = (BorderPane) sc.getRoot();
        gp = new GridPane();
        bp.setCenter(gp);
        gp.setHgap(5);
        gp.setVgap(5);
        gp.setAlignment(Pos.CENTER);

        mainHeader = new Label("Project Information");
        mainHeader.getStyleClass().add("mainHeader");


        gp.add(mainHeader,0,0);

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }
    //TODO: Build out staff project view
    public void makeStaffView(){
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view

        setChanged();
        notifyObservers(sc);
    }
    //TODO: Build out faculty project view
    public void makeFacultyView(){
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }
}
