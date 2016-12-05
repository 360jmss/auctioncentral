package gui;

import model.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

//use isEnabled and setEnabled
//notifyObservers(send in whatever is changed);

public class ContactPanel extends UserPanel implements Observer {

    private Contact myUser;

    private Auction myAuction;

    private JList<AuctionItem> auctionItemList;

    /** The label for showing info. */
    private JLabel myLabel;

    private JLabel myUpcomingAuctionLabel;

    /** Panel to hold the initial user action buttons. */
    private JPanel myInitialButtons;

    /** Panel to hold the view auction options. */
    private JPanel myViewAuctionButtons;

    /** Panel to hold the submit auction request buttons. */
    private JPanel mySubmitAuctionButtons;

    /** Panel to hold the edit options for the contact; currently just 'go back'. */
    private JPanel myEditButtons;

    private JPanel myInfoHolder;

    private JPanel myItemListPanel;

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
        setLayout(new BorderLayout());

        myCalendar = theCalendar;
        myUser = (Contact) theUser;
        myAuction = myCalendar.getContactsAuction(myUser);

        //Initialize the first page that a contact person will see and add it to the panel.
        myInitialButtons = new JPanel();
        myInitialActions = new InitialActionsPanel();
        myInitialButtons.add(myInitialActions);

        //Check to see if the user already has an auction in the system.
        if (myAuction != null) {
            myLabel = new JLabel("Hi " + myUser.getName() + ", what would you like to do?");
            myUpcomingAuctionLabel = new JLabel("Your upcoming auction: " + myAuction.toString());
            myInitialActions.upcomingAuction.setEnabled(true);
            myInitialActions.auctionRequest.setEnabled(false);
        } else {
            myLabel = new JLabel("Hi " + myUser.getName() + "! You have no upcoming auction." +
                    " What would you like to do?");
            myUpcomingAuctionLabel = new JLabel("You have no upcoming auction yet!");
            myInitialActions.upcomingAuction.setEnabled(false);
            myInitialActions.auctionRequest.setEnabled(true);
        }

        //Initialize view auction options but do not add them here.
        myViewAuctionButtons = new JPanel();
        myViewAuctionActions = new ViewAuctionPanel();

        //Initialize submit auction request options but do not add them here.
        mySubmitAuctionButtons = new JPanel();
        mySubmitAuctionActions = new SubmitAuctionPanel();

        //Initialize edit options but do not add them here.
        myEditButtons = new JPanel();
        myEditActions = new EditInfoPanel();

        //Initialize item list panel.
        myItemListPanel = createAuctionItemPanel();

        //Initialize information holder for displaying user's information.
        myInfoHolder = createUserInfoPanel();

