package gui;

import model.Auction;
import model.Calendar;
import model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BidderPanel extends UserPanel  {

    /** The label for showing info */
    private JLabel myLabel;

    /** action buttons for the user*/
    private ActionButtons actionButtons;

    /** panel south of the bidderpanel*/
    private JPanel southOfBidderPanel;

    private JPanel itemsPanel;

    private JPanel centerOfBidderPanel;

    /** The list of auctions, items, and such */
    private JList<Auction> auctionsList;


    /** Constructor for the panel */
    BidderPanel(User theUser, Calendar theCalendar) {
        myCalendar = theCalendar;
        myUser = theUser;
        actionButtons = new ActionButtons();
        myLabel = new JLabel("Hi " + myUser.getName() + ", what would you like to do?");
        southOfBidderPanel = new JPanel();
        centerOfBidderPanel = new JPanel();
        centerOfBidderPanel.setLayout(new BorderLayout());
        itemsPanel = makeItemsPanel();


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
    private JPanel makeItemsPanel() {
        Auction[] auctions = myCalendar.getAuctions().toArray(new Auction[0]);
        auctionsList = new JList<>(auctions);

        final JScrollPane sp = new JScrollPane(auctionsList);
        final JPanel p = new JPanel();
        p.setLayout(new BorderLayout());
        p.add(sp, BorderLayout.CENTER);

        return p;
    }

    /**
     * Adds the specified product to the specified panel.
     *
     * @param theAuction The auction to add.
     * @param thePanel The panel to add the auction to.
     */
    private void addAuction(final Auction theAuction, final JPanel thePanel) {
        final JPanel sub = new JPanel(new FlowLayout(FlowLayout.LEFT));
        sub.add(new JLabel(theAuction.toString()));
        thePanel.add(sub);
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

        ActionButtons() {
            super();
            viewAuctions = new JButton("View Auctions");
            viewItems = new JButton("View Auction Items");
            bidOnItem = new JButton("Bid on an Item");
            goBack = new JButton("Go Back");
            setUp();
        }

        private void setUp() {
            viewAuctions.addActionListener(new ViewAuctionsListener());
            viewItems.addActionListener(new ViewItemsListener());
            goBack.addActionListener(new GoBackListener());

            add(viewAuctions);
            add(viewItems);
            add(bidOnItem);
            add(goBack);
            viewItems.setVisible(false);
            bidOnItem.setVisible(false);
            goBack.setVisible(false);
        }


        class ViewAuctionsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewAuctions.setVisible(false);
                viewItems.setVisible(true);
                goBack.setVisible(true);
                centerOfBidderPanel.add(itemsPanel);
            }
        }

        class ViewItemsListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                viewItems.setVisible(false);
                bidOnItem.setVisible(true);
            }
        }

        class GoBackListener implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                if(viewItems.isVisible()) { //auction list panel
                    viewItems.setVisible(false);
                    viewAuctions.setVisible(true);
                    goBack.setVisible(false);
                } else if(bidOnItem.isVisible()) { //item list panel
                    bidOnItem.setVisible(false);
                    viewItems.setVisible(true);
                }
            }
        }
    }
}
