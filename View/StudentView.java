package View;

import Controller.BusinessLayerLogin;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;

import javafx.scene.layout.*;
import javafx.scene.text.Text;

import static javafx.fxml.FXMLLoader.load;

public class StudentView {
    private String[][] sqlData;
    private TextField name, userName, department, gradDate, major, role;
    private GridPane gp = new GridPane();
    private String[][] rs;
    private BusinessLayerLogin logController = null; //creating new private instance of the controller, so don't have to create new controller instance every time.

    public StudentView(){
        logController = new BusinessLayerLogin();
    }

    public Scene makeUserView(String[][] userInfo){

        BorderPane root = new BorderPane(); //Main layout is a border pane
        Scene sc = new Scene(root,1366,768);//put main pane is a scene
        sc.getStylesheets().add(getClass().getResource("Style.css").toExternalForm());//add css files

        HBox menuButtons = makeMenuButtons("User");//Use function to make the menu buttons. Decides which to show as clicked.

        root.setTop(menuButtons);//Put menu buttons at top of page
        root.setCenter(gp);//set center of page to grid pane (class attribute)
        gp.setHgap( 5 );
        gp.setVgap( 5 );
        gp.setAlignment( Pos.CENTER );

        String[] tempAr = new String[1];

//        for (int x =0; x < userInfo.length; x++) {
//            for (int y = 0; y < userInfo[x].length; y++) {
//                System.out.println("X: " + x + " Y: " + y + " " + userInfo[x][y]);
//            }
//        }

        Label userInfoHeaderLabel = new Label("User Information ");//Bigger header on top
        userInfoHeaderLabel.getStyleClass().add("mainHeader");
        userInfoHeaderLabel.setAlignment(Pos.CENTER);

        gp.add( userInfoHeaderLabel, 0,0,2,1);
        gp.add( new Label("Name: "), 0,1);
        gp.add( new Label("User Name: "), 0,2);
        gp.add( new Label("Department: "), 0,3);
        gp.add( new Label("Graduation Date: "), 0,4);
        gp.add( new Label("Major: "), 0,5);
        gp.add( new Label("Role: "), 0,6);

        //loop through each of the user fields that we have and put them next to the label. They come in in the correct order
        for (int i=0; i < userInfo[1].length; i++){
            gp.add(new Text(userInfo[1][i]),1,i+1);
        }
        gp.setGridLinesVisible(true);
        //creating a new button
        Button controllerActionButton = new Button("Accept");
        //adding to pane
        gp.add(controllerActionButton,0,7);
        //lambdas are sexy
        controllerActionButton.setOnAction(e -> { logController.actionPerformed(e);});

        //making and addind a new menu.
        // --- Menu File



        //menuBar.getMenus().addAll(menuFile, menuEdit, menuView);
        //((VBox) sc.getRoot()).getChildren().addAll(menuBar);
        return sc;
    }


    public HBox makeMenuButtons(String viewName){
        HBox returnMenu = new HBox();
        Button userInfoButton = new Button("User Information");
        Button projectInfoButton = new Button("Project Information");
        Button logOutButton = new Button("Log Out");
        returnMenu.getChildren().addAll(userInfoButton,projectInfoButton,logOutButton);

        if (viewName.equals("User")){
            System.out.println("user view");
        }
        if (viewName.equals("Project")){
            System.out.println("Project view");


        }

        return returnMenu;
    }
}
