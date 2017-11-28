//import Model.*;
import Controller.Controller;
import View.View;
/**
 * Main method class, used to instantiate whole fleet of programs/ classes.
 */
public class Instantiation {
    public static void main(String[] args) {
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(View.class);
            }
        }.start();
    }
}
