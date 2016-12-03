package gui;

import model.Calendar;
import model.User;

import javax.swing.*;
import java.awt.*;

public class BidderPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    private ActionsPanel myActions;

    /** Constructor for the panel */
    BidderPanel(Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = null;
        myActions = new ActionsPanel();
        myLabel = new JLabel("NO BIDDER YET");
        setLayout(new BorderLayout());
        add(myLabel, BorderLayout.NORTH);
        JPanel south = new JPanel();
        south.setBackground(Color.BLACK);
        south.add(myActions);
        add(south, BorderLayout.SOUTH);
    }

    public void setUser(User theUser) {
        super.setUser(theUser);
        myLabel.setText("Hi " + myUser.getName() + ", what would you like to do?");
    }



    class ActionsPanel extends JPanel {

        ActionsPanel() {
            super();
            setUp();
        }

        private void setUp() {
            setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
            add(new Button("View Auctions"));
            add(new Button("Edit My Information"));
        }

    }
}
