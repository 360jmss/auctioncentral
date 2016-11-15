package console_ui;

import model.Auction;
import model.AuctionItem;
import model.Calendar;
import model.User;

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
    /** The user for this ui*/
    private User myUser;

    /** Calendar for auctions.*/
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
     * Prints the view for Auction List view.
     */
    private void printAuctionListView() {
        displayHeader();
        System.out.println("Below are the list of auctions:");
        for(int i = 1; i < myCalendar.getAuctions().size(); i++) {
            System.out.println(i + ". " + myAuctionList.get(i - 1).toString());
        }

    }

    private void printAuctionListMenu() {
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. View an auction");
        System.out.println("2. Go back");
    }
    /**
     * Displays a list of upcoming auctions.
     */
    private void displayAuctionList() {
        printAuctionListView();
        printAuctionListMenu();
        int menuChoice;
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice == 1) {
                displayChooseAuctionView();
                printAuctionListView();
                printAuctionListMenu();
            }
        } while (menuChoice != 2);
    }

    /**
     * print the choose auction view.
     */
    private void printChooseAuctionView() {
        printAuctionListView();
        System.out.println("\nWhich auction would you like to View?");
        System.out.println("Enter 0 to go back.");
    }

    /**
     * Displays the View to choose an auction.
     */
    private void displayChooseAuctionView() {
        printChooseAuctionView();
        int menuChoice;
        menuChoice = getMenuChoice(myAuctionList.size());
        if (menuChoice <= myAuctionList.size() && menuChoice > 0) {
            displayViewAuction(myAuctionList.get(menuChoice - 1));
            printChooseAuctionView();
        }
    }

    /**
     * prints the auction view.
     * @param theAuction the auction we are viewing.
     */
    private void printViewAuction(Auction theAuction) {
        displayHeader();
        System.out.println(theAuction.toString());
        System.out.println("Items offered for sale:");
        System.out.format("%9s%25s%12s%20s%20s\n","ID", "Item Name", "Condition", "Min Bid", "My Bid");
        int i = 1;
        for (AuctionItem item : theAuction.getItems()) {
            System.out.format("%2d. %5d%25s%12s%20.2f%20.2f\n", i, item.getUniqueID(), item.getName(),
                    item.getCondition(), item.getMinBid(), item.getBid(myUser.getName()));
            i++;

        }
        System.out.println("\n\nWhat would you like to do?");
        System.out.println("1. View an item");
        System.out.println("2. Go back");
    }
    /**
     * Displays the view for viewing an auction.
     * @param theAuction the auction we are viewing.
     */
    private void displayViewAuction(Auction theAuction) {
        printViewAuction(theAuction);

        int menuChoice;
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice == 1) {
                displayChooseItemView(theAuction.getItems());
                printViewAuction(theAuction);
            }
        } while (menuChoice != 2);

    }

    /**
     * Displays the view to choose an item.
     * @param theItemList the list of items from the auction.
     */
    private void displayChooseItemView(List<AuctionItem> theItemList) {
        displayHeader();
        System.out.println("Which item would you like to view?");
        int menuChoice;
        menuChoice = getMenuChoice(theItemList.size());
        if (menuChoice <= theItemList.size() && menuChoice > 0) {
            displayViewAnItemView(theItemList.get(menuChoice-1));
        }

    }

    /**
     * prints the view an item view.
     * @param theItem the item we are viewing.
     */
    private void printViewAnItemView(AuctionItem theItem) {
        displayHeader();
        System.out.format("%5s%25s%12s%20s%20s\n","ID", "Item Name", "Condition", "Min Bid", "My Bid");
        System.out.format("%5d%25s%12s%20.2f%20.2f\n", theItem.getUniqueID(), theItem.getName(),
                theItem.getCondition(), theItem.getMinBid(), theItem.getBid(myUser.getName()));
        if (theItem.getComment() != null) {
            System.out.println(theItem.getComment());
        }
        System.out.println("\nWhat would you like to do?");
        System.out.println("1. Place a bid on this item");
        System.out.println("2. Go back");
    }

    /**
     * Displays the view for bidding on an item.
     * @param theItem the item we are viewing.
     */
    private void displayViewAnItemView(AuctionItem theItem) {
        printViewAnItemView(theItem);
        int menuChoice;
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice <= 1) {
                displayBidOnItemView(theItem);
                printViewAnItemView(theItem);
            }
        } while (menuChoice != 2);

    }

    /**
     * prints the bid on item view.
     * @param theItem the item we are bidding on.
     */
    private void printBidOnItemView(AuctionItem theItem) {
        displayHeader();
        System.out.format("%9s%25s%12s%20s%20s\n","ID", "Item Name", "Condition", "Min Bid", "My Bid");
        System.out.format("%5d%25s%12s%20.2f%20.2f\n", theItem.getUniqueID(), theItem.getName(),
                theItem.getCondition(), theItem.getMinBid(), theItem.getBid(myUser.getName()));
        System.out.println("This items minimum bid: " + theItem.getMinBid());
        System.out.println("\nPlease enter your bid: ");

    }

    /**
     * Displays the bid on item view.
     *  @param theItem the item we are bidding on.
     */
    private void displayBidOnItemView(AuctionItem theItem) {
        printBidOnItemView(theItem);
        Double input;
        do  {
            System.out.print("> ");
            while (!S.hasNextDouble()) {
                System.out.println("Invalid input, please enter valid bid ie. 99, 12.0, 12.5");
                S.next();
            }
            input = S.nextDouble();
        } while(input == null);
        theItem.addBid(myUser.getName(), input);

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
        System.out.println(myUser.getName() + " logged in as model.Bidder");
        System.out.println("Todays Date: " + LocalDate.now().toString() + "\n");
    }

    /**
     * Displays the start menu.
     */
    private void displayStartMenu() {
        displayHeader();
        System.out.println("What would you like to do?");
        System.out.println("1. View upcoming auctions");
        System.out.println("2. Log out");
    }

    public void start() {
        int menuChoice;
        displayStartMenu();
        do {
            menuChoice = getMenuChoice(2);
            if (menuChoice == 1) {
                displayAuctionList();
                displayStartMenu();
            }
        } while (menuChoice != 2);
        System.out.println("\nLogging out...");
    }

}
