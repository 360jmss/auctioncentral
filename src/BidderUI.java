import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


/**
 * This is the Bidder UI.
 * @author Samantha Ong
 * @version 11/13/2016
 */
public final class BidderUI {

    private User myUser;

    private Calendar myCalendar;

    /**
     * The Bidder UI.
     * @param theUser the user
     * @param theCalendar the calendar
     */
    public BidderUI(User theUser, Calendar theCalendar) {
        myUser = theUser;
        myCalendar = theCalendar;
        displayMenu();
    }

    /**
     * Displays the main menu.
     */
    private void displayMenu() {
        displayHeader();
        System.out.println("What would you like to do?");
        System.out.println("1. View upcoming auctions");
        System.out.println("2. Log out");
    }

    /**
     * Displays a list of upcoming auctions.
     */
    private void displayAuctionList() {
        displayHeader();
        System.out.println("Below are the list of auctions:");
        List<Auction> auctionList = myCalendar.getAuctions(LocalDateTime.now());
        for(int i = 1; i < myCalendar.getAuctions().size(); i++) {
            System.out.println(i + ". " + auctionList.get(i - 1).toString());
        }
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View an auction");
        System.out.println("2. View my bids");
        System.out.println("3. Go back");
    }

    /**
     * Displays the view for viewing an auction.
     */
    private void displayBidOnAuction(Auction theAuction) {
        //TODO
        displayHeader();
        System.out.println(theAuction.toString());
        System.out.println("Items offered for sale:");
        System.out.format("%6s%25s%10d%16s","ID", "Item Name", "Condition", "Minimum Bid");
        for (AuctionItem item : theAuction.getItems()) {
            System.out.format("%8s%25s%12d%8s", item.getUniqueID(), item.getName(),
                    item.getCondition(), item.getMinBid());
        }
        System.out.println("What would you like to do?");
        System.out.println("1. Bid on an item");
        System.out.println("2. Go back");
        System.out.println("3. Exit AuctionCentral");
    }

    /**
     * Displays the view for bidding on an item.
     * @param theItem
     */
    private void displayBidOnAnItem(AuctionItem theItem) {

    }

    /**
     * Displays the users bids.
     */
    private void displayViewMyBids() {
        //TODO
        displayHeader();
    }

    /**
     * Displays the header of the UI.
     */
    private void displayHeader() {
        System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
        System.out.println(myUser.getName() + " logged in as Bidder\n");
        System.out.println(LocalDate.now().toString() + "\n");
    }

}
