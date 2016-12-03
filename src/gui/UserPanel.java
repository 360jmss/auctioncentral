package gui;

import model.Calendar;
import model.User;

import javax.swing.*;

public abstract class UserPanel extends JPanel {

    /** The master list of all users that can log in. */
    protected User myUser;

    /** The master calendar for AuctionCentral. */
    protected Calendar myCalendar;

    /** Set the correct logged in user */
    public void setUser(User theUser) {
        myUser = theUser;
    }
}
