//import Model.*;
import Controller.Controller;
import Model.*;
import View.*;
/**
 * Main method class, used to instantiate whole fleet of programs/ classes.
 */
public class Instantiation {
    public static void main(String[] args) {
        //test login
        //ab1234 (al baker)
        //5f4dcc3b5aa765d61d8327deb882cf99
        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(View.class);
            }
        }.start();
    }
}
