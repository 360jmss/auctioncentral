import java.time.LocalDate;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * This class is the sequence of screens the Contact user can see.
 *
 * @author Jessica Sills
 * @version November 14 2016
 */
public class ContactUI {

    /** Global scanner for user input.*/
    private static final Scanner sc = new Scanner(System.in);

    /** Current user who should be a contact.*/
    private Contact myUser;

    /** The contact's calendar.*/
    private Calendar myCalendar;

    /** The contact's upcoming auction.*/
    private Auction myAuction;

    /**
     * Constructs a ContactUI object.
     *
     * @param theUser The current user, a contact.
     * @param theCalendar The user's calendar.
     */
    public ContactUI(Contact theUser, Calendar theCalendar) {
        myUser = theUser;
        myCalendar = theCalendar;
        myAuction = myCalendar.getContactsAuction(myUser);
    }

    /**
     * Displays the header for the contact. It shows the current date and the date of an upcoming auction if they have
     * one.
     */
    private void displayHeader() {
        System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
        System.out.println();
        System.out.println(myUser.getName() + " logged in as a contact.");
        System.out.println();
        System.out.println("Today's date: " + LocalDate.now().toString());

        if (myAuction != null) {
            System.out.println(myAuction.toString());

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            System.out.println("Upcoming Auction date: " + myAuction.getStartTime().format(formatter));
        } else {
            System.out.println("You have no upcoming auctions yet!");
        }
        System.out.println();
    }

    /**
     * Double checks that the user wants to submit an auction request.
     */
    private void displayAuctionRequest() {
        System.out.println("You are about to submit an auction request.");
        System.out.println("If you want to go back to the main menu, enter '0'. Otherwise, enter '1'.");
        System.out.print("> ");
        String choice = sc.nextLine();
        System.out.println();

        if (choice.equals("0")) {
            System.out.println("Taking you back to the main menu.\n");
        } else if (choice.equals("1")) {
            enterAuctionRequest();
        }
    }

