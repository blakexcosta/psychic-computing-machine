package View;

import Model.MySQLDatabase;
import javafx.application.Application;
import javafx.scene.Scene;
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
    private StudentInfoView studentInfoView = new StudentInfoView(this);
    private MySQLDatabase msdb = MySQLDatabase.getInstance();//Need a model instance to add as an observer

    public StudentInfoView getStudentInfoView() {
        return studentInfoView;
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
        loginView.addObserver(this);
        studentInfoView.addObserver(this);
        loginView.makeLoginView();
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
