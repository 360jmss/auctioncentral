import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.List;
import java.util.Scanner;

/**
 * This class represents the Staff view for the console UI.
 * @author Miguel Barreto
 * @version 14 Nov, 2016
 */
public class StaffUI {

    /** Global scanner for user input. */
    private static final Scanner S = new Scanner(System.in);

    /** The current user who is logged in */
    private User myUser;

    /** The master calendar for AuctionCentral */
    private Calendar myCalendar;

    /** Menu option for logging out and exiting Staff UI */
    private static final int EXIT = 3;

    /**
     * The Staff UI.
     * @param theUser The user who is logged in
     * @param theCalendar The calendar for the loaded list of all auctions
     */
    StaffUI(User theUser, Calendar theCalendar) {
        myUser = theUser;
        myCalendar = theCalendar;
    }

    /**
     * Prompts the user for what they would like to do.
     *  View Calendar, Admin Functions, or Exit
     * @return the menu choice
     */
    private int getMenuChoice() {
        int input;
        displayHeader();
        printMenuChoices();
        do {
            System.out.print("> ");
            while (!S.hasNextInt()) {
                System.out.println("Invalid input, please enter only '1','2' or '3'");
                S.next();
            }
            input = S.nextInt();
        } while (!(input == 1 || input == 2 || input == 3));
        //S.nextLine();
        return input;
    }

    /**
     * Prints the Staff menu choices.
     *  If this list is updated, the EXIT constant needs to be updated
     *  as well as the getMenuChoice() method's validation.
     */
    private void printMenuChoices() {
        System.out.println("What would you like to do? (Enter an Integer)");
        System.out.println("1. View calendar of upcoming auctions");
        System.out.println("2. Administrative functions");
        System.out.println("3. Exit AuctionCentral\n");
    }

    /**
     * Displays the header of the UI.
     */
    private void displayHeader() {
        System.out.println("AuctionCentral: the auctioneer for non-profit organizations.");
        System.out.println(myUser.getName() + " logged in as Auction Central Staff Person\n");
        System.out.println(LocalDate.now().toString() + "  Total number of upcoming auctions: "
                + myCalendar.getFutureAuctionTotal() + "\n");
    }

    /**
     * Allows the staff member to view the Calendar.
     */
    private void menuViewCalendar() {
        LocalDateTime startTime = LocalDateTime.now().plusDays(1).withHour(0).withMinute(0);
        LocalDate startDate = startTime.toLocalDate();
        LocalDate endDate = startDate.plusMonths(1);
        int dayOfWeek = alignCalendar(startDate);
        //LocalDate startOfWeek = startDate.minusDays(dayOfWeek);
        //List<Auction> auctionsOneMonth = myCalendar.getAuctionsOneMonth(startTime);
        int currMonth = 0;
        displayHeader();
        System.out.println("Su\tMo\tTu\tWe\tTh\tFr\tSa");
        while(startDate != endDate) {
            if(currMonth != startDate.getMonthValue()) {
                System.out.println("\t\t[" + startDate.getMonth().toString() + "]");
                dayOfWeek = alignCalendar(startDate);
                currMonth = startDate.getMonthValue();
                for (int i = 0; i < dayOfWeek; i++)
                    System.out.print("|\t");
            }
            System.out.print("| " + startDate.getDayOfMonth() + ":"
                    + myCalendar.getAuctionTotalOnDay(startDate) + "\t");
            startDate.plusDays(1);
            dayOfWeek++;
            if(dayOfWeek > 7){
                dayOfWeek = 0;
                System.out.print("|\n");
            }
        }
    }

    /**
     * Lets the calendar know how many days from
     *  the start of the week we are off by.
     * @return offset from Sunday
     */
    private int alignCalendar(LocalDate theDate) {
        if(theDate.getDayOfWeek() == DayOfWeek.MONDAY)
            return 1;
        else if(theDate.getDayOfWeek() == DayOfWeek.TUESDAY)
            return 2;
        else if(theDate.getDayOfWeek() == DayOfWeek.WEDNESDAY)
            return 3;
        else if(theDate.getDayOfWeek() == DayOfWeek.THURSDAY)
            return 4;
        else if(theDate.getDayOfWeek() == DayOfWeek.FRIDAY)
            return 5;
        else if(theDate.getDayOfWeek() == DayOfWeek.SATURDAY)
            return 6;
        else
            return 0;
    }

    /**
     * Allows the staff member to view Administrative options.
     */
    private void menuAdmin() {
        System.out.println("\nAdministrative functions currently unavailable. Returning...\n");
    }

    /**
     * Main entry point for the Staff UI.
     */
    public void start() {
        int menuChoice;
        do {
            menuChoice = getMenuChoice();
            if (menuChoice == 1) {
                menuViewCalendar();
            }
            else if (menuChoice == 2 ) {
                menuAdmin();
            }
        } while (menuChoice != EXIT);
        //S.close();
        System.out.println("\nLogging out...\n");
    }


}
