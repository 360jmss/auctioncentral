package gui;

import model.Calendar;
import model.User;
import model.UserRepo;

import javax.swing.*;
import java.awt.*;
import java.util.Objects;
import java.util.Observable;
import java.util.Observer;

/**
 * This is the main starting point for the GUI
 * Created by Simon DeMartini on 11/28/16.
 */
public class MainGUI implements Observer {

    /** The minimum width of the GUI */
    public static final int DEFAULT_WINDOW_WIDTH = 700;

    /** The minimum width of the GUI */
    public static final int DEFAULT_WINDOW_HEIGHT = 500;

    /** The master list of all users that can log in. */
    private UserRepo myRepo;

    /** The master calendar for AuctionCentral. */
    private Calendar myCalendar;

    /** The Main window frame */
    private JFrame myFrame;

    /** The main chooser for the views */
    private JPanel myViews, myLoginPanel;

    /** The status panel */
    private StatusPanel myStatusPanel;


    /**
     * The constructor for the console_ui.MainUI
     * @param theRepo The repo for the loaded map of all users
     * @param theCalendar The calendar for the loaded list of all auctions
     */
    public MainGUI(UserRepo theRepo, Calendar theCalendar) {
        myRepo = theRepo;
        myCalendar = theCalendar;
        myFrame = new JFrame();
        myViews = new JPanel(new CardLayout());
        myStatusPanel = new StatusPanel();
        myLoginPanel = new LoginPanel(myRepo);
    }

    /**
     * Call this to start the GUI.
     */
    public void start() {
        //setup frame
        myFrame.setTitle("AuctionCentral");
        myFrame.setLocationRelativeTo(null); //center on startup
        myFrame.setMinimumSize(new Dimension(DEFAULT_WINDOW_WIDTH, DEFAULT_WINDOW_HEIGHT));

        //setup card layout for user views
        myViews.add(myLoginPanel);

        //add elements
        myFrame.add(myStatusPanel, BorderLayout.NORTH);
        myFrame.add(myViews, BorderLayout.CENTER);

        //add observers
        myRepo.addObserver(this);

        //show frame, and pack it
        myFrame.setVisible(true);
        myFrame.pack();
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg instanceof User){
            myStatusPanel.updateUser((User) arg);
        }
    }
}
