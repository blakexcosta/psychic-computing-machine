package View;

import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

import java.util.Observable;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

// TODO: 12/8/17 Document and Fix indentation -Blake 
public class InfoView extends Observable {
    //both of these are in ALL the view classes
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor

    private String[][] sqlData;
    private TextField name, userName, department, gradDate, major, role;
    private GridPane gp = new GridPane();
    private String[][] returnData;

    public InfoView(MasterView _mv) {
        this.mv = _mv;
    }

    //TODO: Build out student info view (make it look nice. also add the information in loadStudentDBInfo)
    public void makeStudentView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files

        Label userInfoHeaderLabel = new Label("User Information ");//Bigger header on top
        userInfoHeaderLabel.getStyleClass().add("mainHeader");
        userInfoHeaderLabel.setAlignment(Pos.CENTER);

        bp.setCenter(gp);//set center of page to grid pane (class attribute)
        gp.setHgap(5);
        gp.setVgap(5);
        gp.setAlignment(Pos.CENTER);
        gp.setPrefWidth(600);
        gp.setPrefHeight(800);

        gp.add(userInfoHeaderLabel, 0, 0, 2, 1);
        gp.add(new Label("Name: "), 0, 1);
        gp.add(new Label("User Name: "), 0, 2);
        gp.add(new Label("Department: "), 0, 3);
        gp.add(new Label("Graduation Date: "), 0, 4);
        gp.add(new Label("Major: "), 0, 5);
        gp.add(new Label("Role: "), 0, 6);
        gp.gridLinesVisibleProperty().setValue(true);

        loadStudentDBInfo();//this sets the labels to the proper info using the local class instance of MSDB
        //loop through each of the user fields that we have and put them next to the label. They come in in the correct order
        setChanged();
        notifyObservers(sc);
    }

    /**
     * gets all of the data to be put next to the labels in the grid.
     */
    public void loadStudentDBInfo() {
        String[] userNameAL = {msdb.getUserName()};
        returnData = msdb.getData("SELECT CONCAT(FirstName, ' ',  LastName) as 'Name' , UserName, Department, GraduationDate,Major, Role FROM user where UserName in (?)", userNameAL);
    }

    //TODO: Build out staff info view
    public void makeStaffView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    //TODO: Build out faculty info view
    public void makeFacultyView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely, these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }


}