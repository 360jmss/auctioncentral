package gui;

import model.*;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Observable;
import java.util.Observer;

/**
 * This class is the GUI for a contact from an organization.
 *
 * @author Jessica Sills
 * @version December 4 2016
 */
public class ContactPanel extends UserPanel implements Observer {

    /**font size for jtextarea, label, and jlist*/
    private static final int FONT_SIZE = 12;

    /** The current user; a contact person. */
    private Contact myUser;

    /** The contact's auction; null if they have no auction. */
    private Auction myAuction;

    /** The index at which the list selector is at. */
    private int myItemIndex;

    /** The condition of an item. */
    private String myItemCondition;

    /** The size of an item. */
    private int myItemSize;

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

    /** Panel to hold the add item options for the contact. */
    private JPanel myAddItemButtons;

    /** Panel to hold a contact's information. */
    private JPanel myInfoHolder;

    /** Panel to hold all of the items in an auction. */
    private JPanel myItemListPanel;

    /** Panel to hold the auction request form. */
    private JPanel myAuctionFormPanel;

    /** Panel to hold the add item form. */
    private JPanel myAddItemFormPanel;

    /** A text field for contact to enter an auction date. */
    private JTextField myAuctionDate;

    /** A text field for contact to enter the approximate number of items. */
    private JTextField myApproximateItems;

    /** A text field for contact to enter a comment for their auction. */
    private JTextField myComment;

    /** A text field for contact to enter an item name. */
    private JTextField myItemName;

    /** A text field for contact to enter a minimum bid for an item. */
    private JTextField myMinimumBid;

    /** A text field for contact to enter a donor name for an item. */
    private JTextField myDonorName;

    /** A text field for contact to enter an item description. */
    private JTextField myItemDescription;

    /** A text field for contact to enter an item comment. */
    private JTextField myItemComment;

    /** A drop down menu for the condition of an item. */
    private JComboBox<String[]> myItemConditionDropDown;

    /** A drop down menu for the size of an item. */
    private JComboBox<String[]> myItemSizeDropDown;

    /** Initial actions for the user; View auction, Submit auction request, Cancel auction request, Edit info */
    private InitialActionsPanel myInitialActions;

    /** View auction actions for the user. */
    private ViewAuctionPanel myViewAuctionActions;

    /** Submit auction actions for the user. */
    private SubmitAuctionPanel mySubmitAuctionActions;

    /** Edit info actions for the user. */
    private EditInfoPanel myEditActions;

    /** Add item actions for the user. */
    private AddItemPanel myAddItemActions;

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
            myLabel = new JLabel("Hi " + myUser.getName() + ", you already have an upcoming auction and cannot submit" +
                    " an auction request. What would you like to do?");
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

        //Initialize add item options but do not add them here.
        myAddItemButtons = new JPanel();
        myAddItemActions = new AddItemPanel();

        //Initialize item list panel.
        myItemListPanel = createAuctionItemPanel();

        //Initialize auction request form panel.
        myAuctionFormPanel = createSubmitAuctionForm();

        //Initialize add item form panel.
        myAddItemFormPanel = createAddItemForm();

        //Initialize information holder for displaying user's information.
        myInfoHolder = createUserInfoPanel();

