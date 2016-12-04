package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static javax.swing.SwingConstants.*;

//use isEnabled and setEnabled

public class ContactPanel extends UserPanel  {

    private Contact myUser;

    private Auction myAuction;

    /** The label for showing info. */
    private JLabel myLabel;

    /** Panel to hold the initial user action buttons. */
    private JPanel myInitialButtons;

    /** Panel to hold the view auction options. */
    private JPanel myViewAuctionButtons;

    /** Panel to hold the submit auction request buttons. */
    private JPanel mySubmitAuctionButtons;

    /** Panel to hold the edit options for the contact; currently just 'go back'. */
    private JPanel myEditButtons;

    /** Initial actions for the user; View auction, Submit auction request, Cancel auction request, Edit info */
    private InitialActionsPanel myInitialActions;

    /** View auction actions for the user. */
    private ViewAuctionPanel myViewAuctionActions;

    /** Submit auction actions for the user. */
    private SubmitAuctionPanel mySubmitAuctionActions;

    /** Edit info actions for the user. */
    private EditInfoPanel myEditActions;

    /** Constructor for the panel */
    ContactPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = (Contact) theUser;
        myAuction = myCalendar.getContactsAuction(myUser);

        if (myAuction == null) {
            myLabel = new JLabel("Hi " + myUser.getName() + "! You have no upcoming auction." +
                    " What would you like to do?");
        } else {
            myLabel = new JLabel("Hi " + myUser.getName() + ", what would you like to do?");
        }

        setLayout(new BorderLayout());

        //Initialize the first page that a contact person will see and add it to the panel.
        myInitialButtons = new JPanel();
        myInitialActions = new InitialActionsPanel();
        myInitialButtons.add(myInitialActions);

        //Initialize view auction options but do not add them here.
        myViewAuctionButtons = new JPanel();
        myViewAuctionActions = new ViewAuctionPanel();

        //Initialize submit auction request options but do not add them here.
        mySubmitAuctionButtons = new JPanel();
        mySubmitAuctionActions = new SubmitAuctionPanel();

        //Initialize edit options but do not add them here.
        myEditButtons = new JPanel();
        myEditActions = new EditInfoPanel();

        add(myLabel, BorderLayout.NORTH);
        add(myInitialButtons, BorderLayout.SOUTH);
    }

    public void setUser(User theUser) {
        myLabel.setText("Hi " + myUser.getName() + ", what would you like to do?");
    }

    private void viewUpcomingAuctions() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!myViewAuctionButtons.isEnabled()) {
            myViewAuctionButtons.setEnabled(true);
            myViewAuctionButtons.setVisible(true);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            myViewAuctionButtons.add(myViewAuctionActions);
            add(myViewAuctionButtons, BorderLayout.SOUTH);
        }


    }

    private void viewAuctionRequest() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!mySubmitAuctionButtons.isEnabled()) {
            mySubmitAuctionButtons.setEnabled(true);
            mySubmitAuctionButtons.setVisible(true);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            mySubmitAuctionButtons.add(mySubmitAuctionActions);
            add(mySubmitAuctionButtons, BorderLayout.SOUTH);
        }
    }

    private void viewEditInfo() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!myEditButtons.isEnabled()) {
            myEditButtons.setEnabled(true);
            myEditButtons.setVisible(true);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            myEditButtons.add(myEditActions);
            add(myEditButtons, BorderLayout.SOUTH);
        }
    }

    class InitialActionsPanel extends JPanel {

        InitialActionsPanel() {
            super();
            setUp();
        }

        private void setUp() {
            JButton upcomingAuction = new JButton("View Upcoming Auction");
            UpcomingAuctionListener upcoming = new UpcomingAuctionListener();
            upcomingAuction.addActionListener(upcoming);
            add(upcomingAuction);

            JButton auctionRequest = new JButton("Submit an Auction Request");
            AuctionRequestListener request = new AuctionRequestListener();
            auctionRequest.addActionListener(request);
            add(auctionRequest);

            JButton editInfo = new JButton("Edit My Information");
            EditInfoListener info = new EditInfoListener();
            editInfo.addActionListener(info);
            add(editInfo);
        }
    }

    class ViewAuctionPanel extends JPanel {

        ViewAuctionPanel() {
            super();
            setUp();
        }

        private void setUp() {
            JButton addItem = new JButton("Add an Item");
            AddItemListener add = new AddItemListener();
            addItem.addActionListener(add);
            add(addItem);

            JButton cancelItem = new JButton("Cancel an Item");
            CancelItemListener cancel = new CancelItemListener();
            cancelItem.addActionListener(cancel);
            add(cancelItem);

            JButton cancelAuction = new JButton("Cancel This Auction");
            CancelAuctionListener cancelAucListener = new CancelAuctionListener();
            cancelAuction.addActionListener(cancelAucListener);
            add(cancelAuction);

            JButton goBack = new JButton("Go Back");
            GoBackHomeFromViewAuctionListener homeFromView = new GoBackHomeFromViewAuctionListener();
            goBack.addActionListener(homeFromView);
            add(goBack);
        }
    }

    class SubmitAuctionPanel extends JPanel {

        SubmitAuctionPanel() {
            super();
            setUp();
        }

        private void setUp() {
            JButton submitAuction = new JButton("Submit Auction");
            SubmitAuctionListener submit = new SubmitAuctionListener();
            submitAuction.addActionListener(submit);
            add(submitAuction);

            JButton goBack = new JButton("Go Back");
            GoBackHomeFromSubmitAuctionListener homeFromSubmit = new GoBackHomeFromSubmitAuctionListener();
            goBack.addActionListener(homeFromSubmit);
            add(goBack);
        }
    }

    class EditInfoPanel extends JPanel {

        EditInfoPanel() {
            super();
            setUp();
        }

        private void setUp() {
            JButton goBack = new JButton("Go Back");
            GoBackHomeFromEditListener homeFromEdit = new GoBackHomeFromEditListener();
            goBack.addActionListener(homeFromEdit);
            add(goBack);
        }
    }

    private class UpcomingAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewUpcomingAuctions();
        }
    }

    private class AuctionRequestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewAuctionRequest();
        }
    }

    private class EditInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewEditInfo();
        }
    }

    private class AddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    private class CancelItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    private class CancelAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    private class GoBackHomeFromViewAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myViewAuctionButtons.setVisible(false);
            myViewAuctionButtons.setEnabled(false);
            myInitialButtons.setVisible(true);
        }
    }

    private class SubmitAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    private class GoBackHomeFromSubmitAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mySubmitAuctionButtons.setVisible(false);
            mySubmitAuctionButtons.setEnabled(false);
            myInitialButtons.setVisible(true);
        }
    }

    private class GoBackHomeFromEditListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myEditButtons.setVisible(false);
            myEditButtons.setEnabled(false);
            myInitialButtons.setVisible(true);
        }
    }
}