        //Add initial labels and panels.
        add(myLabel, BorderLayout.NORTH);
        add(myInitialButtons, BorderLayout.SOUTH);
    }

    public void setUser(User theUser) {
        myLabel.setText("Hi " + myUser.getName() + ", what would you like to do?");
    }

    private void viewUpcomingAuction() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!myViewAuctionButtons.isEnabled()) {
            myViewAuctionButtons.setEnabled(true);
            myViewAuctionButtons.setVisible(true);
            myItemListPanel.setVisible(true);
            add(myItemListPanel, BorderLayout.CENTER);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            myViewAuctionButtons.add(myViewAuctionActions);
            myViewAuctionButtons.setVisible(true);
            myItemListPanel.setVisible(true);
            add(myItemListPanel, BorderLayout.CENTER);
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
            mySubmitAuctionButtons.setVisible(true);
            add(mySubmitAuctionButtons, BorderLayout.SOUTH);
        }
    }

    private void viewEditInfo() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!myEditButtons.isEnabled()) {
            myEditButtons.setEnabled(true);
            myEditButtons.setVisible(true);
            myInfoHolder.setVisible(true);
            //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            myEditButtons.add(myEditActions);
            myEditButtons.setVisible(true);
            add(myEditButtons, BorderLayout.SOUTH);
            myInfoHolder.setVisible(true);
            add(myInfoHolder, BorderLayout.CENTER);
        }
    }

    private JPanel createUserInfoPanel() {
        JPanel displayInfo = new JPanel();

        displayInfo.setLayout(new GridLayout(0,1));
        JLabel name = new JLabel("Name: " + myUser.getName());
        JLabel username = new JLabel("Username: " + myUser.getUsername());
        JLabel bizNumber = new JLabel("Business Phone Number: " + myUser.getBusinessPhoneNumber());
        JLabel bizEmail = new JLabel("Business Email: " + myUser.getBusinessEmail());
        JLabel bizAddress = new JLabel("Business Address: " + myUser.getBusinessAddress());

        displayInfo.add(name);
        displayInfo.add(username);
        displayInfo.add(bizNumber);
        displayInfo.add(bizEmail);
        displayInfo.add(bizAddress);
        displayInfo.add(myUpcomingAuctionLabel);
        return displayInfo;
    }

    private JPanel createAuctionItemPanel() {

        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        if (myAuction.getItems().isEmpty()) {
            JLabel empty = new JLabel("You have no items yet.");
            p.add(empty, BorderLayout.NORTH);
        } else {
            AuctionItem[] auctionItems = myAuction.getItems().toArray(new AuctionItem[0]);
            auctionItemList = new JList<>(auctionItems);

            final JScrollPane sp = new JScrollPane(auctionItemList);
            p.add(sp, BorderLayout.CENTER);
        }

        return p;
    }

    /**
     * Handle the observables
     * @param o what called this
     * @param arg what the observable sends
     */
    @Override
    public void update(Observable o, Object arg) {
        //Auction added.
        if(arg == "Auction Added"){
            myLabel.setText("Hi " + myUser.getName() + ", what would you like to do?");
            myUpcomingAuctionLabel.setText("Your upcoming auction: " + myAuction.toString());
            myInitialActions.upcomingAuction.setEnabled(true);
            myInitialActions.auctionRequest.setEnabled(false);
        //Auction cancelled.
        } else if(arg == "Auction Cancelled") {
            myLabel.setText("Hi " + myUser.getName() + "! You have no upcoming auction." +
                    " What would you like to do?");
            myUpcomingAuctionLabel.setText("You have no upcoming auction yet!");
            myInitialActions.upcomingAuction.setEnabled(false);
            myInitialActions.auctionRequest.setEnabled(true);
        //Item added.
        } else if(arg == "Item Added") {

        //Item removed.
        } else if(arg == "Item Removed") {

        //
        }
    }

    class InitialActionsPanel extends JPanel {

        private JButton upcomingAuction;

        private JButton auctionRequest;

        InitialActionsPanel() {
            super();
            setUp();
        }

        private void setUp() {
            upcomingAuction = new JButton("View Upcoming Auction");
            UpcomingAuctionListener upcoming = new UpcomingAuctionListener();
            upcomingAuction.addActionListener(upcoming);
            add(upcomingAuction);

            auctionRequest = new JButton("Submit an Auction Request");
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
            GoBackHomeListener homeFromView = new GoBackHomeListener();
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
            GoBackHomeListener homeFromSubmit = new GoBackHomeListener();
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
            GoBackHomeListener homeFromEdit = new GoBackHomeListener();
            goBack.addActionListener(homeFromEdit);
            add(goBack);
        }
    }

    private class UpcomingAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewUpcomingAuction();
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

    private class SubmitAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    private class GoBackHomeListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myViewAuctionButtons.setVisible(false);
            mySubmitAuctionButtons.setVisible(false);
            myEditButtons.setVisible(false);
            myInfoHolder.setVisible(false);
            myItemListPanel.setVisible(false);
            myInitialButtons.setVisible(true);
        }
    }
}
