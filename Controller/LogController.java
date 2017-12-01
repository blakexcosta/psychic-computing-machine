package Controller;

import Model.MySQLDatabase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * controller class handles all the logic passed from
 * the database to here, then updates the view.
 */
public class LogController {
    //private DatabaseConnection mySqlDB;
    private Object info;
    private MySQLDatabase msdb= new MySQLDatabase();

    public LogController() {

    }

    /**
     * when an instance is created, it will call this method from actionPerformed, and then will call a model mehod
     * @param ae
     */

    public void actionPerformed(ActionEvent ae) {
        //some model method called here
        //TODO: Check where the action event is coming from (ae.getSource). Based on that call the correct model method? -Gavin
        //TODO: Look into where the model is calling notifyObservers. How to make updates with getData not calling notify? -Gavin

        //--Get data, how does the information get back to the view?
        System.out.println(ae.getSource());
        System.out.println(msdb.getAllData("user"));
        System.out.println("LogController method fired, Yay!");
    }
}
