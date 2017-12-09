package View;

import Model.MySQLDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
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
    //These classes will have all of the functionality to make each scene.
    //todo: MAYBE- make this abstract? then all of the other views extend it. Each view alone will have an update method that updates their panel.
    // TODO: 12/8/17 Cleanup this class, make it look nice. -Blake
    private Stage window = new Stage();
    private Scene currScene;
    private FacultyView facultyView = new FacultyView();
    private StaffView staffView = new StaffView();
    private StudentView studentView = new StudentView();
    private LoginView loginView = new LoginView();
    private MySQLDatabase msdb = MySQLDatabase.getInstance();//Need a model instance to add as an observer
    //TODO: Constructor should have MySQLDatabase passed into it. Giving error when I try to do that, I think because of how it is being ran in Instantiation. -Gavin
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

        // TODO: 12/8/17 make the subclasses implement Observer, and have it get an instance of the model, instead of in here.
        // we want the subclasses to be able to implement deal with the flexibility of system wide changes.
        if( observableObject != msdb) { //a quick check to make sure observable object is an instance of the database
            System.out.println("Observable object is not the object that is being observed, returning...");
            return;
        }
        //casting the object to different instances. making sure it does not fail.
        //if instanceof String

        String[][] loginPassArg;
        loginPassArg = (String[][]) arg;
        //System.out.println((String[][]) arg[1][1]);
        if (loginPassArg instanceof String[][]) {
            window.setScene(studentView.makeUserView(loginPassArg));
        }

        //switching
        //studentView.makeUserView();
    }

    @Override
    public void start(Stage myStage) throws Exception {
        msdb.addObserver(this);
        window.setTitle("Capstone Tracker - Login");
        window.setScene(loginView.makeLoginView());
        window.show();
    }

    //Quick Question, Gavin, can you explain why there needs to be two mains? is this just a javafx thing? and does instances for the view need
    //to be included in here, or can they be placed normally into the normal main method?

//    public MySQLDatabase Msdb() {
//        return this.msdb;
//    }
//
//    public void setMsdb(MySQLDatabase msdb) {
//        this.msdb = msdb;
//    }
}
