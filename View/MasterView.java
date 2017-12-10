package View;

import Model.MySQLDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
    private MySQLDatabase msdb = MySQLDatabase.getInstance();//Need a model instance to add as an observer

    public InfoView getInfoView() {
        return infoView;
    }

    public LoginView getLoginView() {
        return loginView;
    }

    public MasterView(){}

    /**
     * update functions as a way to get state information from the model, it is done when
     * the model calls setChanged() followed by notifyObservers(), and then this update
     * function can call getters from the model to get its information. It IS assumed
     * that the model updates its state information before notifyObservers() is called.
     * It is usually recommended that this is done by making setters in the model, to be
     * used privately.
     * @param observableObject
     * @param arg
     */
    @Override
    public void update(Observable observableObject, Object arg) {
        System.out.println("Updating the view to: " + observableObject);
        window.setScene((Scene) arg);
    }

    @Override
    public void start(Stage myStage) throws Exception {
        window.setTitle("Capstone Tracker - Login");
        //master view needs to observe all of the different views separately
        loginView.addObserver(this);
        infoView.addObserver(this);
        projectView.addObserver(this);
        loginView.makeLoginView();
        window.show();
    }

    /**
     * this is used by all of the views to make the nav bar at the top so I decided to move it to the master view.
     * At the start of each stage being made (in the various views) you call mv.makeMenuButtons then add it to the top of the border pane.
     * You can see an example in the making of the student info view.
     */
    public HBox makeMenuButtons() {
        HBox returnMenu = new HBox(500);
        Button userInfoButton = new Button("User Information");
        userInfoButton.setOnAction(e -> {
            if (msdb.getRole().equals("student")){
                infoView.makeStudentView();
            }
            if (msdb.getRole().equals("staff")){
                infoView.makeStaffView();
            }
            if (msdb.getRole().equals("faculty")){
                infoView.makeFacultyView();
            }
        });//end action listener for user info button
        Button projectInfoButton = new Button("Project Information");
        projectInfoButton.setOnAction(e -> {
            if (msdb.getRole().equals("student")){
                projectView.makeStudentView();
            }
            if (msdb.getRole().equals("staff")){
                projectView.makeStudentView();
            }
            if (msdb.getRole().equals("faculty")){
                projectView.makeStudentView();
            }
        });//end action listener for project button
        Button logOutButton = new Button("Log Out");
        logOutButton.setOnAction(e -> {
            //todo: Implement logging out.
            //remake the login view. Get rid of stored database variables?

        });//end logout action listner


        returnMenu.getChildren().addAll(userInfoButton, projectInfoButton, logOutButton);
        return returnMenu;
    }

}
