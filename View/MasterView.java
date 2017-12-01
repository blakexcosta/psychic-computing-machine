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

public class MasterView extends Application implements Observer{
    //These classes will have all of the functionality to make each scene.
    //todo: MAYBE- make this abstract? then all of the other views extend it. Each view alone will have an update method that updates their panel.
    Stage window = new Stage();
    Scene currScene;
    FacultyView facultyView = new FacultyView();
    StaffView staffView = new StaffView();
    StudentView studentView = new StudentView();
    LoginView loginView = new LoginView();
    MySQLDatabase msbd;//Need a model instance to add as an observer
    //TODO: Constructor should have MySQLDatabase passed into it. Giving error when I try to do that, I think because of how it is being ran in Instantiation. -Gavin
    public MasterView(){}

    @Override
    public void update(Observable observableObject, Object arg) {

        //TODO: Figure out how to make it so this does something? From here it just needs to call the correct make method in the correct view class. EX: studentView.makeUserView -Gavin
        if( observableObject != new MySQLDatabase() ) { //a quick check to make sure observable object is an instance of the database
            System.out.println("Observable object is not the object that is being observed, returning...");
            return;
        }
        if (arg instanceof MySQLDatabase) { //should be instance of this class
//            arg = (MySQLDatabase) arg;
//            userNameField.setPromptText(((MySQLDatabase) arg).getLoginUser());
            //use this to update your panels and shit.
            //to do that you need to cast it as an object.
        }
    }

    @Override
    public void start(Stage myStage) throws Exception {
//        msbd = _msbd;
//        msbd.addObserver(this);
//        this.update(msbd, MySQLDatabase.getData("I DONT KNOW WHERE TO GET THIS STRING"));

        window.setTitle("Capstone Tracker - Login");
        window.setScene(loginView.makeLoginView());
        window.show();
    }

    //Quick Question, Gavin, can you explain why there needs to be two mains? is this just a javafx thing? and does instances for the view need
    //to be included in here, or can they be placed normally into the normal main method?
    public static void main(String[] args) {
        launch(args);
    }
}
