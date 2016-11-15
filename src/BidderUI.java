import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;


/**
 * This is the Bidder UI.
 * @author Samantha Ong
 * @version 11/13/2016
 */
public final class BidderUI {
    /**The user for this ui*/
    private User myUser;

    /**Calendar for auctions.*/
    private Calendar myCalendar;

    /** Global scanner for user input. */
    private static final Scanner S = new Scanner(System.in);

    private List<Auction> myAuctionList;

    /**
     * The Bidder UI.
     * @param theUser the user
     * @param theCalendar the calendar
     */
    public BidderUI(User theUser, Calendar theCalendar) {
        myUser = theUser;
        myCalendar = theCalendar;
        myAuctionList = myCalendar.getAuctions(LocalDateTime.now());
    }


    /**
     * Displays a list of upcoming auctions.
     */
    private void displayAuctionList() {
        displayHeader();
        System.out.println("Below are the list of auctions:");
        for(int i = 1; i < myCalendar.getAuctions().size(); i++) {
            System.out.println(i + ". " + myAuctionList.get(i - 1).toString());
        }
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View an auction");
        System.out.println("2. View my bids");
        System.out.println("3. Go back");
        int menuChoice;
        do {
            menuChoice = getMenuChoice(3);
            if (menuChoice == 1) {
                displayChooseAuctionView();
            } else if (menuChoice == 2) {
                displayViewMyBids();
            }
        } while (menuChoice != 3);

    }

    /**
     * Displays the View to choose an auction.
     */
    private void displayChooseAuctionView() {
        System.out.println("Which auction would you like to View?");
        System.out.println("Enter 0 to go back.");
        int menuChoice;
        do {
            menuChoice = getMenuChoice(myAuctionList.size());
            if (menuChoice <= myAuctionList.size() && menuChoice > 0) {
                displayViewAuction(myAuctionList.get(menuChoice - 1));
            }
        } while (menuChoice != 0);
    }

    /**
     * Displays the view for viewing an auction.
     */
    private void displayViewAuction(Auction theAuction) {
        displayHeader();
        System.out.println(theAuction.toString());
        System.out.println("Items offered for sale:");
        System.out.format("%6s%25s%10d%16s","ID", "Item Name", "Condition", "Minimum Bid");
        for (AuctionItem item : theAuction.getItems()) {
            System.out.format("%8s%25s%12d%8s", item.getUniqueID(), item.getName(),
                    item.getCondition(), item.getMinBid());
        }
        System.out.println("What would you like to do?");
        System.out.println("1. View an item");
        System.out.println("2. Go back");
        int menuChoice;
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice == 1) {
                displayChooseItemView(theAuction.getItems());
            }
        } while (menuChoice != 2);

    }

    /**
     * Displays the view to choose an item.
     * @param theItemList the list of items from the auction.
     */
    private void displayChooseItemView(List<AuctionItem> theItemList) {
        System.out.println("Which item would you like to view?");
        System.out.println("Enter 0 to go back.");
        int menuChoice;
        do {
            menuChoice = getMenuChoice(theItemList.size());
            if (menuChoice <= theItemList.size() && menuChoice > 0) {
                displayViewAnItemView(theItemList.get(menuChoice-1));
            }
        } while (menuChoice != 0);
    }

    /**
     * Displays the view for bidding on an item.
     * @param theItem the item we are viewing.
     */
    private void displayViewAnItemView(AuctionItem theItem) {
        System.out.println(theItem.toString());
        if (theItem.getComment() != null) {
            System.out.println(theItem.getComment());
        }
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Place a bid on this item");
        System.out.println("2. Go back");
        int menuChoice;
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice <= 1) {
                displayBidOnItemView(theItem);
            }
        } while (menuChoice != 2);

    }

    /**
     * Displays the bid on item view.
     */
    private void displayBidOnItemView(AuctionItem theItem) {
        System.out.println(theItem.toString());
        System.out.println("This items minimum bid: " + theItem.getMinBid());
        System.out.println("\nPlease enter your bid: (Please enter bids with decimal value, ie. 5.00");

        Double input;
        do  {
            System.out.print("> ");
            while (!S.hasNextDouble()) {
                System.out.println("Invalid input, please enter valid bid with decimal value, ie 5.50");
                S.next();
            }
            input = S.nextDouble();
        } while(!S.hasNextDouble());
        theItem.addBid(myUser.getName(), input);

    }

    /**
     * Displays the users bids.
     */
    private void displayViewMyBids() {
        //TODO
        displayHeader();
    }

    /**
     * Prompts the user for what they would like to do.
     *  Login, Register, or Exit
     * @return the menu choice
     */
    private int getMenuChoice(int n) {
        int input;
        do {
            System.out.print("> ");
            while (!S.hasNextInt()) {
                System.out.println("Invalid input, please enter valid option numbers given above.");
                S.next();
            }
            input = S.nextInt();
        } while (!(input <= n));
        S.nextLine();
        return input;
    }

    /**
     * Displays the header of the UI.
     */
    private void displayHeader() {
        System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
        System.out.println(myUser.getName() + " logged in as Bidder\n");
        System.out.println(LocalDate.now().toString() + "\n");
    }

    private void start() {
        int menuChoice;
        displayHeader();
        System.out.println("What would you like to do?");
        System.out.println("1. View upcoming auctions");
        System.out.println("2. Log out");
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice == 1) {
                displayAuctionList();
            }
        } while (menuChoice != 2);
        S.close();
        System.out.println("\nLogging out...");
    }

}
