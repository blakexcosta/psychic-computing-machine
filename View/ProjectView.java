package View;

import Model.MySQLDatabase;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import java.util.Observable;

public class ProjectView extends Observable {
    private MySQLDatabase msdb = MySQLDatabase.getInstance(); //there is only one instance of the database.
    private MasterView mv;//this is passed in through the constructor
    private GridPane gp;
    private Label mainHeader, labName, labSummary, labTopic, labType, labStartDate, labEndDate, labDueDate, labGrade;
    private TextField inputName,inputSummary,inputStartDate, inputEndDate, inputDueDate, inputTopic, inputType;
    private Button showMilestones;
    private String[][] rs;


    public ProjectView(MasterView _mv) {
        this.mv = _mv;
    }

    //TODO: Build out student project view
    public void makeStudentView() {
        Scene sc = mv.getBaseScene();
        //add the css sheet
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files
        //cast the border pane from the scene so it can be used.
        BorderPane bp = (BorderPane) sc.getRoot();
        //make a grid pane to put data on
        gp = new GridPane();

        if (!loadStudentDBInfo())
            return;//If they have project info it will add it to the page, if not it makes the other view

        //add it to the border pane in the center
        bp.setCenter(gp);
        //set margins
        gp.setHgap(5);
        gp.setVgap(5);
        //position in center of screen
        gp.setAlignment(Pos.CENTER);

        //make header label and add css
        mainHeader = new Label("Project Information");
        mainHeader.getStyleClass().add("mainHeader");

        //add  header the grid pane
        gp.add(mainHeader, 0, 0, 2, 1);

        //initialize all of the buttons and add them to the grid
        labName = new Label("Name: ");
        labSummary = new Label("Summary: ");
        labTopic = new Label("Topic: ");
        labDueDate = new Label("Due Date: ");
        labGrade = new Label("Grade: ");
        gp.add(labName, 0, 1);
        gp.add(labSummary, 0, 2);
        gp.add(labTopic, 0, 3);
        gp.add(labDueDate, 0, 4);
        gp.add(labGrade, 0, 5);
        //put all the database info next to the buttons

        //add the milestone button
        showMilestones = new Button();
        showMilestones.setText("Show Project Milestones");

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);


    }

    //TODO: Build out staff project view
    public void makeStaffView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view

        setChanged();
        notifyObservers(sc);
    }

    //TODO: Build out faculty project view
    public void makeFacultyView() {
        //this scene object comes from masterView. it has a border pane in it. The top object of the border pane has been set to the nav bar
        //the borderpane can be referenced by casting an object seen below
        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();

        //After the scene is made completely these two methods run which will update the master view to our new view
        setChanged();
        notifyObservers(sc);
    }

    /**
     * returns true or false based on if the user has project informatioh. If not the view needs to be made to add a project
     *
     * @return
     */
    public Boolean loadStudentDBInfo() {
        String[] userNameAL = {msdb.getUserName()};
        //get the project info for the current user
        rs = msdb.getData("Select Name, Summary, Topic, DueDate, Grade from  project where ID in (select ProjectID from user_project_link where UserName in (?))", userNameAL);

        if (!rs[0][1].equals("Summary")) {//They do not have any project info, so we need to make the view to add a project.
            //TODO: notify the user that they have no projects before the new view is made.
            System.out.println("no projects found");
            makeAddProjectView();
            return false;
        } else {
            //add their project info to the grid pane one row at a time
            int rowCount = 0;
            for (String str : rs[1]) {
                Label lab = new Label(str);
                lab.getStyleClass().add("infoDataLabel");
                gp.add(lab, 1, ++rowCount);
            }
            return true;
        }
    }

    public void makeAddProjectView() {
        //TODO: this will be a form that they can input into and it will add their project to the database
        //private TextField inputName,inputSummary,inputStartDate, inputEndDate, inputDueDate, inputTopic, inputType;

        Scene sc = mv.getBaseScene();
        BorderPane bp = (BorderPane) sc.getRoot();
        gp = new GridPane();
        bp.setCenter(gp);
        gp.setAlignment(Pos.CENTER);
        Label header = new Label("Add a new Project");
        labName = new Label("Name: ");
        labSummary = new Label("Summary: ");
        labTopic = new Label("Topic: ");
        labType = new Label("Type: ");
        labStartDate = new Label("Start Date(YYYY-MM-DD): ");
        labEndDate = new Label("End Date(YYYY-MM-DD): ");
        Button submit = new Button("Submit");
        submit.setOnAction(e ->{
            //TODO: check form to validate input
            String[] queryVals = {inputName.getText(),inputSummary.getText(),inputTopic.getText(),inputType.getText(),
                    inputStartDate.getText(),inputEndDate.getText(),"0","0"};

            String sqlQuery = "INSERT INTO project ('Name','Summary','Topic','Type','StartDate','EndDate','Completed','ProposalApproved')" +
                    " VALUES (?,?,?,?,?,?,?,?)";

            //TODO: it says it is inserting but there is no new data going into the DB
            System.out.println(msdb.setData(sqlQuery,queryVals));

            System.out.println("insert into Project");
        });

        inputName = new TextField();
        inputSummary = new TextField();
        inputTopic = new TextField();
        inputType = new TextField();
        inputType.setPromptText("Capstone or Thesis");
        inputStartDate = new TextField();
        inputStartDate.setPromptText("YYYY-MM-DD");
        inputEndDate = new TextField();
        inputEndDate.setPromptText("YYYY-MM-DD");
        gp.addColumn(0,header,labName,labSummary,labTopic,labType,labStartDate,labEndDate,submit);
        gp.addColumn(1,new Label(""),inputName,inputSummary,inputTopic,inputType,inputStartDate,inputEndDate);





        setChanged();
        notifyObservers(sc);
    }
}
