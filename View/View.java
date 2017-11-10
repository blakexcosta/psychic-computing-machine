package View;

import javax.swing.*;
import java.util.Observable;
import java.util.Observer;

/**
 * Blake Costa, Gavin Drabik, Matthew Turczmanovicz, Oswaldo Rosete-Garcia, and Quinn Bissen
 * Group 11
 * ISTE-330
 * Professor Floeser
 * November 10th, 2017
 * This abstract view class extends View
 */
public abstract class View extends JFrame implements Observer{

    /**
     * Purpose: View constructor
     * @param
     * @return
     */
    public View(){
        super();
    }


    /**
     * Purpose: update is used to update all observable objects
     * @param
     * @return
     */
    @Override
    public void update(Observable observable, Object o) {

    }

}
