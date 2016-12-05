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

public class BidderPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** action buttons for the user*/
    private ActionButtons actionButtons;

    /** panel south of the bidderpanel*/
    private JPanel southOfBidderPanel;

    private JPanel auctionListPanel;

    private JPanel centerOfBidderPanel;

    /** The list of auctions */
    private JList<Auction> auctionsList;

    /**The list of items. */
    private JList<AuctionItem> itemList;

    /**The list selection model */
    private ListSelectionModel listSelectionModel;

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
        actionButtons = new ActionButtons();
        myLabel = new JLabel("Hi " + myUser.getName() + ", what would you like to do?");
        southOfBidderPanel = new JPanel();
        centerOfBidderPanel = new JPanel();
        centerOfBidderPanel.setLayout(new BorderLayout());
        auctionListPanel = makeAuctionListPanel();
        myAuctionItemListPanel = makeAuctionItemListPanel();
        myBidOnItemPanel = null;
        myBid = 0.00;

        setLayout(new BorderLayout());
        add(myLabel, BorderLayout.NORTH);
        southOfBidderPanel.add(actionButtons);
        add(southOfBidderPanel, BorderLayout.SOUTH);
        add(centerOfBidderPanel, BorderLayout.CENTER);
    }


    /**
     * Creates a panel to hold the specified list of auctions.
     *
     * @return The created panel
     */
    private JPanel makeAuctionListPanel() {
        Auction[] auctions = myCalendar.getAuctions().toArray(new Auction[0]);
        auctionsList = new JList<>(auctions);

        listSelectionModel = auctionsList.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new AuctionListSelectionHandler()
        );
        listSelectionModel.setSelectionMode(listSelectionModel.SINGLE_SELECTION);
        final JScrollPane sp = new JScrollPane(auctionsList);
        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(sp, BorderLayout.CENTER);

        return p;
    }

    private JPanel makeAboutItemPanel() {
        StringBuilder sb = new StringBuilder();
        AuctionItem i = myCalendar.getAuctions().get(myAuctionIndex).getItems().get(myAuctionItemIndex);
        sb.append("Item Unique ID: " + i.getUniqueID() + "\n");
        sb.append("Item Name: "
                + i.getName() + "\n");
        sb.append("Item Condition: "
                + i.getCondition() + "\n");
        sb.append("Item Size: " + i.getSize());
        sb.append(String.format("Item Minimum Bid: %.2f\n", i.getMinBid()));


        JPanel p = new JPanel();
        JTextArea aboutItem = new JTextArea(sb.toString(), 50, 60);
        aboutItem.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(aboutItem);

        p.add(scrollPane);
        return p;
    }

    private JPanel makeBidTextFieldPanel() {
        JPanel p = new JPanel();
        JLabel newBid = new JLabel("New Bid: ");
        JTextField textField = new JTextField(20);

        p.add(newBid);
        p.add(textField);

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
     * Creates a panel to hold the specified list of items in an auciton.
     *
     * @return The created panel
     */
    private JPanel makeAuctionItemListPanel() {
        AuctionItem[] items = new AuctionItem[myCalendar.getAuctions().get(myAuctionIndex).getItems().size()];
        items = myCalendar.getAuctions().get(myAuctionIndex).getItems().toArray(items);
        itemList = new JList<>(items);

        listSelectionModel = itemList.getSelectionModel();
        listSelectionModel.addListSelectionListener(
                new AuctionItemListSelectionHandler()
        );
        listSelectionModel.setSelectionMode(listSelectionModel.SINGLE_SELECTION);
        final JScrollPane sp = new JScrollPane(itemList);
        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(sp, BorderLayout.CENTER);

        return p;
    }


    /**
     * Update the auction Item list panel
     */
    private void updateAuctionItemListPanel() {
        AuctionItem[] items = new AuctionItem[myCalendar.getAuctions().get(myAuctionIndex).getItems().size()];
        items = myCalendar.getAuctions().get(myAuctionIndex).getItems().toArray(items);
        itemList.setListData(items);
    }

    /**
     * JPanel for the buttons.
     */
    class ActionButtons extends JPanel {

        /**view auctions button*/
        private JButton viewAuctions;

        /**view items in the auction button*/
        private JButton viewItems;

        /**Bid on an item button*/
        private JButton bidOnItem;

        /**go back button*/
        private JButton goBack;

        private JLabel newBid;

        private JTextField textField;

        /**place bid button*/
        private JButton placeBid;

        ActionButtons() {
            super();
            viewAuctions = new JButton("View Auctions");
            viewItems = new JButton("View Auction Items");
            bidOnItem = new JButton("Bid on Selected Item");
            goBack = new JButton("Go Back");
            placeBid = new JButton("Place Bid");
            newBid = new JLabel("New Bid: ");
            textField = new JTextField(20);
            setUp();
        }

        private void setUp() {
            viewAuctions.addActionListener(new ViewAuctionsListener());
            viewItems.addActionListener(new ViewItemsListener());
            goBack.addActionListener(new GoBackListener());
            bidOnItem.addActionListener(new BidOnItemListener());
            textField.addActionListener(new PlaceBidActionListener());
            placeBid.addActionListener(new PlaceBidActionListener());

            add(newBid);
            add(textField);
            add(viewAuctions);
            add(viewItems);
            add(bidOnItem);
            add(placeBid);
            add(goBack);

            viewItems.setVisible(false);
            bidOnItem.setVisible(false);
            goBack.setVisible(false);
            newBid.setVisible(false);
            textField.setVisible(false);
            placeBid.setVisible(false);
        }

        class TextFieldActionListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myBid = Double.parseDouble(textField.getText());
                System.out.println("Current bid updated: $" + myBid);
            }
        }

        class PlaceBidActionListener implements  ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Double text = Double.parseDouble(textField.getText());
                Auction a = myCalendar.getAuctions().get(myAuctionIndex);
                AuctionItem i = a.getItems().get(myAuctionItemIndex);
                boolean addedSuccessfully = i.addBid(myUser.getName(), text);
                if(addedSuccessfully) {
                    myBid = text;
                    System.out.println("Current bid updated: $" + myBid);
                    System.out.println("Added bid: " + i.getBid(myUser.getName()));
                } else {
                    JOptionPane.showMessageDialog(null,
                            String.format("Error: Please enter bid higher than miniminum bid: $%.2f", i.getMinBid()),
                            "Error Massage", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        class ViewAuctionsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewAuctions.setVisible(false);
                viewItems.setVisible(true);
                goBack.setVisible(true);
                centerOfBidderPanel.add(auctionListPanel);
            }
        }

        class ViewItemsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewItems.setVisible(false);
                bidOnItem.setVisible(true);
                auctionListPanel.setVisible(false);
                updateAuctionItemListPanel();
                centerOfBidderPanel.add(myAuctionItemListPanel);
            }
        }

        class PlaceBidListener implements  ActionListener {
            @Override
            public void actionPerformed(ActionEvent acitonEvent) {

            }
        }

        class GoBackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(viewItems.isVisible()) { //auction list panel
                    viewItems.setVisible(false);
                    viewAuctions.setVisible(true);
                    goBack.setVisible(false);
                    auctionListPanel.setVisible(false);
                } else if(bidOnItem.isVisible()) { //item list panel
                    bidOnItem.setVisible(false);
                    viewItems.setVisible(true);
                    auctionListPanel.setVisible(true);
                    myAuctionItemListPanel.setVisible(false);
                } else if(placeBid.isVisible()) { //about item panel
                    bidOnItem.setVisible(true);
                    viewItems.setVisible(false);
                    placeBid.setVisible(false);
                    textField.setVisible(false);
                    myAuctionItemListPanel.setVisible(true);
                    myBidOnItemPanel.setVisible(false);
                    newBid.setVisible(false);
                }
            }
        }

        class BidOnItemListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                myBidOnItemPanel = makeAboutItemPanel();
                centerOfBidderPanel.add(myBidOnItemPanel);
                myAuctionItemListPanel.setVisible(false);
                newBid.setVisible(true);
                textField.setVisible(true);
                placeBid.setVisible(true);
                bidOnItem.setVisible(false);
            }
        }
    }
}