    /**
     * Asks for the date of the user's requested auction. Checks that it's valid.
     */
    private void enterAuctionRequest() {
        System.out.println("Please enter the start time of your auction (YYYY-MM-DD HH:mm): ");
        String date = sc.nextLine();
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
     * If the auction request is valid, let's the user know. Asks them if they want to add an estimated amount of
     * items and a comment for their auction.
     */
    private void successfulAuctionRequest() {
        System.out.println("Your auction was successfully added to the calendar!");
        System.out.println("Would you like to enter the estimated number of items for your auction? Y or N:");
        System.out.print("> ");
        String answer = sc.nextLine();

        if (answer.equals("Y")) {
            enterEstItems();
        }

        System.out.println("Would you like to enter a comment for your auction? Y or N:");
        answer = sc.nextLine();

        if (answer.equals("Y")) {
            enterComment();
        }

        System.out.println("Your upcoming auction is: " + myAuction.toString());
        System.out.println("You are now being returned to the main menu.\n");
        myCalendar.addAuction(myAuction);
    }

    /**
     * If the auction request was invalid, it let's the user know why.
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
        System.out.println("or try again later.\n");
    }

    /**
     * Let's the user enter an estimated number of items for their auction.
     */
    private void enterEstItems() {
        System.out.println("Please enter the estimated number of items in your auction: ");
        int number = sc.nextInt();
        sc.nextLine();
        myAuction.setEstItems(number);
    }

    /**
     * Let's the user enter a comment for their auction.
     */
    private void enterComment() {
        System.out.println("Please enter a comment for your auction: ");
        String comment = sc.nextLine();
        myAuction.setComment(comment);
    }

    /**
     * Double checks that the user wants to add an item to their auction.
     */
    private void displayAddItems() {
        if (myAuction == null) {
            System.out.println("You do not have an auction to add items to. You are being returned to the main menu.");
            System.out.println();
        } else {
            System.out.println("You are about to add an item to your auction.");
            System.out.println("If you want to go back to the main menu, enter '0'. Otherwise, enter '1'.");
            System.out.print("> ");
            String choice = sc.nextLine();
            System.out.println();

            if (choice.equals("0")) {
                System.out.println("Taking you back to the main menu.\n");
            } else if (choice.equals("1")) {
                enterAnItem();
            }
        }
    }

    /**
     * Asks for the name, condition, size, and minimum bid of their item.
     */
    private void enterAnItem() {
        System.out.println("Please enter the name of your item: ");
        String name = sc.nextLine();

        System.out.println("Please enter the condition of your item: ");
        String condition = sc.nextLine();

        System.out.println("Please enter the size of your item (as an integer): ");
        int size = sc.nextInt();
        sc.nextLine();

        System.out.println("Please enter the minimum bid of your item: ");
        double minBid = sc.nextDouble();
        sc.nextLine();

        AuctionItem newItem = new AuctionItem(name, condition, size, minBid);

        if (myAuction.validateItem(newItem)) {
            successfulItemAdd(newItem);
        } else {
            failedItemAdd();
        }
    }

    /**
     * If their item add was valid, let's the user add a donor name, description, and/or comment to their auction.
     *
     * @param theItem The item the contact added.
     */
    private void successfulItemAdd(AuctionItem theItem) {
        System.out.println("Your item was successfully added to your auction!");
        System.out.println("Would you like to enter the the name of the donor of this item? Y or N:");
        System.out.print("> ");
        String answer = sc.nextLine();

        if (answer.equals("Y")) {
            System.out.println("Please enter a donor name for your item: ");
            String donor = sc.nextLine();
            theItem.setDonorName(donor);
        }

        System.out.println("Would you like to enter a description for this item? Y or N:");
        answer = sc.nextLine();

        if (answer.equals("Y")) {
            System.out.println("Please enter a description for your item: ");
            String description = sc.nextLine();
            theItem.setDescription(description);
        }

        System.out.println("Would you like to enter a comment for this item? Y or N:");
        answer = sc.nextLine();

        if (answer.equals("Y")) {
            System.out.println("Please enter a comment for your item: ");
            String comment = sc.nextLine();
            theItem.setComment(comment);
        }

        System.out.println("Item: " + theItem.toString() + " has been added to your auction.");

        myAuction.addItem(theItem);

        System.out.println("You are now being returned to the main menu.\n");
    }

    /**
     * If their item add was invalid, let's the user know why.
     */
    private void failedItemAdd() {
        System.out.println("Your item was NOT successfully added to your auction.");
        System.out.println("This is because the name of the item matched the name of another item that is already");
        System.out.println("in the auction item list.");
        System.out.println("You are being returned to the main menu.\n");
    }

    /**
     * Prints the main menu choices.
     *  If this list is updated, the EXIT constant needs to be updated
     *  as well as the getMenuChoice() method's validation.
     */
    private void printMenuChoices() {
        System.out.println("What would you like to do? (Enter an Integer)");
        System.out.println("1. Submit an auction request");
        System.out.println("2. Add items to your auction");
        System.out.println("3. Log out");
    }

    /**
     * Prompts the user for what they would like to do.
     *  Login, Register, or Exit
     * @return the menu choice
     */
    private int getMenuChoice() {
        int input;
        displayHeader();
        printMenuChoices();
        do {
            System.out.print("> ");
            while (!sc.hasNextInt()) {
                System.out.println("Invalid input, please enter only '1','2' or '3'");
                sc.next();
            }
            input = sc.nextInt();
        } while (!(input == 1 || input == 2 || input == 3));
        sc.nextLine();
        return input;
    }

    /**
     * Starts the UI.
     */
    public void start() {
        int menuChoice;
        System.out.println("Hello! Welcome to Auction Central! \n");
        do {
            menuChoice = getMenuChoice();
            if (menuChoice == 1) {
                displayAuctionRequest();
            }
            else if (menuChoice == 2 ) {
                displayAddItems();
            }
        } while (menuChoice != 3);
        System.out.println("\nLogging out. Thank you for using Auction Central.");
    }
}
