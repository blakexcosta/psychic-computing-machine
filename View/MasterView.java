package View;

import Controller.BusinessLayerLogin;
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
public class MasterView extends Application{
   //These classes will have all of the functionality to make each scene.
   //todo: MAYBE- make this abstract? then all of the other views extend it. Each view alone will have an update method that updates their panel.
   // TODO: 12/8/17 Cleanup this class, make it look nice. -Blake
   private Stage window = new Stage();
   private Scene currScene;
   private FacultyView facultyView = null;
   private StaffView staffView = null;
   private StudentView studentView = null;
   private LoginView loginView = null;
   private MySQLDatabase msdb = MySQLDatabase.getInstance();//Need a model instance to add as an observer

   //TODO: Constructor should have MySQLDatabase passed into it. Giving error when I try to do that, I think because of how it is being ran in Instantiation. -Gavin
   public MasterView(){}

   @Override
   public void start(Stage myStage) throws Exception {
      //remove observer, make classes implement it.
      window.setTitle("Capstone Tracker - Login");
      window.setScene(loginView.makeLoginView());
      //get the login information.
      window.show();
   }

   /**
    * used by the model to set a state check,
    */
   public void setWindow() {
      //checks for which window to initially use, instead of having to use
      if (msdb.getUserRole().equals("student")) {
         System.out.println("student found");
         window.setScene(studentView.makeUserView(msdb.getUserInfo()));
      } else if (msdb.getUserRole().equals("faculty")) {
         
      }
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
