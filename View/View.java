package View;

import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

/**
 * View class serves as the default template that holds all the functionality of the jpanels
 * individual components
 */
public class View extends JFrame implements Observer{
    //has instances of all the jpanels, as well as the model
    JPanel createUserView = null;
    JPanel homeView = null;
    JPanel loginView = null;
    JPanel projectView = null;
    //now we need an instantion of the model
    // TODO: 11/26/17 can't get the model instance in here -Blake

    /**
     * default view constructor
     */
    public View() {
        JFrame frame = new JFrame("View Demo"); //create new frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //create default close operation

        // TODO: 11/26/17 component information goes here. need to make this -blake
        //https://docs.oracle.com/javase/tutorial/uiswing/components/frame.html
        frame.getContentPane().add(new Label("Hello there"),BorderLayout.CENTER);

        //size frame
        //frame.pack();
        frame.setSize(500,500);
        //center frame
        frame.setLocationRelativeTo(null);
        //show it
        frame.setVisible(true);
    }

    /**
     * update method from Observer to update the view panel
     * @param observable
     * @param o
     */
    @Override
    public void update(Observable observable, Object o) {

    }
}
