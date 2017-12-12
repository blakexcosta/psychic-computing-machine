package View;

import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

public class MilestoneView extends Observable {
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private ComboBox milestoneDropdown;
    private GridPane gp;
    private HashMap<String, String> dropDownVal_ProjectID = new HashMap<String, String>();
    private Label statusLab,nameLab,numberLab,dueDateLab,approvedLab;

    public MilestoneView(MasterView _mv) {
        this.mv = _mv;
    }

    //TODO: Build out student milestoneView
    public void makeStudentView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar.
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();
        gp = new GridPane();
        bp.setCenter(gp);
        //set margins between cells
        gp.setHgap(5);
        gp.setVgap(10);
        //center the grid pane on the page
        gp.setAlignment(Pos.CENTER);
        //Make the dropdown with their information

        statusLab = new Label("Status Code: ");
        nameLab = new Label("Milestone Name: ");
        numberLab = new Label("Milestone Number: ");
        dueDateLab = new Label("Milestone Due Date: ");
        approvedLab = new Label("Approval Status: ");
        gp.addColumn(0, makeMilestoneDropdown(),statusLab,nameLab,numberLab,dueDateLab,approvedLab);


        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    //TODO: Build out staff milestoneView
    public void makeStaffView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view

        setChanged();
        notifyObservers(sc);
    }

    //TODO: Build out faculty milestoneView
    public void makeFacultyView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    public void switchMilestone(String msNum) {
        //todo: Based on which milestone they choose in the dropdown. Dislay the info for that milestone
        //switch the page to show the passed in milestone number

        ArrayList<ArrayList<String>> rs = msdb.getData("Select StatusCode,Name,Number,DueDate,Approved from milestone where ID in (?)", new ArrayList<String>(Arrays.asList(msNum)));
        System.out.println(rs);
        int rowCount=1;
        //put all the new information on the page.clear it first
        gp.getChildren().clear();
        gp.addColumn(0, makeMilestoneDropdown(),statusLab,nameLab,numberLab,dueDateLab,approvedLab);
        for (String curr : rs.get(1)) {
            Label lab = new Label(curr);//make a new label with the DB text
            gp.add(lab,1,rowCount);
            rowCount++;
            //add the label to the gridpane in the correct spot. col = 1, row = counter
        }
        //make a msdb query to get milestone info where milestoneNumber = msNum;
    }

    //TODO: Finish functionality to make dropdown
    public ComboBox makeMilestoneDropdown() {
        milestoneDropdown = new ComboBox<String>();
        //how ever many milestones there are, that should be the length of the combo box
        ArrayList<String> projectIDAL = new ArrayList<>(Arrays.asList(mv.getCurrProjectID()));

        //get all of the milestoneIDs that are associated with the current project
        ArrayList<ArrayList<String>> rs = msdb.getData("select MilestoneID from project_milestone_link where ProjectID in (?)", projectIDAL);

        //Map each milestone id to the corresponding number (from 1 to the length) and put it in the combo box
        int counter = 0;
        for (ArrayList<String> curr : rs) {
            if (counter == 0) {//do nothing because its the header
            } else {
                //add number to the dropdown.
                milestoneDropdown.getItems().add(Integer.toString(counter));
                //map dropdown number to milestoneID value
                dropDownVal_ProjectID.put(Integer.toString(counter), curr.get(0));
            }
            counter++;

        }
        //map of Key: dropdownNumber value: projectID
        //this fires when the dropdown changes.
        milestoneDropdown.setOnAction(e -> {
            String hashMapKeyStr = (String) milestoneDropdown.getValue(); //this is the value of the drop down. "milestone x" need to extract the number
            hashMapKeyStr = hashMapKeyStr.substring(hashMapKeyStr.length() - 1);//Get the last character of the string (should be milestone num) and change it to an int
            //todo: should be passing a string that is a single number. (1,2,3,4,5...)
            switchMilestone(dropDownVal_ProjectID.get(hashMapKeyStr));
        });
        return milestoneDropdown;
    }
}
