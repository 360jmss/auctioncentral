package gui;

import model.Calendar;
import model.User;

import javax.swing.*;
import java.awt.*;

public class BidderPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** Constructor for the panel */
    BidderPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = theUser;

        myLabel = new JLabel("NO BIDDER YET");
        add(myLabel, BorderLayout.CENTER);
        setUser(theUser);
    }

    public void setUser(User theUser) {
        myLabel.setText("...and the bidder is " + myUser.getName() + "!");
    }
}
