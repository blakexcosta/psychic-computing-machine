//import Model.*;

import Model.MySQLDatabase;
import View.*;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * December 18th, 2017
 */

/**
 * Main method class, used to instantiate whole fleet of programs/ classes.
 */
public class Instantiation {
    public static void main(String[] args) {
        //test login
        //ab1234 (al baker)
        //5f4dcc3b5aa765d61d8327deb882cf99
        //runs the program
        //| GradCoord | Qi        | Yu          | Password | https://ist.rit.edu/assets/img/people/qyuvks.jpg                                       | 1900-01-01     | Associate Professor          |       | staff   |
        //creates a new thread to run the javafx application, goes to MasterView

        new Thread() {
            @Override
            public void run() {
                javafx.application.Application.launch(MasterView.class);
            }
        }.start();
    }
}
