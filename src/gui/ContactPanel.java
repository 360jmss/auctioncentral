package gui;

import model.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is the GUI for a contact from an organization.
 *
 * @author Jessica Sills
 * @version December 4 2016
 */
public class ContactPanel extends UserPanel implements Observer {

    /** The current user; a contact person. */
    private Contact myUser;

    /** The contact's auction; null if they have no auction. */
    private Auction myAuction;

    /** The index at which the list selector is at. */
    private int myItemIndex;

    /** The list of items in an auction. */
    private JList<AuctionItem> auctionItemList;

    /** The label for showing info. */
    private JLabel myLabel;

    /** A label for a contact's upcoming label. */
    private JLabel myUpcomingAuctionLabel;

    /** Panel to hold the initial user action buttons. */
    private JPanel myInitialButtons;

    /** Panel to hold the view auction options. */
    private JPanel myViewAuctionButtons;

    /** Panel to hold the submit auction request buttons. */
    private JPanel mySubmitAuctionButtons;

    /** Panel to hold the edit options for the contact; currently just 'go back'. */
    private JPanel myEditButtons;

    /** Panel to hold a contact's information. */
    private JPanel myInfoHolder;

    /** Panel to hold all of the items in an auction. */
    private JPanel myItemListPanel;

    /** Initial actions for the user; View auction, Submit auction request, Cancel auction request, Edit info */
    private InitialActionsPanel myInitialActions;

    /** View auction actions for the user. */
    private ViewAuctionPanel myViewAuctionActions;

    /** Submit auction actions for the user. */
    private SubmitAuctionPanel mySubmitAuctionActions;

    /** Edit info actions for the user. */
    private EditInfoPanel myEditActions;

    /** The list selection model */
    private ListSelectionModel listSelectionModel;

