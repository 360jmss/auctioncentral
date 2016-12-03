package gui;

import model.Calendar;
import model.User;

import javax.swing.*;
import java.awt.*;

public class StaffPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** Constructor for the panel */
    StaffPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = theUser;

        myLabel = new JLabel("NO STAFF YET");
        add(myLabel, BorderLayout.CENTER);
        setUser(myUser);
    }

    public void setUser(User theUser) {
        myLabel.setText("...and the staff member is " + myUser.getName() + "!");
    }
}
