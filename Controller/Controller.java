package Controller;

import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

/**
 * controller class handles all the logic passed from
 * the database to here, then updates the view.
 */
public class Controller extends Observable{
    //private DatabaseConnection mySqlDB;
    private Object info;

    public Controller() {

    }

    @Override
    public synchronized void addObserver(Observer observer) {
        super.addObserver(observer);
    }

    @Override
    public synchronized void deleteObserver(Observer observer) {
        super.deleteObserver(observer);
    }

    @Override
    public synchronized boolean hasChanged() {
        return super.hasChanged();
    }

    @Override
    public void notifyObservers() {
        super.notifyObservers();
    }

    public void postInformation(String info, ArrayList<String> arrayList) {

    }
}