    /** Constructor for the panel */
    ContactPanel(User theUser, Calendar theCalendar) {
        setLayout(new BorderLayout());

        myCalendar = theCalendar;
        myUser = (Contact) theUser;
        myAuction = myCalendar.getContactsAuction(myUser);

        //Add observers.
        myCalendar.addObserver(this);
        if (myAuction != null) {
            myAuction.addObserver(this);
        }

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

    /**
     * viewUpcomingAuction changes the view of the GUI to the list of items in the contact's current auction.
     */
    private void viewUpcomingAuction() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!myViewAuctionButtons.isEnabled()) {
            myViewAuctionButtons.setEnabled(true);
            myViewAuctionButtons.setVisible(true);
            myItemListPanel.setEnabled(true);
            myItemListPanel.setVisible(true);
//            add(myItemListPanel, BorderLayout.CENTER);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            myViewAuctionButtons.add(myViewAuctionActions);
            myViewAuctionButtons.setVisible(true);
            myItemListPanel.setVisible(true);
            add(myItemListPanel, BorderLayout.CENTER);
            add(myViewAuctionButtons, BorderLayout.SOUTH);
        }
    }

    /**
     * viewAuctionRequest changes the view of the GUI to a form that the user can fill out to submit an auction. It is
     * not available to users who already have an auction in the system.
     */
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
        createSubmitAuctionForm();
    }

    private void createSubmitAuctionForm() {
        //Required.
        JTextField auctionDate = new JTextField(20);
        JTextField auctionTime = new JTextField(20);
        //Optional.
        JTextField approximateItems = new JTextField(20);
        JTextField comment = new JTextField(20);
    }

    /**
     * viewEditInfo changes the view of the GUI to the user's information, including whether or not they have an
     * upcoming auction.
     */
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

    /**
     * createUserInfoPanel creates a list of the information that is saved about the user.
     *
     * @return The panel containing the user's contact information and the date of their upcoming auction, if they have
     * one.
     */
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

    /**
     * confirmCancelItem first confirms whether a user wants to cancel an item, then performs the cancel if they do.
     */
    private void confirmCancelItem() {
        int dialogOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this item?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogOption == 0) {
            if (myAuction.isCancelable()) {
                myAuction.removeItem(myItemIndex);
            } else {
                JOptionPane.showMessageDialog(null, "You cannot cancel this item because it is less than 2 days " +
                        "before the auction start time.");
            }
        }
    }

    /**
     * confirmCancelAuction first confirms whether a user wants to cancel an auction, then performs the cancel if they
     * do and takes them back to their home page.
     */
    private void confirmCancelAuction() {
        int dialogOption = JOptionPane.showConfirmDialog(null, "Are you sure you want to cancel this auction?",
                "Warning", JOptionPane.YES_NO_OPTION);
        if (dialogOption == 0) {
            if (myAuction.isCancelable()) {
                myCalendar.cancelAuction(myAuction);
                myViewAuctionButtons.setVisible(false);
                mySubmitAuctionButtons.setVisible(false);
                myEditButtons.setVisible(false);
                myInfoHolder.setVisible(false);
                myItemListPanel.setVisible(false);
                myInitialButtons.setVisible(true);
                JOptionPane.showMessageDialog(null, "You have successfully cancelled your auction.");
            } else {
                JOptionPane.showMessageDialog(null, "You cannot cancel your auction because it is less than 2 days " +
                        "before the auction start time.");
            }
        }
    }

    /**
     * createAuctionItemPanel creates a scrollable, selectable list of items in an auction.
     *
     * @return The panel that contains a list of items.
     */
    private JPanel createAuctionItemPanel() {

        JPanel p = new JPanel();
        p.setLayout(new BorderLayout());

        if (myAuction == null || myAuction.getItems().isEmpty()) {
            JLabel empty = new JLabel("You have no items yet.");
            p.add(empty, BorderLayout.NORTH);
        } else {
            AuctionItem[] auctionItems = myAuction.getItems().toArray(new AuctionItem[0]);
            auctionItemList = new JList<>(auctionItems);

            listSelectionModel = auctionItemList.getSelectionModel();
            listSelectionModel.addListSelectionListener(
                    new ItemListSelectionHandler()
            );
            listSelectionModel.setSelectionMode(listSelectionModel.SINGLE_SELECTION);

            JScrollPane sp = new JScrollPane(auctionItemList);
            p.add(sp, BorderLayout.CENTER);
        }

        return p;
    }

    /**
     * The Item List Selection Handler.
     */
    class ItemListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            int firstIndex = lsm.getLeadSelectionIndex();
            myItemIndex = firstIndex;
            System.out.println("The item index is: " + myItemIndex);
        }
    }

    /**
     * Handle the observables.
     *
     * @param o What called this
     * @param arg What the observable sends
     */
    @Override
    public void update(Observable o, Object arg) {
        //Auction added.
        if(arg.equals("Auction Added")){
            myLabel.setText("Hi " + myUser.getName() + ", what would you like to do?");
            myUpcomingAuctionLabel.setText("Your upcoming auction: " + myAuction.toString());
            myInitialActions.upcomingAuction.setEnabled(true);
            myInitialActions.auctionRequest.setEnabled(false);
            myAuction.addObserver(this);
        //Auction cancelled.
        } else if(arg.equals("Auction Cancelled")) {
            myLabel.setText("Hi " + myUser.getName() + "! You have no upcoming auction." +
                    " What would you like to do?");
            myUpcomingAuctionLabel.setText("You have no upcoming auction yet!");
            myInitialActions.upcomingAuction.setEnabled(false);
            myInitialActions.auctionRequest.setEnabled(true);
        //Item added.
        } else if(arg.equals("Item Added")) {
            myItemListPanel = createAuctionItemPanel();
        //Item removed.
        } else if(arg.equals("Item Removed")) {
            myItemListPanel = createAuctionItemPanel();
        }
    }

    /**
     * Creates a panel that contains the first button options that the user will see.
     */
    class InitialActionsPanel extends JPanel {

        /** A button that takes the user to the upcoming auction. */
        private JButton upcomingAuction;

        /** A button that takes the user to an auction request form. */
        private JButton auctionRequest;

        /**
         * Calls the super constructor and then calls setup.
         */
        InitialActionsPanel() {
            super();
            setUp();
        }

        /**
         * Creates the first three buttons a user will see and then adds them to the panel.
         */
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

    /**
     * Creates a panel that contains the button options that are associated with the auction and auction items.
     */
    class ViewAuctionPanel extends JPanel {

        /**
         * Calls the super constructor and then calls setup.
         */
        ViewAuctionPanel() {
            super();
            setUp();
        }

        /**
         * Creates the buttons associated with a auction and auction items and then adds them to the panel.
         */
        private void setUp() {
            JButton addItem = new JButton("Add an Item");
            AddItemListener add = new AddItemListener();
            addItem.addActionListener(add);
            add(addItem);

            JButton cancelItem = new JButton("Cancel Selected Item");
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

    /**
     * Creates a panel that contains the button options that are associated submitting an auction request.
     */
    class SubmitAuctionPanel extends JPanel {

        /**
         * Calls the super constructor and then calls setup.
         */
        SubmitAuctionPanel() {
            super();
            setUp();
        }

        /**
         * Creates the buttons associated with submitting an auction request and then adds them to the panel.
         */
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

    /**
     * Creates a panel that contains the button options that are associated editing a user's info.
     */
    class EditInfoPanel extends JPanel {

        /**
         * Calls the super constructor and then calls setup.
         */
        EditInfoPanel() {
            super();
            setUp();
        }

        /**
         * Creates the buttons associated with editing a user's info and then adds them to the panel.
         */
        private void setUp() {
            JButton goBack = new JButton("Go Back");
            GoBackHomeListener homeFromEdit = new GoBackHomeListener();
            goBack.addActionListener(homeFromEdit);
            add(goBack);
        }
    }

    /**
     * Creates a listener for the upcoming auction button.
     */
    private class UpcomingAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewUpcomingAuction();
        }
    }

    /**
     * Creates a listener for the auction request button.
     */
    private class AuctionRequestListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewAuctionRequest();
        }
    }

    /**
     * Creates a listener for the edit info button.
     */
    private class EditInfoListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            viewEditInfo();
        }
    }

    /**
     * Creates a listener for the add an item button.
     */
    private class AddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    /**
     * Creates a listener for the cancel an item button.
     */
    private class CancelItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            confirmCancelItem();
        }
    }

    /**
     * Creates a listener for the cancel this auction button.
     */
    private class CancelAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            confirmCancelAuction();
        }
    }

    /**
     * Creates a listener for the submit auction request button.
     */
    private class SubmitAuctionListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);

        }
    }

    /**
     * Creates a listener for the go back button.
     */
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
