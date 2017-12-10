package View;

import Controller.BusinessLayerLogin;
import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

import static javafx.fxml.FXMLLoader.load;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

// TODO: 12/8/17 Document and Fix indentation -Blake 
public class StudentInfoView extends Observable{
    //both of these are in ALL the view classes
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor

    private String[][] sqlData;
    private TextField name, userName, department, gradDate, major, role;
    private GridPane gp = new GridPane();
    private String[][] returnData;
  
    public StudentInfoView(MasterView _mv){
        this.mv = _mv;
    }

    public void makeView(){

        BorderPane root = new BorderPane(); //Main layout is a border pane
        Scene sc = new Scene(root,1366,768);//put main pane is a scene
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files

        HBox menuButtons = makeMenuButtons("User");//Use function to make the menu buttons. Decides which to show as clicked.

        root.setTop(menuButtons);//Put menu buttons at top of page
        root.setCenter(gp);//set center of page to grid pane (class attribute)
        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );
        gp.setPrefWidth(600);
        gp.setPrefHeight(800);


        Label userInfoHeaderLabel = new Label("User Information ");//Bigger header on top
        userInfoHeaderLabel.getStyleClass().add("mainHeader");
        userInfoHeaderLabel.setAlignment(Pos.CENTER);

        gp.add( userInfoHeaderLabel, 0,0,2,1);
        gp.add( new Label("Name: "), 0,1);
        gp.add( new Label("User Name: "), 0,2);
        gp.add( new Label("Department: "), 0,3);
        gp.add( new Label("Graduation Date: "), 0,4);
        gp.add( new Label("Major: "), 0,5);
        gp.add( new Label("Role: "), 0,6);
        gp.gridLinesVisibleProperty().setValue(true);

        loadDBInfo();//this sets the labels to the proper info using the local class instance of MSDB
        //loop through each of the user fields that we have and put them next to the label. They come in in the correct order
        setChanged();
        notifyObservers(sc);
    }

    public void loadDBInfo(){
        String[] userNameAL = {msdb.getUserName()};
        returnData = msdb.getData("SELECT CONCAT(FirstName, ' ',  LastName) as 'Name' , UserName, Department, GraduationDate,Major, Role FROM user where UserName in (?)",userNameAL);
        System.out.println(convertToAL(returnData));
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

    public ArrayList<String> convertToAL(String [][] arr){
        ArrayList<String> lst = new ArrayList<>();
        for (String[] array : arr){
            lst.addAll(Arrays.asList(array));
        }
        return lst;
    }
}
