package gui;

import model.Calendar;
import model.UserRepo;

/**
 * This is the main starting point for the GUI
 * Created by Simon DeMartini on 11/28/16.
 */
public class MainGUI {
    /** The master list of all users that can log in. */
    private UserRepo myRepo;

    /** The master calendar for AuctionCentral. */
    private Calendar myCalendar;

    /**
     * The constructor for the console_ui.MainUI
     * @param theRepo The repo for the loaded map of all users
     * @param theCalendar The calendar for the loaded list of all auctions
     */
    public MainGUI(UserRepo theRepo, Calendar theCalendar) {
        myRepo = theRepo;
        myCalendar = theCalendar;
    }
}
