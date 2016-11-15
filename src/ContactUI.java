/**
 * Created by Jessica on 11/13/2016.
 */

import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ContactUI {

    /**
     *
     */
    private static final Scanner sc = new Scanner(System.in);

    /**
     *
     */
    private Contact myUser;

    /**
     *
     */
    private Calendar myCalendar;

    /**
     *
     */
    private Auction myAuction;

    /**
     *
     * @param theUser
     * @param theCalendar
     */
    public ContactUI(Contact theUser, Calendar theCalendar) {
        myUser = theUser;
        myCalendar = theCalendar;
        myAuction = myCalendar.getContactsAuction(myUser);
        displayMenu();
    }

    /**
     *
     */
    private void displayMenu() {
        displayHeader();
        System.out.println("What would you like to do?");
        System.out.println("1. Submit an auction request");
        System.out.println("2. Add items to your auction");
        System.out.println("3. Exit Auction Central");
        System.out.println();
        System.out.print("> ");
        String choice = sc.next();
        System.out.println();

        if (choice == "1") {
            displayAuctionRequest();
        } else if (choice == "2") {
            displayAddItems();
        }
        sc.close();
    }

    /**
     *
     */
    private void displayHeader() {
        System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
        System.out.println(myUser.getName() + " logged in as a contact.");
        System.out.println();
        System.out.println("Today's date: " + LocalDate.now().toString());

        if (myAuction != null) {
            System.out.println("Upcoming Auction date: " + myAuction.getStartTime().toString());
        }
    }

    /**
     *
     */
    private void displayAuctionRequest() {
        displayHeader();
        System.out.println("You are about to submit an auction request.");
        System.out.println("If you want to go back to the main menu, enter '0'. Otherwise, enter '1'.");
        System.out.print("> ");
        String choice = sc.next();
        System.out.println();

        if (choice == "0") {
            displayMenu();
        } else if (choice == "1") {
            enterAuctionRequest();
        }
    }

    /**
     *
     */
    private void enterAuctionRequest() {
        System.out.println("Please enter the start time of your auction (YYYY-MM-DD HH:mm): ");
        String date = sc.nextLine();
        System.out.println();
        System.out.println();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(date, formatter);

        Auction newAuction = new Auction(dateTime, myUser);

        if (myCalendar.validateAuction(newAuction)) {
            myAuction = newAuction;
            successfulAuctionRequest();
        } else {
            failedAuctionRequest();
        }
    }

    /**
     *
     */
    private void successfulAuctionRequest() {
        System.out.println("Your auction was successfully added to the calendar!");
        System.out.println("Would you like to enter the estimated number of items for your auction? Y or N:");
        System.out.print("> ");
        String answer = sc.next();
        System.out.println();

        if (answer == "Y") {
            enterEstItems();
        }

        System.out.println();
        System.out.println("Would you like to enter a comment for your auction? Y or N:");
        answer = sc.next();
        System.out.println();

        if (answer == "Y") {
            enterComment();
        }

        System.out.println();
        System.out.println("You are now being returned to the main menu.");
        myCalendar.addAuction(myAuction);
        displayMenu();
    }

    /**
     *
     */
    private void failedAuctionRequest() {
        System.out.println("Your auction was NOT successfully added to the calendar.");
        System.out.println("One of three things happened:");
        System.out.println("1. You already had an auction within the past year.");
        System.out.println("2. You already have an auction scheduled sometime from today's date.");
        System.out.println("3. Auction Central has the max number of auctions scheduled within the date you ");
        System.out.println("    chose, the week you chose, or within the next three months.");
        System.out.println();
        System.out.println("You are being returned to the main menu. Please try changing the date of your auction");
        System.out.println("or try again later.");
        displayMenu();
    }

    /**
     *
     */
    private void enterEstItems() {
        System.out.println("Please enter the estimated number of items in your auction: ");
        int number = sc.nextInt();
        myAuction.setEstItems(number);
    }

    /**
     *
     */
    private void enterComment() {
        System.out.println("Please enter a comment for your auction: ");
        String comment = sc.nextLine();
        myAuction.setComment(comment);
    }

    /**
     *
     */
    private void displayAddItems() {
        displayHeader();

        if (myAuction == null) {
            System.out.println("You do not have an auction to add items to. You are being returned to the main menu.");
            System.out.println();
            displayMenu();
        }

        System.out.println("You are about to add an item to your auction.");
        System.out.println("If you want to go back to the main menu, enter '0'. Otherwise, enter '1'.");
        System.out.print("> ");
        String choice = sc.next();
        System.out.println();

        if (choice == "0") {
            displayMenu();
        } else if (choice == "1") {
            enterAnItem();
        }
    }

    /**
     *
     */
    private void enterAnItem() {
        System.out.println("Please enter the name of your item: ");
        String name = sc.nextLine();
        System.out.println();

        System.out.println("Please enter the condition of your item: ");
        String condition = sc.nextLine();
        System.out.println();

        System.out.println("Please enter the size of your item: ");
        int size = sc.nextInt();
        System.out.println();

        System.out.println("Please enter the minimum bid of your item: ");
        double minBid = sc.nextDouble();
        System.out.println();

        AuctionItem newItem = new AuctionItem(name, condition, size, minBid);

        if (myAuction.validateItem(newItem)) {
            successfulItemAdd(newItem);
        } else {
            failedItemAdd();
        }
    }

    /**
     * donor name description comment
     */

    private void successfulItemAdd(AuctionItem theItem) {
        System.out.println("Your item was successfully added to your auction!");
        System.out.println("Would you like to enter the the name of the donor of this item? Y or N:");
        System.out.print("> ");
        String answer = sc.next();
        System.out.println();

        if (answer == "Y") {

        }

        System.out.println();
        System.out.println("Would you like to enter a description for this item? Y or N:");
        answer = sc.next();
        System.out.println();

        if (answer == "Y") {

        }

        System.out.println();
        System.out.println("Would you like to enter a comment for this item? Y or N:");
        answer = sc.next();
        System.out.println();

        if (answer == "Y") {

        }

        System.out.println();
        System.out.println("You are now being returned to the main menu.");

        displayMenu();
    }

    private void failedItemAdd() {
        displayMenu();
    }
}
