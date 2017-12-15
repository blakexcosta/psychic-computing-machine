/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

package View;

import Model.MySQLDatabase;
import BusinessLayer.BusinessLayer;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Observable;
import java.util.HashMap;

public class ProjectView extends Observable {
    private MySQLDatabase msdb; //there is only one instance of the database.
    private BusinessLayer busLayer = new BusinessLayer();
    private MasterView mv;//this is passed in through the constructor
    private GridPane gp;
    private Label mainHeader, labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade, labApproved;
    private TextField inputName, inputSummary, inputStartDate, inputEndDate, inputDueDate, inputTopic, inputType;
    private Button showMilestonesButton, editInfoButton, committeeInfoButton, deleteProjectButton, showMoreInfoButton, showLessInfoButton;
    private ComboBox memberDropdown, staffProjectDropdown;
    private ArrayList<ArrayList<String>> rs;
    private ArrayList<ArrayList<String>> moreInfoArray; //used to get more information from the project, used when a button is clicked.
    private HashMap<String, String> dropDownVal_ProjectID = new HashMap<String, String>();
    // TODO: 12/14/17 8. private accessor to get information.
    private final Group moreInfoGroup = new Group();

    public ProjectView(MasterView _mv) {
        this.mv = _mv;
        msdb = mv.getMsdb();
    }

    // TODO: 12/14/17 Start here -blake 
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
            // TODO: 12/14/17 2.
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

        //delete the project
        deleteProjectButton = new Button("Delete Project");
        deleteProjectButton.setOnAction(e -> {
            deleteConfirmPopup();
        });

        // TODO: 12/14/17 3. after here, add button
        //add a new show more info button, then when populated remove info
        showMoreInfoButton = new Button("More Information");
        showMoreInfoButton.setOnAction(e -> {
            showMoreInfo();
        });

        //show less info.
        showLessInfoButton = new Button("Less Informaiton");
        showLessInfoButton.setOnAction(e -> {
            showLessInfo();
        });

        gp.add(showMilestonesButton, 0, 8);
        gp.add(editInfoButton, 0, 9);
        gp.add(committeeInfoButton, 0, 10);
        gp.add(deleteProjectButton, 0, 11);
        //adding show more info to the pane.
        gp.add(showMoreInfoButton, 1, 11);
        //adding less info to the gridpane
        gp.add(showLessInfoButton, 1, 12);
        //project progress bar
        //get data
        ArrayList<String> projectIDAL = new ArrayList<>();
        projectIDAL.add(mv.getCurrProjectID());
        rs = msdb.getData("SELECT MAX(StatusCode) FROM milestone JOIN project_milestone_link on (milestone.ID = project_milestone_link.MilestoneID) WHERE project_milestone_link.ProjectID in (?)", projectIDAL);
        String maxStatus = rs.get(1).get(0).toString();
        System.out.println("MAX STATUS CODE: " + maxStatus);
        double maxStatusNum = Double.parseDouble(maxStatus);
        double progressBarVal = (maxStatusNum / 1600);
        ProgressBar projectStatusBar = new ProgressBar();
        System.out.println("Progress Bar Value: " + progressBarVal);
        projectStatusBar.setProgress(progressBarVal);