        //Add initial labels and panels.
        add(myLabel, BorderLayout.NORTH);
        add(myInitialButtons, BorderLayout.SOUTH);
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
            myAuctionFormPanel.setEnabled(true);
            myAuctionFormPanel.setVisible(true);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            mySubmitAuctionButtons.add(mySubmitAuctionActions);
            mySubmitAuctionButtons.setVisible(true);
            add(mySubmitAuctionButtons, BorderLayout.SOUTH);
            myAuctionFormPanel.setVisible(true);
            add(myAuctionFormPanel, BorderLayout.CENTER);
        }
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
     * viewAddItem changes the view of the GUI to the the add item form page.
     */
    private void viewAddItem() {
        //If these buttons have already been added to the panel and this method was called, then they are not
        //enabled and simply need to be re-enabled and set to visible.
        if (!myAddItemButtons.isEnabled()) {
            myAddItemButtons.setEnabled(true);
            myAddItemButtons.setVisible(true);
            myAddItemFormPanel.setVisible(true);
        //Otherwise, this is the first time this method was called and the buttons need to be added to the panel.
        } else {
            myAddItemButtons.add(myAddItemActions);
            myAddItemButtons.setVisible(true);
            add(myAddItemButtons, BorderLayout.SOUTH);
            myAddItemFormPanel.setVisible(true);
            add(myAddItemFormPanel, BorderLayout.CENTER);
        }
    }

    /**
     * Creates a JPanel with a form for the contact to fill out to submit an auction.
     * @return A JPanel with the auction form on it.
     */
    private JPanel createSubmitAuctionForm() {
        //Required fields.
        JLabel auctionDateIndicator = new JLabel("Please enter the auction date and time (YYYY-MM-DD HH:mm): ");
        myAuctionDate = new JTextField(20);

        //Optional fields.
        JLabel approxItemsIndicator = new JLabel("If applicable, please enter the approximate number of items: ");
        myApproximateItems = new JTextField(20);
        JLabel commentIndicator = new JLabel("If applicable, please enter a comment for this auction: ");
        myComment = new JTextField(20);

        JPanel auctionFormPanel = new JPanel();
        auctionFormPanel.setLayout(new GridLayout(0,2));

        auctionFormPanel.add(auctionDateIndicator);
        auctionFormPanel.add(myAuctionDate);
        auctionFormPanel.add(approxItemsIndicator);
        auctionFormPanel.add(myApproximateItems);
        auctionFormPanel.add(commentIndicator);
        auctionFormPanel.add(myComment);

        return auctionFormPanel;
    }

    /**
     * Creates a JPanel with a form for the contact to fill out to submit an item
     * @return A JPanel with an add item form on it.
     */
    private JPanel createAddItemForm() {
        String[] conditions = {"Acceptable", "Good", "Very Good", "Like New", "New"};
        String[] sizes = {"Small", "Medium", "Large"};

        //Required fields.
        JLabel name = new JLabel("Please enter the item name: ");
        myItemName = new JTextField(20);
        JLabel condition = new JLabel("Please choose the item's condition: ");
        myItemConditionDropDown = new JComboBox(conditions);
        myItemConditionDropDown.setSelectedIndex(0);
        myItemCondition = (String)myItemConditionDropDown.getSelectedItem();
        myItemConditionDropDown.addActionListener(new ItemConditionSelectionHandler());
        JLabel size = new JLabel("Please choose the item's size: ");
        myItemSizeDropDown = new JComboBox(sizes);
        myItemSizeDropDown.setSelectedIndex(0);
        myItemSize = myItemSizeDropDown.getSelectedIndex() + 1;
        myItemSizeDropDown.addActionListener(new ItemSizeSelectionHandler());
        JLabel minBid = new JLabel("Please enter the minimum bid: ");
        myMinimumBid = new JTextField(20);

        //Optional fields.
        JLabel donorName = new JLabel("If applicable, please enter the name of the donor: ");
        myDonorName = new JTextField(20);
        JLabel itemDescription = new JLabel("If applicable, please enter an item description: ");
        myItemDescription = new JTextField(20);
        JLabel itemComment = new JLabel("If applicable, please enter a comment for the item: ");
        myItemComment = new JTextField(20);

        JPanel addItemFormPanel = new JPanel();
        addItemFormPanel.setLayout(new GridLayout(0,2));

        addItemFormPanel.add(name);
        addItemFormPanel.add(myItemName);
        addItemFormPanel.add(condition);
        addItemFormPanel.add(myItemConditionDropDown);
        addItemFormPanel.add(size);
        addItemFormPanel.add(myItemSizeDropDown);
        addItemFormPanel.add(minBid);
        addItemFormPanel.add(myMinimumBid);
        addItemFormPanel.add(donorName);
        addItemFormPanel.add(myDonorName);
        addItemFormPanel.add(itemDescription);
        addItemFormPanel.add(myItemDescription);
        addItemFormPanel.add(itemComment);
        addItemFormPanel.add(myItemComment);

        return addItemFormPanel;
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
                String item = myAuction.getItems().get(myItemIndex).toString();
                myAuction.removeItem(myItemIndex);
                JOptionPane.showMessageDialog(null, "You have successfully cancelled " + item + ".");
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
     * If the user tries to submit an auction request by clicking the Submit Auction button, this function will make
     * checks to ensure that they entered the correct information, then add the auction to a calendar if they did.
     */
    private void confirmSubmitAuction() {

        String date = myAuctionDate.getText();
        String approxItems = myApproximateItems.getText();
        String comment = myComment.getText();

        if (date.equals("")) {
            JOptionPane.showMessageDialog(null, "Auction date and time is a required field. Please enter an auction " +
                            "date and start time.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

            Auction newAuction = new Auction(dateTime, myUser);

            if (myCalendar.validateAuction(newAuction) == 0) {
                myAuction = newAuction;

                JOptionPane.showMessageDialog(null, "Your auction has been approved! You are being returned to the " +
                        "home page where you can view your auction and add items to it.");

                myCalendar.addAuction(myAuction);

                myViewAuctionButtons.setVisible(false);
                mySubmitAuctionButtons.setVisible(false);
                myEditButtons.setVisible(false);
                myInfoHolder.setVisible(false);
                myItemListPanel.setVisible(false);
                myAuctionFormPanel.setVisible(false);
                myInitialButtons.setVisible(true);
            } else if (myCalendar.validateAuction(newAuction) == 1) {
                JOptionPane.showMessageDialog(null, "Your auction has been denied because you already have an " +
                        "upcoming auction or you already had one within the past year.");
            } else if (myCalendar.validateAuction(newAuction) == 2) {
                JOptionPane.showMessageDialog(null, "Your auction has been denied because you cannot schedule an " +
                        "auction on a date that is less than one week from today's date. Please try a different date" +
                        "and time.");
            } else if (myCalendar.validateAuction(newAuction) == 3) {
                JOptionPane.showMessageDialog(null, "Your auction has been denied because you cannot schedule an " +
                        "auction on a date that is more than one month from today's date. Please try a different " +
                        "date and time.");
            } else if (myCalendar.validateAuction(newAuction) == 4) {
                JOptionPane.showMessageDialog(null, "Your auction has been denied because AuctionCentral already has " +
                        "the max number of auctions scheduled for the day you chose. Please try a different day.");
            } else if (myCalendar.validateAuction(newAuction) == 5) {
                JOptionPane.showMessageDialog(null, "Your auction has been denied because AuctionCentral already has " +
                        "the max number of total auctions scheduled. Please try again later.");
            }

            if (!approxItems.equals("") && myAuction != null) {
                int ai = Integer.parseInt(approxItems);
                myAuction.setEstItems(ai);
            }

            if (!comment.equals("") && myAuction != null) {
                myAuction.setComment(comment);
            }
        }
    }

    /**
     * If the user tries to add an item by clicking the Add This Item button, this function will make checks to
     * ensure that they entered the correct information, then add the item to the auction if they did.
     */
    private void confirmAddItem() {
        String name = myItemName.getText();
        String minBid = myMinimumBid.getText();
        String donor = myDonorName.getText();
        String descrip = myItemDescription.getText();
        String comment = myItemComment.getText();

        if (name.equals("")) {
            JOptionPane.showMessageDialog(null, "The name of the item is a required field. Please enter an item name.",
                    "Warning", JOptionPane.WARNING_MESSAGE);
        } else {
            if (minBid.equals("")) {
                JOptionPane.showMessageDialog(null, "The minimum bid of the item is a required field. Please enter a" +
                                " minimum bid.", "Warning", JOptionPane.WARNING_MESSAGE);
            } else {
                Double min = Double.parseDouble(minBid);
                AuctionItem newItem = new AuctionItem(name, myItemCondition, myItemSize, min);
                newItem.setComment(comment);
                newItem.setDescription(descrip);
                newItem.setDonorName(donor);
                if (myAuction.validateItem(newItem) == true) {
                    myAuction.addItem(newItem);
                    JOptionPane.showMessageDialog(null, "Your item " + newItem.toString() + " has been successfully " +
                            "added! You are being returned to your auction item list.");
                    mySubmitAuctionButtons.setVisible(false);
                    myEditButtons.setVisible(false);
                    myInfoHolder.setVisible(false);
                    myAuctionFormPanel.setVisible(false);
                    myAddItemFormPanel.setVisible(false);
                    myInitialButtons.setVisible(false);
                    myAddItemButtons.setVisible(false);
                    myItemListPanel.setVisible(true);
                    myViewAuctionButtons.setVisible(true);
                } else {
                    JOptionPane.showMessageDialog(null, "Your item was not added because it already exists in your " +
                            "auction item list.", "Warning", JOptionPane.WARNING_MESSAGE);
                }
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
            auctionItemList.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
            listSelectionModel = auctionItemList.getSelectionModel();
            listSelectionModel.addListSelectionListener(
                    new ItemListSelectionHandler()
            );
            listSelectionModel.setSelectionMode(listSelectionModel.SINGLE_SELECTION);

            JScrollPane sp = new JScrollPane(auctionItemList);
            JLabel columnHeaderView = new JLabel(String.format("%5s%15s%15s%15s%15s",
                    "ID", "Name", "Condition", "Size", "Minimum Bid"));
            columnHeaderView.setFont(new Font("monospaced", Font.PLAIN, FONT_SIZE));
            sp.setColumnHeaderView(columnHeaderView);
            p.add(sp, BorderLayout.CENTER);
        }

        return p;
    }

    /**
     * Updates the existing auctionItem Panel
     */
    private void updateAuctionItemPanel() {
        if (myAuction == null || myAuction.getItems().isEmpty()) {
            //TODO Show text that says its empty
            AuctionItem[] auctionItems = myAuction.getItems().toArray(new AuctionItem[0]);
            auctionItemList.setListData(auctionItems);
        } else {
            AuctionItem[] auctionItems = myAuction.getItems().toArray(new AuctionItem[0]);
            auctionItemList.setListData(auctionItems);
        }
    }

    /**
     * The Item List Selection Handler.
     */
    class ItemListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            int firstIndex = lsm.getLeadSelectionIndex();
            myItemIndex = firstIndex;
        }
    }

    /**
     * The item condition drop down menu selection handler.
     */
    class ItemConditionSelectionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            myItemCondition = (String)cb.getSelectedItem();
        }
    }

    /**
     * The item size drop down menu selection handler.
     */
    class ItemSizeSelectionHandler implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            JComboBox cb = (JComboBox)e.getSource();
            myItemSize = cb.getSelectedIndex();
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
            myLabel.setText("Hi " + myUser.getName() + ", you already have an upcoming auction and cannot submit" +
                    " an auction request. What would you like to do?");
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
            if(myAuction != null && myAuction.getItems().size() == 1) {
                myItemListPanel = createAuctionItemPanel();
                myItemListPanel.setVisible(true);
                myItemListPanel.setEnabled(true);
                add(myItemListPanel, BorderLayout.CENTER);
            } else {
                updateAuctionItemPanel();
            }
        //Item removed.
        } else if(arg.equals("Item Removed")) {
            updateAuctionItemPanel();
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
            upcomingAuction = new JButton("View My Auction");
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

        private JButton addItem;

        private JButton cancelItem;

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
            addItem = new JButton("Add an Item");
            AddAnItemListener add = new AddAnItemListener();
            addItem.addActionListener(add);
            add(addItem);

            cancelItem = new JButton("Cancel Selected Item");
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
     * Creates a panel that contains the button options that are associated with adding an item.
     */
    class AddItemPanel extends JPanel {

        private JButton addItem;

        /**
         * Calls the super constructor and then calls setup.
         */
        AddItemPanel() {
            super();
            setUp();
        }

        /**
         * Creates the buttons associated with editing a user's info and then adds them to the panel.
         */
        private void setUp() {
            addItem = new JButton("Add This Item");
            AddThisItemListener add = new AddThisItemListener();
            addItem.addActionListener(add);
            add(addItem);

            JButton goBack = new JButton("Go Back");
            GoBackFromAddItemListener homeFromAddItem = new GoBackFromAddItemListener();
            goBack.addActionListener(homeFromAddItem);
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
    private class AddAnItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            myViewAuctionButtons.setVisible(false);
            myItemListPanel.setVisible(false);
            viewAddItem();
        }
    }

    /**
     * Creates a listener for the add this item button.
     */
    private class AddThisItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            myInitialButtons.setVisible(false);
            myInitialButtons.setEnabled(false);
            confirmAddItem();
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
            if (auctionItemList != null || auctionItemList.isSelectionEmpty()) {
                confirmCancelItem();
            }
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
            confirmSubmitAuction();
        }
    }

    /**
     * Creates a listener for the go back button on the Add Item page.
     */
    private class GoBackFromAddItemListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent actionEvent) {
            mySubmitAuctionButtons.setVisible(false);
            myEditButtons.setVisible(false);
            myInfoHolder.setVisible(false);
            myAuctionFormPanel.setVisible(false);
            myAddItemFormPanel.setVisible(false);
            myInitialButtons.setVisible(false);
            myAddItemButtons.setVisible(false);
            myItemListPanel.setVisible(true);
            myViewAuctionButtons.setVisible(true);
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
            myAuctionFormPanel.setVisible(false);
            myAddItemFormPanel.setVisible(false);
            myInitialButtons.setVisible(true);
        }
    }
}
