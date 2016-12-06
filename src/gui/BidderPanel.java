package gui;

import model.Auction;
import model.AuctionItem;
import model.Calendar;
import model.User;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class BidderPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** action buttons for the user*/
    private ActionButtons myActionButtons;

    /** panel south of the bidderpanel*/
    private JPanel mySouthOfBidderPanel;

    /**the auction list panel*/
    private JPanel myAuctionListPanel;

    /**the center of bidder panel*/
    private JPanel myCenterOfBidderPanel;

    /** The list of auctions */
    private JList<Auction> myAuctionsList;

    /**The list of items. */
    private JList<AuctionItem> myItemList;

    /**The list selection model */
    private ListSelectionModel myListSelectionModel;

    /**Current auction index */
    private int myAuctionIndex;

    /**The auction item list panel */
    private JPanel myAuctionItemListPanel;

    /**The auction item index */
    private int myAuctionItemIndex;

    /**The bid on item panel */
    private JPanel myBidOnItemPanel;

    /**The bid by user*/
    private Double myBid;


    /** Constructor for the panel */
    BidderPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = theUser;
        myActionButtons = new ActionButtons();
        myLabel = new JLabel("Hi " + myUser.getName() + ", ");
        mySouthOfBidderPanel = new JPanel();
        myCenterOfBidderPanel = new JPanel();
        myCenterOfBidderPanel.setLayout(new BorderLayout());
        myAuctionListPanel = makeAuctionListPanel();
        myAuctionItemListPanel = makeAuctionItemListPanel();
        myBidOnItemPanel = null;
        myBid = 0.00;

        setLayout(new BorderLayout());
        add(myLabel, BorderLayout.NORTH);
        mySouthOfBidderPanel.add(myActionButtons);
        add(mySouthOfBidderPanel, BorderLayout.SOUTH);
        add(myCenterOfBidderPanel, BorderLayout.CENTER);
    }


    /**
     * Creates a panel to hold the specified list of auctions.
     *
     * @return The created panel
     */
    private JPanel makeAuctionListPanel() {
        Auction[] auctions =  new Auction[myCalendar.getAuctions(LocalDateTime.now()).size()];
        auctions = myCalendar.getAuctions(LocalDateTime.now()).toArray(auctions);
        myAuctionsList = new JList<>(auctions);

        myListSelectionModel = myAuctionsList.getSelectionModel();
        myListSelectionModel.addListSelectionListener(
                new AuctionListSelectionHandler()
        );
        myListSelectionModel.setSelectionMode(myListSelectionModel.SINGLE_SELECTION);
        final JScrollPane sp = new JScrollPane(myAuctionsList);
        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(sp, BorderLayout.CENTER);

        return p;
    }

    /**
     * Creates a panel to hold the specified list of items in an auciton.
     *
     * @return The created panel
     */
    private JPanel makeAuctionItemListPanel() {
        AuctionItem[] items =
                new AuctionItem[myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex).getItems().size()];
        items = myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex).getItems().toArray(items);
        myItemList = new JList<>(items);
        myItemList.setCellRenderer(new AuctionItemCellRenderer());
        //myItemList.setFont(new Font("monospaced", Font.PLAIN, 12));
        myListSelectionModel = myItemList.getSelectionModel();
        myListSelectionModel.addListSelectionListener(
                new AuctionItemListSelectionHandler()
        );
        myListSelectionModel.setSelectionMode(myListSelectionModel.SINGLE_SELECTION);
        final JScrollPane sp = new JScrollPane(myItemList);
        JLabel label = new JLabel();
        sp.setColumnHeaderView(new JLabel(String.format("%5s%45s%30s%50s", "ID", "Name", "Condition", "Minimum Bid")));
        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(sp, BorderLayout.CENTER);

        return p;
    }


    /**
     * Creates the about item panel.
     *
     * @return the about item panel made.
     */
    private JPanel makeAboutItemPanel() {
        StringBuilder sb = new StringBuilder();
        AuctionItem i = myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex).getItems().get(myAuctionItemIndex);
        sb.append("Item Unique ID: " + i.getUniqueID() + "\n");
        sb.append("Item Name: "
                + i.getName() + "\n");
        sb.append("Item Condition: "
                + i.getCondition() + "\n");
        sb.append("Item Size: " + i.getSize() + "\n");
        sb.append(String.format("Item Minimum Bid: %.2f\n", i.getMinBid()));
        if(i.getBid(myUser.getName()) != null) {
            sb.append("My bid: " + i.getBid(myUser.getName()));
        }



        JPanel p = new JPanel();
        JTextArea aboutItem = new JTextArea(sb.toString(), 50, 60);
        aboutItem.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(aboutItem);

        p.add(scrollPane);
        return p;
    }

    /**
     * The Auction List Selection Handler.
     */
    class AuctionListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            int firstIndex = lsm.getLeadSelectionIndex();
            myAuctionIndex = firstIndex;
            //myAuctionItemListPanel = makeAuctionItemListPanel();
            System.out.println("The auction index is: " + myAuctionIndex);
        }
    }

    /**
     * The Auction Item List Selection Handler.
     */
    class AuctionItemListSelectionHandler implements ListSelectionListener {
        public void valueChanged(ListSelectionEvent e) {
            ListSelectionModel lsm = (ListSelectionModel) e.getSource();

            int firstIndex = lsm.getLeadSelectionIndex();
            myAuctionItemIndex = firstIndex;
            System.out.println("The auction item index is: " + myAuctionItemIndex);
        }
    }



    /**
     * Update the auction Item list panel
     */
    private void updateAuctionItemListPanel() {
        AuctionItem[] items = new AuctionItem[myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex).getItems().size()];
        items = myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex).getItems().toArray(items);
        myItemList.setListData(items);
    }

    /**
     * Display the list of auction items with the users bid
     */
    class AuctionItemCellRenderer extends DefaultListCellRenderer {

        /* This is the only method defined by ListCellRenderer.  We just
         * reconfigure the Jlabel each time we're called.
         */
        public Component getListCellRendererComponent(
                JList list,
                Object value,   // value to display
                int index,      // cell index
                boolean iss,    // is the cell selected
                boolean chf)    // the list and the cell have the focus
        {
        /* The DefaultListCellRenderer class will take care of
         * the JLabels text property, it's foreground and background
         * colors, and so on.
         */
            super.getListCellRendererComponent(list, value, index, iss, chf);

        /* We additionally set the JLabels icon property here.
         */
            String s = value.toString();
            if (value instanceof AuctionItem) {
                AuctionItem auctItem = (AuctionItem) value;
                double bid = auctItem.getBid(myUser.getName());
                if ( bid != 0.0) {
                    s = s + " My Bid: " + bid;
                }
            }
            setText(s);
            return this;
        }
    }

    /**
     * JPanel for the buttons.
     */
    class ActionButtons extends JPanel {

        /**view auctions button*/
        private JButton myViewAuctionsButton;

        /**view items in the auction button*/
        private JButton myViewItemsButton;

        /**Bid on an item button*/
        private JButton myBidOnItemButton;

        /**go back button*/
        private JButton myGoBackButton;

        /**the new bid label*/
        private JLabel myNewBidLabel;

        /**the bid text field*/
        private JTextField myBidTextField;

        /**place bid button*/
        private JButton myPlaceBidButton;

        /**cancel bid button*/
        private JButton myCancelBidButton;

        /**
         * Action Button constructor.
         */
        ActionButtons() {
            super();
            myViewAuctionsButton = new JButton("View Auctions");
            myViewItemsButton = new JButton("View Auction Items");
            myBidOnItemButton = new JButton("View/Cancel/Bid on Item");
            myCancelBidButton = new JButton("Cancel Bid");
            myGoBackButton = new JButton("Go Back");
            myPlaceBidButton = new JButton("Place Bid");
            myNewBidLabel = new JLabel("New Bid: ");

            myBidTextField = new JTextField(20);
            setUp();
        }

        /**
         * Sets up the Action button with listeners, adds to panel, visibility.
         */
        private void setUp() {
            myViewAuctionsButton.addActionListener(new ViewAuctionsListener());
            myViewItemsButton.addActionListener(new ViewItemsListener());
            myGoBackButton.addActionListener(new GoBackListener());
            myBidOnItemButton.addActionListener(new BidOnItemListener());
            myBidTextField.addActionListener(new PlaceBidActionListener());
            myPlaceBidButton.addActionListener(new PlaceBidActionListener());
            myCancelBidButton.addActionListener(new CancelBidActionListener());


            add(myNewBidLabel);
            add(myBidTextField);
            add(myViewAuctionsButton);
            add(myViewItemsButton);
            add(myBidOnItemButton);
            add(myPlaceBidButton);
            add(myCancelBidButton);
            add(myGoBackButton);

            myViewItemsButton.setVisible(false);
            myBidOnItemButton.setVisible(false);
            myGoBackButton.setVisible(false);
            myNewBidLabel.setVisible(false);
            myBidTextField.setVisible(false);
            myPlaceBidButton.setVisible(false);
            myCancelBidButton.setVisible(false);
        }

        /**
         * Listener for placing a bid button action.
         */
        class PlaceBidActionListener implements  ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Double text = Double.parseDouble(myBidTextField.getText());
                Auction a = myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex);
                AuctionItem i = a.getItems().get(myAuctionItemIndex);
                boolean addedSuccessfully = i.addBid(myUser.getName(), text);
                if(addedSuccessfully) {
                    myBid = text;
                    System.out.println("Current bid updated: $" + myBid);
                    System.out.println("Added bid: " + i.getBid(myUser.getName()));
                    myCenterOfBidderPanel.remove(myBidOnItemPanel);
                    myBidOnItemPanel = makeAboutItemPanel();
                    myCenterOfBidderPanel.add(myBidOnItemPanel);
                    myBidOnItemPanel.revalidate();
                    myBidOnItemPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null,
                            String.format("Error: Please enter bid higher than miniminum bid: $%.2f", i.getMinBid()),
                            "Error Massage", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        class CancelBidActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Auction a = myCalendar.getAuctions(LocalDateTime.now()).get(myAuctionIndex);
                AuctionItem i = a.getItems().get(myAuctionItemIndex);
                int cancelledSuccessfully = i.cancelBid(myUser.getName());
                if(cancelledSuccessfully == 0) {
                    myCenterOfBidderPanel.remove(myBidOnItemPanel);
                    myBidOnItemPanel = makeAboutItemPanel();
                    myCenterOfBidderPanel.add(myBidOnItemPanel);
                    myBidOnItemPanel.revalidate();
                    myBidOnItemPanel.repaint();
                } else {
                    JOptionPane.showMessageDialog(null,
                            "Error: You had no bid to begin with.",
                            "Error Massage", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        /**
         * Listener for viewing auctions button.
         */
        class ViewAuctionsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myViewAuctionsButton.setVisible(false);
                myViewItemsButton.setVisible(true);
                myGoBackButton.setVisible(true);
                myCenterOfBidderPanel.add(myAuctionListPanel);
                myLabel.setText("Please select an auction from the list that you would like to view.");

            }
        }

        /**
         * Listener for view item button.
         */
        class ViewItemsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myViewItemsButton.setVisible(false);
                myBidOnItemButton.setVisible(true);
                myAuctionListPanel.setVisible(false);
                myAuctionItemListPanel.setVisible(true);
                updateAuctionItemListPanel();
                myCenterOfBidderPanel.add(myAuctionItemListPanel);
                myLabel.setText("Please select the item from the list that you would like to view or bid on.");
            }
        }

        /**
         * Listener for go back button.
         */
        class GoBackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(myViewItemsButton.isVisible()) { //auction list panel
                    myViewItemsButton.setVisible(false);
                    myViewAuctionsButton.setVisible(true);
                    myGoBackButton.setVisible(false);
                    myAuctionListPanel.setVisible(false);
                    myLabel.setText("Hi " + myUser.getName() + ",");
                } else if(myBidOnItemButton.isVisible()) { //item list panel
                    myBidOnItemButton.setVisible(false);
                    myViewItemsButton.setVisible(true);
                    myAuctionListPanel.setVisible(true);
                    myAuctionItemListPanel.setVisible(false);
                    myLabel.setText("Please select an auction from the list that you would like to view.");
                } else if(myPlaceBidButton.isVisible()) { //about item panel
                    myBidOnItemButton.setVisible(true);
                    myViewItemsButton.setVisible(false);
                    myPlaceBidButton.setVisible(false);
                    myCancelBidButton.setVisible(false);
                    myBidTextField.setVisible(false);
                    myAuctionItemListPanel.setVisible(true);
                    myBidOnItemPanel.setVisible(false);
                    myNewBidLabel.setVisible(false);
                    myLabel.setText("Please select the item from the list that you would like to view or bid on.");

                }
            }
        }

        /**
         * Listener for Bid on item button.
         */
        class BidOnItemListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myBidOnItemPanel = makeAboutItemPanel();
                myCenterOfBidderPanel.add(myBidOnItemPanel);
                myAuctionItemListPanel.setVisible(false);
                myNewBidLabel.setVisible(true);
                myBidTextField.setVisible(true);
                myPlaceBidButton.setVisible(true);
                myCancelBidButton.setVisible(true);
                myBidOnItemButton.setVisible(false);
                myLabel.setText("If you bid, please bid at least the minimum bid.");
            }
        }
    }
}
