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
public class BusinessLayer extends ActionEvent{
    //private DatabaseConnection mySqlDB;
    private Object info;
    //private MySQLDatabase = new MySQLDatabase();

    public BusinessLayer() {

    }

    /**
     * when an instance is created, it will call this method from actionPerformed, and then will call a model mehod
     * @param actionEvent
     */

    public void actionPerformed(ActionEvent actionEvent) {
        //some model method called here
        System.out.println("BusinessLayer method fired, Yay!");
    }
}
