package gui;

import model.Calendar;
import model.User;

import javax.swing.*;
import java.awt.*;

public class BidderPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** Constructor for the panel */
    BidderPanel(Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = null;

        myLabel = new JLabel("NO BIDDER YET");
        add(myLabel, BorderLayout.CENTER);
    }

    public void setUser(User theUser) {
        super.setUser(theUser);
        myLabel.setText("...and the bidder is " + myUser.getName() + "!");
    }
}