        //initialize all of the buttons and add them to the grid
        labName = new Label("Name: ");
        labSummary = new Label("Summary: ");
        labTopic = new Label("Topic: ");
        labDueDate = new Label("Due Date: ");
        labGrade = new Label("Grade: ");
        labApproved = new Label("Approved: ");
        gp.add(labName, 0, 1);
        gp.add(labSummary, 0, 2);
        gp.add(labTopic, 0, 3);
        gp.add(labDueDate, 0, 4);
        gp.add(labGrade, 0, 5);
        gp.add(labApproved, 0, 6);
        gp.add(projectStatusBar, 0, 7, 2, 1);
        ColumnConstraints col1Style = new ColumnConstraints();
        col1Style.setPercentWidth(20);
        gp.getColumnConstraints().add(col1Style);

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);


    }

    /**
     * Show more information about the project, has a private method to remove information as well
     */
    // TODO: 12/14/17 5 
    public void showMoreInfo() {
        System.out.println("Show more information button clicked.");
        //get information from the database with the required information
        //store it
        //called and stored from the loadStudentDBInfo method, when that is fired.
        if (moreInfoArray.size() == 1) {//They do not have any project info, so we need to make the view to add a project.
            //TODO: notify the user that they have no projects before the new view is made.
            System.out.println("nothing found");
        } else {
            //int rowCount = gp.getScene().getRoot().getS;
            System.out.println("length before population" + gp.getChildren().size());
            int rowCount = 13;
            for (int i = 0; i < moreInfoArray.get(1).size(); i++) {
                Label lab = new Label(moreInfoArray.get(1).get(i));
                lab.getStyleClass().add("infoDataLabel");
                gp.add(lab, 1, rowCount++); //added to the gridpane.
                //moreInfoGroup.getChildren().add(lab);
                // TODO: 12/14/17 9, may have to add scrollpane -Blake
            }
            //for each child, add it to the pane.
            //gp.add(moreInfoGroup,0, 13);
            //add group of children to the pane

        }
        //send it to the view
    }

    /**
     * When the show less info button is clicked it remakes the standard view for whatever role we currently have
     */
    public void showLessInfo() {
        gp.getChildren().clear();
        if (msdb.getRole().equals("student")) {
            makeStudentView();
        }
        if (msdb.getRole().equals("staff")) {
            makeStaffView();
        }
        if (msdb.getRole().equals("faculty")) {
            makeFacultyView();
        }
    }

    /**
     * returns true or false based on if the user has project informatioh. If not the view needs to be made to add a project
     *
     * @return
     */
    // TODO: 12/14/17 6. look at for reference 
    public Boolean loadStudentDBInfo() {
        ArrayList<String> userNameAL = new ArrayList<String>();
        userNameAL.add(mv.getCurrUserName());
        rs = msdb.getData("select * from user_project_link where UserName in (?)", userNameAL);
        for (ArrayList<String> row : rs) {
            mv.setCurrProjectID(row.get(1));
        }

        ArrayList<String> projectIDAL = new ArrayList<>();
        projectIDAL.add(mv.getCurrProjectID());
        rs = msdb.getData("Select Name, Summary, Topic, DueDate, Grade,ProposalApproved from  project where ID in (?)", projectIDAL);
        // TODO: 12/14/17 7. make another call here to get more info.
        moreInfoArray = msdb.getData("SELECT * FROM project WHERE ID IN (?)", projectIDAL); //populating a new array.

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
                lab.setWrapText(true);
                gp.add(lab, 1, ++rowCount);
            }

            return true;
        }
    }

    public ComboBox loadStaffDBInfo() {
        ArrayList<String> blankAL = new ArrayList<String>();
        rs = msdb.getData("SELECT name FROM project", blankAL);
        staffProjectDropdown = new ComboBox<String>();

        int counter = 0;
        for (ArrayList<String> curr : rs) {
            if (counter == 0) {//do nothing because its the header
            } else {
                //add number to the dropdown.
                staffProjectDropdown.getItems().add(Integer.toString(counter));
                //map dropdown number to milestoneID value
                dropDownVal_ProjectID.put(Integer.toString(counter), curr.get(0));
            }
            counter++;
        }

        staffProjectDropdown.setOnAction(e -> {
            //get the value from the combo box and cast it as a a string
            String hashMapKeyStr = (String) staffProjectDropdown.getValue(); //this is the value of the drop down. "1,2" need to extract the number
            //pass the ProjectID value from the hashmap
            switchProject(dropDownVal_ProjectID.get(hashMapKeyStr));
        });

        return staffProjectDropdown;
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


    /**
     * This is the popup to edit project info.
     * This gets called when the usr presses the edit button in the project view
     */
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

            ArrayList<String> newProjectVals = new ArrayList<>(Arrays.asList(inputName.getText(), inputSumm.getText(), inputTopic.getText()));

            //Business layer checks the values
            boolean checkResult = busLayer.checkEditProject(newProjectVals);
            System.out.println("Business Layer Check: " + checkResult);

            if (checkResult) {
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
            } else {
                makeStudentView();
                popupWindow.close();
                popupWindow.show();
            }
        });


    }

    /**
     * This makes the committee view for the student.
     * It has a logic check to make sure the project has been approved before displaying committee info
     */
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

        Label committeeHeader = new Label("Committee information");
        Button addMemberButton = new Button("Add a committee member");

        addMemberButton.setOnAction(e -> {
            makeAddMemberPopup();
        });
        gp.add(committeeHeader, 0, 0);

        //If they have committee members and the project is approved put them all on the page
        if (checkCommiteeMems() && msdb.checkProjectApproved(mv.getCurrProjectID())) {
            //They do have committee members. Display them and their role. THEN the button to add
            rs = msdb.getData("Select UserName, Role from committee where ProjectID in (?)",
                    new ArrayList<String>(Arrays.asList(mv.getCurrProjectID())));
            int rowCount = 0;
            for (ArrayList<String> curr : rs) {
                if (rowCount == 0) {
                } else {
                    Label lab = new Label(curr.get(1) + ": " + curr.get(0));
                    gp.add(lab, 0, rowCount);
                }
                rowCount++;
            }
            gp.add(addMemberButton, 0, rowCount);

        } else {//they do not have any committee members or project is not approved. Do another check to see which failed

            if (!msdb.checkProjectApproved(mv.getCurrProjectID())) {//Project is not approved. Show page that says that
                gp.add(new Label("Project is not yet approved"), 0, 1);
            } else {//The project is approved but there are not committee mems
                gp.add(addMemberButton, 0, 1);
            }
        }
        setChanged();
        notifyObservers(sc);
        //after the page is made. Check if they have any committee members to notify
        if (msdb.checkUserWaitingOnNotifications(mv.getCurrUserName(), "committee")) {
            makeEmailNotifyPopup();
        }
    }

    /**
     * This is only made if the current user is waiting on committee members to accept (or decline).
     * Clicking the button will send an email to the professor.
     */
    private void makeEmailNotifyPopup() {
        //Make inital popup stuff
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);

        //Only val added is the current username
        ArrayList<String> queryVals = new ArrayList<>();
        queryVals.add(mv.getCurrUserName());
        rs = msdb.getData("SELECT NotifiedUserName from user_notifications where NotifierUserName in (?) and Approved is null and NotificationType in ('committee')", queryVals);

        //make the header and put it on top.
        Label Header = new Label("Click the button to send an email notification");
        gp.add(Header, 0, 0);
        int rowCount = 0;

        //Loop through all professors that have not approved notifications. add their username and a button to email them.
        for (ArrayList<String> curr : rs) {
            if (rowCount == 0) {
            } else {
                Button btn = new Button("NOTIFY");
                btn.setOnAction(e -> {
                    System.out.println("call email function from MV");
                });
                Label lab = new Label(curr.get(0));
                gp.add(lab, 0, rowCount);
                gp.add(btn, 1, rowCount);
            }
            rowCount++;
        }
        //show the window
        popupWindow.show();
    }

    /**
     * Checks through all of the project IDs that have committee members.
     * If this projectID is one of them it returns true
     *
     * @return
     */
    private Boolean checkCommiteeMems() {
        rs = msdb.getData("select ProjectID from committee", new ArrayList<>());
        for (ArrayList<String> curr : rs) {
            if (curr.get(0).equals(mv.getCurrProjectID())) return true;
        }
        return false;
    }

    /**
     * Makes the popup to add a nother member to a committee.
     * When a student chooses someone to add it will make a notification for them from this student.
     */
    private void makeAddMemberPopup() {
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Choose a commitee member from the dropdown and click add member to notify them.");

        memberDropdown = new ComboBox<String>();
        memberDropdown.getItems().addAll("chair", "reader", "other");

        Button addMemberButton = new Button("Add to committee");

        addMemberButton.setOnAction(e -> {
            ArrayList<String> notificationValsAL = new ArrayList<>();
            //The username to be notified.
            notificationValsAL.add(nameToUserName((String) memberDropdown.getValue()));
            //the user name of the person sending a notification
            notificationValsAL.add(mv.getCurrUserName());
            //The type can be hard coded because it is being set in the committee window
            notificationValsAL.add("committee");
            //the current user wants to add you as a member of their committee
            notificationValsAL.add(mv.getCurrUserName() + " wants you to be on their committee");
            msdb.setData("Insert into user_notifications (NotifiedUserName,NotifierUserName,NotificationType,NotificationDesc,Approved) VALUES (?,?,?,?,null)", notificationValsAL);
            makeStudentCommitteeView();
            popupWindow.close();
        });
        gp.add(header, 0, 0);
        gp.add(memberDropdown, 0, 1);
        gp.add(makeMemberOptionDropdown(), 0, 2);
        gp.add(addMemberButton, 0, 3);
        popupWindow.show();


    }

    /**
     * Makes the combo box that is put at the top when adding a staff or faculty member to your committee.
     *
     * @return
     */
    private ComboBox makeMemberOptionDropdown() {
        memberDropdown = new ComboBox<String>();
        ArrayList<String> memberOptions;
        rs = msdb.getData("SELECT CONCAT(FirstName, ' ', LastName) as 'Name' FROM user WHERE role IN ('staff','faculty')", new ArrayList<>());
        boolean header = true;
        for (ArrayList<String> curr : rs) {
            if (header) {
                header = false;
            } else {
                memberDropdown.getItems().add(curr.get(0));
            }
        }
        return memberDropdown;
    }

    /**
     * helper used to convert a Full Name to a user name EX: Gavin Drabik -> grd2747.
     *
     * @param _name to be converted
     * @return
     */
    private String nameToUserName(String _name) {
        //Arraylist is the first name and last name split
        ArrayList<String> queryVals = new ArrayList<>(Arrays.asList(_name.split(" ")));
        //Get the username for that first and last name
        rs = msdb.getData("SELECT UserName from user where FirstName in (?) and LastName in (?)", queryVals);
        //return the username
        return rs.get(1).get(0);

    }

    //TODO: Build out staff project view
    public void makeStaffView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        //sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());
        BorderPane bp = (BorderPane) sc.getRoot();
        //Make a grid pane to put data on
        gp = new GridPane();

        ComboBox projectsComboBox = loadStaffDBInfo();

        bp.setCenter(gp);
        gp.setHgap(5);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);

        //labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade, labApproved

        mainHeader = new Label("All Projects");
        mainHeader.getStyleClass().add("mainHeader");
        labName = new Label("Name: ");
        labSummary = new Label("Summary: ");
        labTopic = new Label("Type: ");
        labStartDate = new Label("Start Date: ");
        labEndDate = new Label("Due Date: ");
        labDueDate = new Label("End Date: ");
        labGrade = new Label("Grade: ");
        labApproved = new Label("Approved? ");

        gp.addColumn(0, projectsComboBox, mainHeader, labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade, labApproved);

        //gp.add(mainHeader, 0, 0, 2, 1);

        //gp.add(projectsComboBox, 0, 1);

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
        gp = new GridPane();
        bp.setCenter(gp);
        gp.setHgap(5);
        gp.setVgap(10);
        gp.setAlignment(Pos.CENTER);

        ArrayList<String> userNameAL = new ArrayList<>(Arrays.asList(mv.getCurrUserName()));
        rs = msdb.getData("SELECT ProjectID from committee where UserName in (?)", userNameAL);

        if (rs.size() == 1) {
            Label noComLab = new Label("You are not currently on any committees");
            gp.add(noComLab,0,0);

        } else {
            //todo: make a dropdown of all the possible projects (loop through rs).
            ComboBox<String> projectOptionsDropdown = makeFacultyProjOptionsDropdown();
            // hen the project is chosen display that info.
        }


        //this pops up a window so if you are having difficulties just comment it out and I can finish up this part - Gavin
        if (msdb.checkUserHasNotifications(mv.getCurrUserName(), "committee")) {
            makeFacultyNotifactionPopup();
            System.out.println("Professor has notifications to be added to a committee");
        }

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    private void makeFacultyNotifactionPopup() {
        //TODO: display notifications that have the type committee and give them the option to press yes or no for each one
        //Yes will set the notification to approved and add the professor to the student committee.
        //The student can be found in the notification as notifierUserName
        //No will set the notification to false and not add the professor
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Students would like to add you to their committee");
        gp.add(header, 0, 0, 2, 1);
        ArrayList<String> userNameAL = new ArrayList<>(Arrays.asList(mv.getCurrUserName()));
        //this will get all of the notifications they have.
        rs = msdb.getData("SELECT NotifierUserName,NotificationDesc, NotificationID from user_notifications where NotifiedUserName in (?) and NotificationType in ('committee') and Approved is null", userNameAL);
        System.out.println(rs);
        int rowCount = 0;
        for (ArrayList<String> curr : rs) {
            if (rowCount == 0) {
            }//do nothing because its the headers
            else {
                Label lab = new Label(curr.get(1));//make a label with the notification description
                Button yesButton = new Button("Yes");
                Button noButton = new Button("No");
                //Description of notification  [YES] [NO]
                gp.add(lab, 1, rowCount);
                gp.add(yesButton, 2, rowCount);
                gp.add(noButton, 3, rowCount);

                //select project id where username = curr.get(0)
                String notifierProjectID = msdb.getData("SELECT ProjectID from user_project_link where UserName in (?)",
                        new ArrayList<String>(Arrays.asList(curr.get(0)))).get(1).get(0);
                ArrayList<String> queryVals = new ArrayList<>();
                queryVals.add(mv.getCurrUserName());
                queryVals.add(notifierProjectID);
                queryVals.add("chair");

                //notification ID in an Arraylist
                ArrayList<String> notificationIDAL = new ArrayList<>(Arrays.asList(curr.get(2)));
                yesButton.setOnAction(e -> {
                    msdb.setData("INSERT INTO committee VALUES (?,?,?)",queryVals);
                    msdb.setData("UPDATE user_notifications SET Approved = 1 where NotificationID in (?)",notificationIDAL);
                    //get the student username (curr.get(0)) and add the current user (mv.getCurrUser) to their committee
                    // then set approved = 1 for the current notificationID((curr.get(2)))
                    makeFacultyView();
                    popupWindow.close();

                });

                noButton.setOnAction(e -> {
                    //set approved = 0 for the current notificationID(curr.get(2))
                    msdb.setData("UPDATE user_notifications SET Approved = 0 where NotificationID in (?)",notificationIDAL);
                    makeFacultyView();
                    popupWindow.close();

                });
            }
            rowCount++;
        }

        popupWindow.show();
    }

    private ComboBox<String> makeFacultyProjOptionsDropdown(){
        ComboBox<String> optionsBox = new ComboBox<>();
        //get all project IDs from user_project_link;
        //put the name of all projects into the comboBox
        //use that name in a query to get data
        optionsBox.setOnAction(e ->{
            switchFacProjectView("VALUE OF COMBO BOX");
        });

        return optionsBox;
    }

    private void switchFacProjectView(String projName){
        //get all information about the proj
        //put that next to the labels
    }

    public void deleteConfirmPopup() {
        Stage popupWindow = new Stage();
        gp = new GridPane();
        Scene popupInfo = new Scene(gp, 600, 800);
        popupWindow.setScene(popupInfo);
        Label header = new Label("Are you sure that you want to delete your project?");

        Button deleteButton = new Button("Yes");
        Button cancelButton = new Button("Cancel");

        deleteButton.setOnAction(e -> {
            ArrayList<String> projectIDAL = new ArrayList<>(Arrays.asList(mv.getCurrProjectID()));
            msdb.setData("DELETE FROM project WHERE ID in (?)", projectIDAL);
            makeStudentView();
            popupWindow.close();
        });

        cancelButton.setOnAction(e -> {
            makeStudentView();
            popupWindow.close();
        });

        gp.add(header, 0, 0);
        gp.add(deleteButton, 0, 1);
        gp.add(cancelButton, 0, 2);
        popupWindow.show();
    }

    public void switchProject(String projectVal) {
        System.out.println("PROJECT VALUE: " + projectVal);
        ArrayList<String> projNmAL = new ArrayList<String>();
        projNmAL.add(projectVal);
        ArrayList<ArrayList<String>> rs = msdb.getData("Select Name, Summary, Topic, DueDate, Grade,ProposalApproved from  project where name in (?)", projNmAL);

        int rowCount = 1;
        gp.getChildren().clear();

        ComboBox projectsComboBox = loadStaffDBInfo();

        gp.addColumn(0, projectsComboBox, labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade, labApproved);

        for (String curr : rs.get(1)) {
            Label lab = new Label(curr);
            gp.add(lab, 1, rowCount);
            rowCount++;
        }


    }

    public void staffUpdatePlagiarismScore() {


    }
}