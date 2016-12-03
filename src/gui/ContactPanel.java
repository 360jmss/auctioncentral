package gui;

import model.Calendar;
import model.User;

import javax.swing.*;
import java.awt.*;

public class ContactPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** Constructor for the panel */
    ContactPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = theUser;

        myLabel = new JLabel("NO CONTACT YET");
        add(myLabel, BorderLayout.CENTER);
        setUser(myUser);
    }

    public void setUser(User theUser) {
        myLabel.setText("...and the contact is " + myUser.getName() + "!");
    }
}
