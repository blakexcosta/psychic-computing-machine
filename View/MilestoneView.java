/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

package View;

import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Observable;

public class MilestoneView extends Observable {
    private MySQLDatabase msdb; //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private ComboBox milestoneDropdown;
    private GridPane gp;
    private HashMap<String, String> dropDownVal_ProjectID = new HashMap<String, String>();
    private Label statusLab, nameLab, numberLab, dueDateLab, approvedLab;
    private String currMSID;

    public MilestoneView(MasterView _mv) {
        this.mv = _mv;
        msdb = mv.getMsdb();
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
        gp.addColumn(0, makeMilestoneDropdown(), statusLab, nameLab, numberLab, dueDateLab, approvedLab);

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

    public void switchMilestone(String _msID) {
        //todo: Based on which milestone they choose in the dropdown. Dislay the info for that milestone
        //switch the page to show the passed in milestone number

        ArrayList<ArrayList<String>> rs = msdb.getData("Select StatusCode,Name,Number,DueDate,Approved from milestone where ID in (?)", new ArrayList<String>(Arrays.asList(_msID)));
        int rowCount = 1;
        //put all the new information on the page.clear it first
        gp.getChildren().clear();
        Button editMilestoneButton = new Button("Edit Milestone");

        editMilestoneButton.setOnAction(e -> {
            makeStudentEditMilestonePopup();
        });
        gp.addColumn(0, makeMilestoneDropdown(), statusLab, nameLab, numberLab, dueDateLab, approvedLab,editMilestoneButton);
        for (String curr : rs.get(1)) {
            Label lab = new Label(curr);//make a new label with the DB text
            gp.add(lab, 1, rowCount);
            rowCount++;
            //add the label to the gridpane in the correct spot. col = 1, row = counter
        }
        this.currMSID = _msID;
    }

    public void makeStudentEditMilestonePopup(){
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Input new milestone information");
        Label nameLab = new Label("New Name: ");
        Label summLab = new Label("New Due Date: ");

        TextField inputName = new TextField();
        TextField inputDate = new TextField();
        inputDate.setPromptText("YYYY-MM-DD");

        Button submitButton = new Button("Submit Changes");
        ArrayList<String> milestoneIDAL = new ArrayList<>(Arrays.asList(currMSID));

        submitButton.setOnAction(e -> {
            if (!inputName.getText().isEmpty()) {
                msdb.setData("UPDATE milestone set Name='" + inputName.getText() + "' where ID in (?)", milestoneIDAL);
            }
            if (!inputDate.getText().isEmpty()) {
                msdb.setData("UPDATE milestone set DueDate='" + inputDate.getText() + "' where ID in (?)", milestoneIDAL);
            }
            makeStudentView();
            popupWindow.close();
        });

        gp.addColumn(0, header, nameLab, summLab, submitButton);
        gp.addColumn(1, new Label(), inputName, inputDate);

        popupWindow.show();
    }

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
            //get the value from the combo box and cast it as a a string
            String hashMapKeyStr = (String) milestoneDropdown.getValue(); //this is the value of the drop down. "1,2" need to extract the number
            //pass the ProjectID value from the hashmap
            switchMilestone(dropDownVal_ProjectID.get(hashMapKeyStr));
        });
        return milestoneDropdown;
    }
}
