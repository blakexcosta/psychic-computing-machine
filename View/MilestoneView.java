package View;

import Model.MySQLDatabase;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;

import java.util.Observable;

public class MilestoneView extends Observable{
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private ComboBox milestoneDropdown;

    //TODO: Build out student milestoneView
    public void makeStudentView(){
        //make the scene and store everyting in this object
        //You have access to the database using the msdb object (but it would be a good idea to break things into separate methods)
        Scene returnScene = null;

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(returnScene);
    }
     //TODO: Build out staff milestoneView
    public void makeStaffView(){
        //make the scene and store everyting in this object
        //You have access to the database using the msdb object (but it would be a good idea to break things into separate methods)
        Scene returnScene = null;

        //After the scene is made completely these two methods run which will update the master view to our new view

        setChanged();
        notifyObservers(returnScene);
    }
    //TODO: Build out faculty milestoneView
    public void makeFacultyView(){
        //make the scene and store everyting in this object
        //You have access to the database using the msdb object (but it would be a good idea to break things into separate methods)
        Scene returnScene = null;

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(returnScene);
    }

    //TODO: Functionality to switch between milestones based on value of dropdown.
    public void switchMilestone(int msNum){
        //switch the page to show the passed in milestone number

        //make a msdb query to get milestone info where milestoneNumber = msNum;
    }

    //TODO: Finish functionality to make dropdown
    public void makeMilestoneDropdown(){
        milestoneDropdown = new ComboBox<String>();
        //how ever many milestones there are, that should be the length of the combo box
        milestoneDropdown.getItems().add("Milestone (loop counter)");

        //this fires when the dropdown changes.
        milestoneDropdown.setOnAction(e ->{
            String newMilestoneStr = (String) milestoneDropdown.getValue(); //this is the value of the drop down. "milestone x" need to extract the number
            int newMilestoneInt = Integer.parseInt(newMilestoneStr.substring(-1));//Get the last character of the string (should be milestone num) and change it to an int
            switchMilestone(newMilestoneInt);
        });

    }
}
