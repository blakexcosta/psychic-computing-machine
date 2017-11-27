package View;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.control.Button;

import java.util.Observable;
import java.util.Observer;

/**
 * View class serves as the default template that holds all the functionality of the jpanels
 * individual components
 */
public class View extends Application implements Observer{

    @Override
    public void update(Observable o, Object arg) {

    }

    Button button;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        //primaryStage is the window that will appear
        primaryStage.setTitle("main window");
        button = new Button("Button Text");

        button.setOnAction(e -> {
            System.out.println("lamda expression");
            System.out.println("lamda expression2");
        });//lambda action event

        StackPane layout = new StackPane();
        layout.getChildren().add(button);

        Scene scene = new Scene(layout,300,250);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
