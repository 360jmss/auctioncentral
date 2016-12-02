package gui;

import model.Bidder;
import model.Contact;
import model.Staff;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

/**
 * Created by simon on 11/29/16.
 */
public class StatusPanel extends JPanel {

    private JLabel myDate, myUser, myTagline;

    public StatusPanel() {
        myDate = new JLabel(LocalDate.now().toString());
        myUser = new JLabel("Not logged in");
        myTagline = new JLabel("We are the best auctioneers");
        myTagline.setAlignmentX(Component.CENTER_ALIGNMENT);

        //settings
        setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
        setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
        setBackground(Color.LIGHT_GRAY);

        //add components
        add(myDate);
        add(Box.createHorizontalGlue());
        add(myTagline);
        add(Box.createHorizontalGlue());
        add(myUser);
    }

    public void updateUser(User theUser) {
        //Set user type
        String type;
        if(theUser instanceof Staff) {
            type = "Staff";
        } else if (theUser instanceof Contact) {
            type = "Contact";
        } else if (theUser instanceof Bidder) {
            type = "Bidder";
        } else {
            type = "Undefined";
        }

        //update labels
        myUser.setText(theUser.getName() + " / " + type);
        myDate.setText(LocalDate.now().toString());
    }

}
