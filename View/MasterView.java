package View;

import Model.MySQLDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.util.Observable;
import java.util.Observer;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

/**
 * MasterView serves as the home base for the views, as it instantiates
 * new views.
 */
public class MasterView extends Application implements Observer{

    private Stage window = new Stage();
    private LoginView loginView = new LoginView(this);
    private InfoView infoView = new InfoView(this);
    private ProjectView projectView = new ProjectView(this);
    private MilestoneView milestoneView = new MilestoneView(this);
    private MySQLDatabase msdb = MySQLDatabase.getInstance();//Need a model instance to add as an observer
    private String currUserName, currProjectID;
    private String buttonStyle ="-fx-padding: 2em;-fx-background-color:linear-gradient(#dddddd 0%, #f6f6f6 50%);-fx-background-radius: 8,7,6; -fx-background-insets: 0,1,2; -fx-text-fill: black; -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );";


    public String getCurrUserName() {
        return currUserName;
    }

    public String getCurrProjectID() {
        return currProjectID;
    }

    public void setCurrUserName(String currUserName) {
        this.currUserName = currUserName;
    }

    public void setCurrProjectID(String currProjectID) {
        this.currProjectID = currProjectID;
    }


    public InfoView getInfoView() {
        return infoView;
    }

    public ProjectView getProjectView() {
        return projectView;
    }

    public MilestoneView getMilestoneView() {
        return milestoneView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public String getButtonStyle() {
        return buttonStyle;
    }

    /**
     * this is used by all of the views to make the nav bar at the top so I decided to move it to the master view.
     * At the start of each stage being made (in the various views) you call mv.makeMenuButtons then add it to the top of the border pane.
     * You can see an example in the making of the student info view.
     */
    public Scene getBaseScene(){
        BorderPane bp = new BorderPane();
        bp.setTop(makeMenuButtons());
        return new Scene(bp,1280,800);
    }

    public HBox makeMenuButtons() {
        HBox returnMenu = new HBox(430);
        Button userInfoButton = new Button("User Information");
        userInfoButton.setPrefHeight(50);
        userInfoButton.setStyle(buttonStyle);
        userInfoButton.setOnAction(e -> {
            if (msdb.getRole().equals("student")){
                infoView.makeStudentView();
            }
            if (msdb.getRole().equals("staff")){
                infoView.makeStaffView();//make infoview of the staffview.
            }
            if (msdb.getRole().equals("faculty")){
                infoView.makeFacultyView();
            }
        });//end action listener for user info button
        Button projectInfoButton = new Button("Project Information");
        projectInfoButton.setPrefHeight(50);
        projectInfoButton.setStyle(buttonStyle);
        projectInfoButton.setOnAction(e -> {
            if (msdb.getRole().equals("student")){
                projectView.makeStudentView();
            }
            if (msdb.getRole().equals("staff")){
                //make a new staff view
                projectView.makeStaffView();//making a new staff view
            }
            if (msdb.getRole().equals("faculty")){
                projectView.makeStudentView();
            }
        });//end action listener for project button
        Button logOutButton = new Button("Log Out");
        logOutButton.setPrefHeight(50);
        logOutButton.setStyle(buttonStyle);
        logOutButton.setOnAction(e -> {
            //remake the login view. Get rid of stored database variablesz
            System.out.println("logout clicked.");
            //make it go back to the login screen
            // TODO: 12/12/17 I see an issue with this. state information may break, but we'll cross that bridge when we get there -Blake.
            loginView.makeLoginView();
            window.show();
        });//end logout action listner
        returnMenu.getChildren().addAll(userInfoButton, projectInfoButton, logOutButton);
        return returnMenu;
    }

    public MasterView(){}

    /**
     * The master view is observing each of the view classes individually. So whenever a view class is done making the
     * necessary view it calls setChanged and NotifyObservers which notifys this to switch the view to whichever one is
     * passed in to notifyObservers
     * @param observableObject
     * @param arg
     */
    @Override
    public void update(Observable observableObject, Object arg) {
        System.out.println("Updating the view to: " + observableObject);
        window.setScene((Scene) arg);
    }

    /**
     * When the program first runs
     * @param myStage
     * @throws Exception
     */
    @Override
    public void start(Stage myStage) throws Exception {
        window.setTitle("Capstone Tracker - Login");
        //master view needs to observe all of the different views separately
        loginView.addObserver(this);
        infoView.addObserver(this);
        projectView.addObserver(this);
        milestoneView.addObserver(this);
        loginView.makeLoginView();
        window.show();
    }


}
