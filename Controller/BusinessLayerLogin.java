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
public class BusinessLayerLogin {//extends ActionEvent{
    //private DatabaseConnection mySqlDB;
    private Object info;
    private MySQLDatabase msdb= MySQLDatabase.getInstance();

    public BusinessLayerLogin() {

    }

    /**
     * when an instance is created, it will call this method from actionPerformed, and then will call a model mehod
     * @param ae
     */
//    public void actionPerformed(ActionEvent ae) {
//        //some model method called here
//        System.out.println("BusinessLayerLogin method fired, Yay!");
//        //call login button
//    }

    /**
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        //this calls the model login
        msdb.login(username, password);
    }
}
