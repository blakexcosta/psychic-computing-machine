package Controller;

import Model.MySQLDatabase;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.util.ArrayList;
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
 * controller class handles all the logic passed from
 * the database to here, then updates the view.
 */
// TODO: 12/8/17 Documentation and formatting, make it nice -Blake
public class BusinessLayerLogin {//extends ActionEvent{
    private MySQLDatabase msdb= MySQLDatabase.getInstance();
   
    public BusinessLayerLogin(String username, String password) {
        login(username, password);
    }

    /**
     * @param username
     * @param password
     */
    public void login(String username, String password) {
        //this calls the model login
        msdb.login(username, password);

    }
}
