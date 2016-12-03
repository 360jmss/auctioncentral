package gui;

import model.*;
import sun.rmi.runtime.Log;

import javax.smartcardio.Card;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
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

    /** The String to access the bidder view */
    private static final String BIDDER_PANEL = "bp";

    /** The String to access the bidder view */
    private static final String CONTACT_PANEL = "cp";

    /** The String to access the bidder view */
    private static final String STAFF_PANEL = "sp";

    /** The String to access the bidder view */
    private static final String LOGIN_PANEL = "lp";

    /** The master list of all users that can log in. */
    private UserRepo myRepo;

    /** The master calendar for AuctionCentral. */
    private Calendar myCalendar;

    /** The Main window frame */
    private JFrame myFrame;

    /** The main chooser for the views */
    private JPanel myMainPanel;

    /** The login panel */
    private LoginPanel myLoginPanel;

    /** The status panel */
    private StatusPanel myStatusPanel;

    /** The bidder panel */
    private UserPanel myBidderPanel, myStaffPanel, myContactPanel;

    /** Te card Layout */
    private CardLayout myCards;


    /**
     * The constructor for the console_ui.MainUI
     * @param theRepo The repo for the loaded map of all users
     * @param theCalendar The calendar for the loaded list of all auctions
     */
    public MainGUI(UserRepo theRepo, Calendar theCalendar) {
        myRepo = theRepo;
        myCalendar = theCalendar;
        myFrame = new JFrame();
        myCards = new CardLayout();
        myMainPanel = new JPanel(myCards);
        myStatusPanel = new StatusPanel();
        myLoginPanel = new LoginPanel(myRepo);
        myBidderPanel = new BidderPanel(myCalendar);
        myStaffPanel = new StaffPanel(myCalendar);
        myContactPanel = new ContactPanel(myCalendar);
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
        myMainPanel.add(myLoginPanel, LOGIN_PANEL);
        myMainPanel.add(myBidderPanel, BIDDER_PANEL);
        myMainPanel.add(myStaffPanel, STAFF_PANEL);
        myMainPanel.add(myContactPanel, CONTACT_PANEL);


        //add elements
        myFrame.add(myStatusPanel, BorderLayout.NORTH);
        myFrame.add(myMainPanel, BorderLayout.CENTER);

        //add observers
        myRepo.addObserver(this);


        //show frame, and pack it
        myFrame.setVisible(true);
        myFrame.pack();
    }

    /**
     * Logout and return to login screen
     */
    private void logout() {
        myCards.show(myMainPanel, LOGIN_PANEL);
        myLoginPanel.reset();
    }

    /**
     * Login to the correct user screen
     * @param theUser a user
     */
    private void login(User theUser) {
        myStatusPanel.updateUser(theUser);
        if(theUser instanceof Staff) {
            myCards.show(myMainPanel, STAFF_PANEL);
            myStaffPanel.setUser(theUser);
        } else if (theUser instanceof Contact) {
            myCards.show(myMainPanel, CONTACT_PANEL);
            myContactPanel.setUser(theUser);
        } else if (theUser instanceof Bidder) {
            myCards.show(myMainPanel, BIDDER_PANEL);
            myBidderPanel.setUser(theUser);
        }
    }

    /**
     * Handle the observables
     * @param o what called this
     * @param arg what the observable sends
     */
    @Override
    public void update(Observable o, Object arg) {

        //login
        if(arg instanceof User){
            login((User) arg);
        }
    }

    /**
     * Displays persistent information about the logged in user and the date, and has a logout button.
     * Created by Simon DeMartini on 11/29/16.
     */
    private class StatusPanel extends JPanel {

        private JLabel myDate, myUser, myTagline;

        private JButton myLogoutButton;

        StatusPanel() {
            myDate = new JLabel(LocalDate.now().toString());
            myUser = new JLabel("Not logged in");
            myTagline = new JLabel("We are the best auctioneers");
            myTagline.setAlignmentX(Component.CENTER_ALIGNMENT);
            myLogoutButton = new JButton("Logout");
            myLogoutButton.setEnabled(false);
            myLogoutButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    myUser.setText("Not logged in");
                    myLogoutButton.setEnabled(false);
                    logout();
                }
            });

            //settings
            setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
            setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
            setBackground(Color.LIGHT_GRAY);

            //add components
            add(myDate);
            add(Box.createHorizontalGlue());
            add(myUser);
            add(Box.createRigidArea(new Dimension(10, 10)));
            add(myLogoutButton);
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
            myLogoutButton.setEnabled(true);
            myUser.setText(theUser.getName() + " / " + type);
            myDate.setText(LocalDate.now().toString());
        }
    }

}
