package View;

import Model.MySQLDatabase;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Array;
import java.util.Arrays;
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

    public void switchMilestone(String msNum){
        //todo: Based on which milestone they choose in the dropdown. Dislay the info for that milestone
        //switch the page to show the passed in milestone number

        String[][] rs = msdb.getData("Select StatusCode,Name,Number,DueDate,Approved from milestone where ID in (?)",new String[]{msNum});
        for (String curr : rs[1]){
            Label lab = new Label(curr);//make a new label with the DB text
            //add the label to the gridpane in the correct spot. col = 1, row = counter

        }
        //make a msdb query to get milestone info where milestoneNumber = msNum;
    }

    //TODO: Finish functionality to make dropdown
    public ComboBox makeMilestoneDropdown(){
        milestoneDropdown = new ComboBox<String>();
        //how ever many milestones there are, that should be the length of the combo box
        String[] projectIDStr = new String[1];
        projectIDStr[0] = mv.getCurrProjectID();
        //TODO: The dropdown should increment until the number of milestones they have.
        String[][] rs = msdb.getData("select MIN(MilestoneID), 'makeItWork' from project_milestone_link where ProjectID in (?)", projectIDStr);
        System.out.println(Arrays.deepToString(rs));
        for (String curr : rs[1]){
            System.out.println(curr);
        }

        milestoneDropdown.getItems().add("Milestone (loop counter)");

        //this fires when the dropdown changes.
        milestoneDropdown.setOnAction(e ->{
            String newMilestoneStr = (String) milestoneDropdown.getValue(); //this is the value of the drop down. "milestone x" need to extract the number
            newMilestoneStr = newMilestoneStr.substring(-1);//Get the last character of the string (should be milestone num) and change it to an int
            switchMilestone(newMilestoneStr);
        });
        return milestoneDropdown;
    }
}
