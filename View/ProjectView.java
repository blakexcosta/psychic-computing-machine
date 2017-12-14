package View;

import Model.MySQLDatabase;
import BusinessLayer.BusinessLayer;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;

public class ProjectView extends Observable {
    private MySQLDatabase msdb; //there is only one instance of the database.
    private BusinessLayer busLayer = new BusinessLayer();
    private MasterView mv;//this is passed in through the constructor
    private GridPane gp;
    private Label mainHeader, labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade;
    private TextField inputName, inputSummary, inputStartDate, inputEndDate, inputDueDate, inputTopic, inputType;
    private Button showMilestonesButton, editInfoButton, committeeInfoButton;
    private ComboBox memberDropdown, roleDropdown;
    private ArrayList<ArrayList<String>> rs;


    public ProjectView(MasterView _mv) {
        this.mv = _mv;
        msdb = mv.getMsdb();
    }

    //TODO: Build out student project view
    public void makeStudentView() {
        Scene sc = mv.getBaseScene();
        //add the css sheet
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        //cast the border pane from the scene so it can be used.
        BorderPane bp = (BorderPane) sc.getRoot();
        //make a grid pane to put data on
        gp = new GridPane();

        if (!loadStudentDBInfo())
            return;//If they have project info it will add it to the page, if not it makes the other view

        //add it to the border pane in the center
        bp.setCenter(gp);
        //set margins
        gp.setHgap(5);
        gp.setVgap(5);
        //position in center of screen
        gp.setAlignment(Pos.CENTER);

        //make header label and add css
        mainHeader = new Label("Project Information");
        mainHeader.getStyleClass().add("mainHeader");

        //add  header the grid pane
        gp.add(mainHeader, 0, 0, 2, 1);

        //add the milestone button
        showMilestonesButton = new Button();
        showMilestonesButton.setText("Show Project Milestones");

        showMilestonesButton.setOnAction(e -> {
            //on click make the milestone view.
            mv.getMilestoneView().makeStudentView();
        });

        editInfoButton = new Button();
        editInfoButton.setText("Edit Project Information");

        editInfoButton.setOnAction(e -> {
            makeStudentEditInfoPopup();
        });

        committeeInfoButton = new Button("Show Committee Information");
        committeeInfoButton.setOnAction(e -> {
            makeStudentCommitteeView();
        });


        gp.add(showMilestonesButton, 0, 6);
        gp.add(editInfoButton, 0, 7);
        gp.add(committeeInfoButton, 0, 8);


        //initialize all of the buttons and add them to the grid
        labName = new Label("Name: ");
        labSummary = new Label("Summary: ");
        labTopic = new Label("Topic: ");
        labDueDate = new Label("Due Date: ");
        labGrade = new Label("Grade: ");
        gp.add(labName, 0, 1);
        gp.add(labSummary, 0, 2);
        gp.add(labTopic, 0, 3);
        gp.add(labDueDate, 0, 4);
        gp.add(labGrade, 0, 5);

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);


    }

    /**
     * returns true or false based on if the user has project informatioh. If not the view needs to be made to add a project
     *
     * @return
     */
    public Boolean loadStudentDBInfo() {
        //todo: why is it erroring to get the project ID for the curr user;
        ArrayList<String> userNameAL = new ArrayList<String>();
        userNameAL.add(mv.getCurrUserName());
        rs = msdb.getData("select * from user_project_link where UserName in (?)", userNameAL);
        for (ArrayList<String> row : rs) {
            mv.setCurrProjectID(row.get(1));
        }

        ArrayList<String> projectIDAL = new ArrayList<>();
        projectIDAL.add(mv.getCurrProjectID());
        rs = msdb.getData("Select Name, Summary, Topic, DueDate, Grade from  project where ID in (?)", projectIDAL);

        if (rs.size() == 1) {//They do not have any project info, so we need to make the view to add a project.
            //TODO: notify the user that they have no projects before the new view is made.
            System.out.println("no projects found");
            makeAddProjectView();
            return false;
        } else {
            //add their project info to the grid pane one row at a time
            int rowCount = 0;
            for (int i = 0; i < rs.get(1).size(); i++) {
                Label lab = new Label(rs.get(1).get(i));
                lab.getStyleClass().add("infoDataLabel");
                gp.add(lab, 1, ++rowCount);
            }

            return true;
        }
    }


    /**
     * This view is made by a call in loadStudentDBInfo.
     * If there is no project info for a student this form will submit to the DB to add a new project for that user
     */
    public void makeAddProjectView() {
        //Main scene and border pane to put everything in
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();
        //Grid pane to store labels and input boxes
        gp = new GridPane();
        bp.setCenter(gp);
        gp.setAlignment(Pos.CENTER);

        //Initialize all the labels that will be put on the screen
        Label header = new Label("Add a new Project");
        labName = new Label("Name: ");
        labSummary = new Label("Summary: ");
        labTopic = new Label("Topic: ");
        labType = new Label("Type: ");
        labStartDate = new Label("Start Date(YYYY-MM-DD): ");
        labEndDate = new Label("End Date(YYYY-MM-DD): ");
        //Make the submit button
        Button submit = new Button("Submit");
        //Button functionality

        submit.setOnAction(e -> {
            //get the new project ID value
            int newMaxID = Integer.parseInt(msdb.getMaxProjectID()) + 1;
            //TODO: check form to validate input
            //query to add to user_project_link
            String projectLinkQuery = "INSERT INTO user_project_link (UserName,ProjectID) VALUES (?,?)";

            //Values to go in user_project_link query
            ArrayList<String> projectLinkVals = new ArrayList<>(Arrays.asList(mv.getCurrUserName(), Integer.toString(newMaxID)));

            //query to add a new project
            String newProjectQuery = "INSERT INTO project (ID,Name,Summary,Topic,Type,StartDate,EndDate,Completed,ProposalApproved)" +
                    " VALUES (?,?,?,?,?,?,?,?,?)";

            //Values to go in new project query
            ArrayList<String> newProjectVals = new ArrayList<>(Arrays.asList(Integer.toString(newMaxID), inputName.getText(), inputSummary.getText(), inputTopic.getText(), inputType.getText(),
                    inputStartDate.getText(), inputEndDate.getText(), "0", "0"));

            //Business layer checks the values
            boolean checkResult = busLayer.checkNewProject(newProjectVals);
            System.out.println("Business Layer Check: " + checkResult);

            //make the two calls to put it into the database
            //TODO: put this in a transaction?
            if (checkResult) {
                msdb.setData(newProjectQuery, newProjectVals);
                msdb.setData(projectLinkQuery, projectLinkVals);
                //store the current projectID in the master view
                mv.setCurrProjectID(Integer.toString(newMaxID));
                makeStudentView();
            }

        });


        //Initialize all of the input fields. Put placeholder text into some of them
        inputName = new TextField();
        inputSummary = new TextField();
        inputTopic = new TextField();
        inputType = new TextField();
        inputType.setPromptText("Capstone or Thesis");
        inputStartDate = new TextField();
        inputStartDate.setPromptText("YYYY-MM-DD");
        inputEndDate = new TextField();
        inputEndDate.setPromptText("YYYY-MM-DD");
        //Add all labels, header, and button in one column
        gp.addColumn(0, header, labName, labSummary, labTopic, labType, labStartDate, labEndDate, submit);

        //Add all input boxes in one column (next to their labels)
        gp.addColumn(1, new Label(""), inputName, inputSummary, inputTopic, inputType, inputStartDate, inputEndDate);
        setChanged();
        notifyObservers(sc);
    }

    private void makeStudentEditInfoPopup() {
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Input new project information");
        Label nameLab = new Label("New Name: ");
        Label summLab = new Label("New Summary: ");
        Label topicLab = new Label("New Topic: ");

        TextField inputName = new TextField();
        TextField inputSumm = new TextField();
        TextField inputTopic = new TextField();

        Button submitButton = new Button("Submit Changes");

        gp.addColumn(0, header, nameLab, summLab, topicLab, submitButton);
        gp.addColumn(1, new Label(), inputName, inputSumm, inputTopic);


        popupWindow.show();

        submitButton.setOnAction(e -> {
            ArrayList<String> projectIDAL = new ArrayList<>(Arrays.asList(mv.getCurrProjectID()));

            if (!inputName.getText().isEmpty()) {
                msdb.setData("UPDATE project set Name='" + inputName.getText() + "' where ID in (?)", projectIDAL);
            }
            if (!inputSumm.getText().isEmpty()) {
                msdb.setData("UPDATE project set Summary='" + inputSumm.getText() + "' where ID in (?)", projectIDAL);
            }
            if (!inputTopic.getText().isEmpty()) {
                msdb.setData("UPDATE project set Topic='" + inputTopic.getText() + "' where ID in (?)", projectIDAL);
            }
            makeStudentView();
            popupWindow.close();
        });


    }

    private void makeStudentCommitteeView() {
        Scene sc = mv.getBaseScene();
        //add the css sheet
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        //cast the border pane from the scene so it can be used.
        BorderPane bp = (BorderPane) sc.getRoot();
        //make a grid pane to put data on
        gp = new GridPane();
        bp.setCenter(gp);
        gp.setAlignment(Pos.CENTER);
        gp.setHgap(5);
        gp.setVgap(5);

        //TODO: If checkUserWaitingOnNotifications is true then it should pop up a box. All professors they are waiting on and a button to email them.
        //Professor Name - [Click to send email notification]
        //If they are waiting on committee notifications make a box to email the prof

        Label committeeHeader = new Label("Committee information");
        Button addMemberButton = new Button("Add a committee member");

        addMemberButton.setOnAction(e -> {
            makeAddMemberPopup();
        });
        gp.add(committeeHeader, 0, 0);

        if (checkCommiteeMems()) {
            //They do have committee members. Display them and their role. THEN the button to add
            rs = msdb.getData("Select UserName, Role from committee where ProjectID in (?)",
                    new ArrayList<String>(Arrays.asList(mv.getCurrProjectID())));
            int rowCount = 0;
            for (ArrayList<String> curr : rs) {
                if (rowCount == 0){}
                else {
                    Label lab = new Label(curr.get(1) + ": " + curr.get(0));
                    gp.add(lab,0,rowCount);
                }
                rowCount++;
            }
            gp.add(addMemberButton,0,rowCount);

        } else {//they do not have any committee members. only add the button to add new members
            gp.add(addMemberButton, 0, 1);
        }
        setChanged();
        notifyObservers(sc);
        if (msdb.checkUserWaitingOnNotifications(mv.getCurrUserName(),"committee")){makeEmailNotifyPopup();}


    }

    /**
     * This is only made if the current user is waiting on committee members to accept (or decline).
     * Clicking the button will send an email to the professor.
     */
    private void makeEmailNotifyPopup(){
        //Make inital popup stuff
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);

        //Only val added is the current username
        ArrayList<String> queryVals = new ArrayList<>();
        queryVals.add(mv.getCurrUserName());
        rs = msdb.getData("SELECT NotifiedUserName from user_notifications where NotifierUserName in (?) and Approved is null and NotificationType in ('committee')",queryVals);

        //make the header and put it on top.
        Label Header = new Label("Click the button to send an email notification");
        gp.add(Header,0,0);
        int rowCount = 0;

        //Loop through all professors that have not approved notifications. add their username and a button to email them.
        for (ArrayList<String> curr:rs){
            if (rowCount == 0){}
            else{
                Button btn = new Button("NOTIFY");
                btn.setOnAction(e -> {
                    System.out.println("call email function from MV");
                });
                Label lab = new Label(curr.get(0));
                gp.add(lab,0,rowCount);
                gp.add(btn,1,rowCount);
            }
            rowCount++;
        }
        //show the window
        popupWindow.show();
    }

    /**
     * Checks through all of the project IDs that have committee members.
     * If this projectID is one of them it returns true
     * @return
     */
    private Boolean checkCommiteeMems() {
        rs = msdb.getData("select ProjectID from committee", new ArrayList<>());
        for (ArrayList<String> curr : rs) {
            if (curr.get(0).equals(mv.getCurrProjectID())) return true;
        }
        return false;
    }

    private void makeAddMemberPopup() {
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Choose a commitee member from the dropdown and click add member to notify them.");

        roleDropdown = new ComboBox<String>();
        roleDropdown.getItems().addAll("chair","reader","other");

        Button addMemberButton = new Button("Add to committee");

        addMemberButton.setOnAction(e -> {
            //todo: not sure if we want to notify the professor to add the student or write query to actually add them?abab
            ArrayList<String> notificationValsAL = new ArrayList<>();
            //The username to be notified.
            //todo: this is their actual name(because that is the val in the dropdown) it should be their username.
            notificationValsAL.add(nameToUserName((String) memberDropdown.getValue()));
            //the user name of the person sending a notification
            notificationValsAL.add(mv.getCurrUserName());
            //The type can be hard coded because it is being set in the committee window
            notificationValsAL.add("committee");
            //the current user wants to add you as a member of their committee
            notificationValsAL.add(mv.getCurrUserName() + " wants you to be on their committee");
            msdb.setData("Insert into user_notifications (NotifiedUserName,NotifierUserName,NotificationType,NotificationDesc,Approved) VALUES (?,?,?,?,null)",notificationValsAL);
            makeStudentCommitteeView();
            popupWindow.close();
        });
        gp.add(header, 0, 0);
        gp.add(roleDropdown, 0, 1);
        gp.add(makeMemberOptionDropdown(), 0, 2);
        gp.add(addMemberButton, 0, 3);
        popupWindow.show();


    }

    /**
     * Makes the combo box that is put at the top when adding a staff or faculty member to your committee.
     * @return
     */
    private ComboBox makeMemberOptionDropdown() {
        memberDropdown = new ComboBox<String>();
        ArrayList<String> memberOptions;
        rs = msdb.getData("SELECT CONCAT(FirstName, ' ', LastName) as 'Name' FROM user WHERE role IN ('staff','faculty')",new ArrayList<>());
        boolean header = true;
        for (ArrayList<String> curr : rs){
            if (header){header = false;}
            else{
                memberDropdown.getItems().add(curr.get(0));
            }
        }
        return memberDropdown;
    }

    /**
     * helper used to convert a Full Name to a user name EX: Gavin Drabik -> grd2747.
     * @param _name to be converted
     * @return
     */
    private String nameToUserName(String _name){
        //Arraylist is the first name and last name split
        ArrayList<String> queryVals = new ArrayList<>(Arrays.asList(_name.split(" ")));
        //Get the username for that first and last name
        rs = msdb.getData("SELECT UserName from user where FirstName in (?) and LastName in (?)",queryVals);
        //return the username
        return rs.get(1).get(0);

    }

    //TODO: Build out staff project view
    public void makeStaffView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view

        setChanged();
        notifyObservers(sc);
    }

    //TODO: Build out faculty project view
    public void makeFacultyView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }


}