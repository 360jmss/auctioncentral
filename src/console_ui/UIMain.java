package console_ui;

import model.*;

import java.util.Scanner;

/**
 * This class represents the main view for the console UI.
 * The starting point for the UI
 * @author Miguel Barreto
 * @version 13 Nov, 2016
 */
public class UIMain {

    /** Global scanner for user input. */
    private static Scanner S = new Scanner(System.in);

    /** The master list of all users that can log in. */
    private UserRepo myRepo;

    /** The master calendar for main.AuctionCentral. */
    private Calendar myCalendar;

    /** Menu option for exiting the UI */
    private static final int EXIT = 3;

    /**
     * The constructor for the console_ui.UIMain
     * @param theRepo The repo for the loaded map of all users
     * @param theCalendar The calendar for the loaded list of all auctions
     */
    public UIMain(UserRepo theRepo, Calendar theCalendar) {
        myRepo = theRepo;
        myCalendar = theCalendar;
    }

    /**
     * Prompts the user for what they would like to do.
     *  Login, Register, or Exit
     * @return the menu choice
     */
    private int getMenuChoice() {
        int input;
        System.out.println("main.AuctionCentral: the auctioneer for non-profit organizations.\n");
        printMenuChoices();
        do {
            System.out.print("> ");
            while (!S.hasNextInt()) {
                System.out.println("Invalid input, please enter only '1','2' or '3'");
                S.next();
            }
            input = S.nextInt();
        } while (!(input == 1 || input == 2 || input == 3));
        S.nextLine();
        return input;
    }

    /**
     * Prints the main menu choices.
     *  If this list is updated, the EXIT constant needs to be updated
     *  as well as the getMenuChoice() method's validation.
     */
    private void printMenuChoices() {
        System.out.println("What would you like to do? (Enter an Integer)");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Exit main.AuctionCentral\n");
    }

    /**
     * Prints the registration user choices.
     */
    private void printRegisterChoices() {
        System.out.println("What type of user are you?");
        System.out.println("1. model.Bidder");
        System.out.println("2. Non-Profit model.Contact");
        System.out.println("3. main.AuctionCentral model.Staff");
        System.out.println("4. Go Back");
    }

    /**
     * Prompts the user for their unique username
     *  to log them in, then calls the respective
     *  sub-user UI for that user or returns them
     *  to the main menu if they fail to log in.
     */
    private void menuLogin() {
        User loginUser;
        String username;
        System.out.println("\nmodel.Auction Central - Login");
        System.out.print("\tUsername: ");
        username = S.nextLine();
        //System.out.println("\tPassword:");
        //password = S.next();
        loginUser = myRepo.loginUser(username);
        if(loginUser != null) {
            System.out.println("Login Successful. Welcome " + loginUser.getName() + "!\n");
            if(loginUser instanceof Bidder) {
                BidderUI ui = new BidderUI(loginUser, myCalendar);
                ui.start();
            }
            else if(loginUser instanceof Contact) {
                ContactUI ui = new ContactUI((Contact) loginUser, myCalendar);
                ui.start();
            }
            else if(loginUser instanceof Staff) {
                StaffUI ui = new StaffUI(loginUser, myCalendar);
                ui.start();
            }
            else {
                System.out.println("There seems to be an error. Returning...");
            }
        }
        else {
            System.out.println("Login failed. Returning... ");
        }
    }

    /**
     * Prompts the user for what type of user they are,
     *  then calls the appropriate sub-user register
     *  method, then registers them if possible.
     */
    private void menuRegister() {
        User newUser;
        int input;
        System.out.println("\nmodel.Auction Central - Register");
        printRegisterChoices();
        do {
            System.out.print("> ");
            while (!S.hasNextInt()) {
                System.out.println("Invalid input, please enter only '1', '2', '3', or '4'");
                S.next();
            }
            input = S.nextInt();
        } while (!(input == 1 || input == 2 || input == 3 || input == 4));
        S.nextLine();
        if (input == 1) {
            System.out.println("\nRegister as model.Bidder...");
            newUser = menuRegisterBidder();
        }
        else if (input == 2 ) {
            System.out.println("\nRegister as model.Contact...");
            newUser = menuRegisterContact();
        }
        else if (input == 3 ) {
            System.out.println("\nRegister as model.Staff...");
            newUser = menuRegisterStaff();
        }
        else
            return;
        if(myRepo.registerUser(newUser)) {
            System.out.println("Registration successful. Returning...");
        }
        else {
            System.out.println("Registration failed. Returning... ");
        }
    }

    /**
     * Prompts the user to fill in the required field
     *  for registering as a model.Bidder.
     * @return the to-be registered model.User
     */
    private User menuRegisterBidder() {
        Bidder newBidder;
        String name, username, phoneNumber, address, email;
        System.out.println("\tEnter your Full Name: ");
        name = S.nextLine();
        System.out.println("\tEnter your Username: ");
        username = S.nextLine();
        System.out.println("\tEnter your Phone Number: ");
        phoneNumber = S.nextLine();
        System.out.println("\tEnter your Address: ");
        address = S.nextLine();
        System.out.println("\tEnter your Email: ");
        email = S.nextLine();
        newBidder = new Bidder(name,username,phoneNumber,address,email);
        return newBidder;
    }

    /**
     * Prompts the user to fill in the required field
     *  for registering as a model.Contact.
     * @return the to-be registered model.User
     */
    private User menuRegisterContact() {
        Contact newContact;
        String name, username, phoneNumber, email, address, organization;
        System.out.println("\tEnter your Full Name: ");
        name = S.nextLine();
        System.out.println("\tEnter your Username: ");
        username = S.nextLine();
        System.out.println("\tEnter your Business' Name: ");
        organization = S.nextLine();
        System.out.println("\tEnter your Business' Phone Number: ");
        phoneNumber = S.nextLine();
        System.out.println("\tEnter your Business' Email: ");
        email = S.nextLine();
        System.out.println("\tEnter your Business' Address: ");
        address = S.nextLine();
        newContact = new Contact(name,username,phoneNumber,email,address,organization);
        return newContact;
    }

    /**
     * Prompts the user to fill in the required field
     *  for registering as a model.Staff.
     * @return the to-be registered model.User
     */
    private User menuRegisterStaff() {
        Staff newStaff;
        String name, username;
        System.out.println("\tEnter your Full Name: ");
        name = S.nextLine();
        System.out.println("\tEnter your Username: ");
        username = S.nextLine();
        newStaff = new Staff(name,username);
        return newStaff;
    }

    /**
     * Main UI for model.Auction Central. Entry point for all the UI.
     */
    public void start() {
        int menuChoice;
        System.out.println("Hello! Welcome to model.Auction Central! \n");
        do {
            menuChoice = getMenuChoice();
            if (menuChoice == 1) {
                menuLogin();
            }
            else if (menuChoice == 2 ) {
                menuRegister();
            }
        } while (menuChoice != EXIT);
        S.close();
        System.out.println("\nExiting program. Thank you for using model.Auction Central.");
    }

}
