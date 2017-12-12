package View;

import Model.MySQLDatabase;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Observable;

public class MilestoneView extends Observable{
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private ComboBox milestoneDropdown;

    public MilestoneView(MasterView _mv){
        this.mv = _mv;
    }

    //TODO: Build out student milestoneView
    public void makeStudentView(){
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar.
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();
        GridPane gp = new GridPane();
        bp.setCenter(gp);

        gp.add(makeMilestoneDropdown(),0,0);


        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }
     //TODO: Build out staff milestoneView
    public void makeStaffView(){
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view

        setChanged();
        notifyObservers(sc);
    }
    //TODO: Build out faculty milestoneView
    public void makeFacultyView(){
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    //TODO: Functionality to switch between milestones based on value of dropdown.
    public void switchMilestone(int msNum){
        //switch the page to show the passed in milestone number

        //make a msdb query to get milestone info where milestoneNumber = msNum;
    }

    //TODO: Finish functionality to make dropdown
    public ComboBox makeMilestoneDropdown(){
        milestoneDropdown = new ComboBox<String>();
        //how ever many milestones there are, that should be the length of the combo box
        String[][] rs = msdb.getData("select MilestoneID from project_milestone_link where ProjectID in (?)", new String[] {mv.getCurrProjectID()});
        for (String curr : rs[1]){
            System.out.println(curr);
        }

        milestoneDropdown.getItems().add("Milestone (loop counter)");

        //this fires when the dropdown changes.
        milestoneDropdown.setOnAction(e ->{
            String newMilestoneStr = (String) milestoneDropdown.getValue(); //this is the value of the drop down. "milestone x" need to extract the number
            int newMilestoneInt = Integer.parseInt(newMilestoneStr.substring(-1));//Get the last character of the string (should be milestone num) and change it to an int
            switchMilestone(newMilestoneInt);
        });
        return milestoneDropdown;
    }
}
