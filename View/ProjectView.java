package View;

import Model.MySQLDatabase;
import javafx.scene.Scene;

import java.util.Observable;

public class ProjectView extends Observable {
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor

    public ProjectView(MasterView _mv){
        this.mv = _mv;
    }
    //TODO: Build out student project view
    public void makeStudentView(){
        //make the scene and store everyting in this object
        //You have access to the database using the msdb object (but it would be a good idea to break things into separate methods)
        Scene returnScene = null;

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(returnScene);
    }
    //TODO: Build out staff project view
    public void makeStaffView(){
        //make the scene and store everyting in this object
        //You have access to the database using the msdb object (but it would be a good idea to break things into separate methods)

        Scene returnScene = null;

        //After the scene is made completely these two methods run which will update the master view to our new view
        //You have access to the database using the msdb object (but it would be a good idea to break things into separate methods)

        setChanged();
        notifyObservers(returnScene);
    }
    //TODO: Build out faculty project view
    public void makeFacultyView(){
        //make the scene and store everyting in this object
        Scene returnScene = null;

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(returnScene);
    }
}
