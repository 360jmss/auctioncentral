import java.io.*;
import java.time.LocalDateTime;

/**
 * This class is the starting point for the AuctionCentral program and is responsible for storing and saving the
 * serialized objects.
 * @author Simon DeMartini
 * @version Nov 7 2016
 */
public class AuctionCentral {

    /** The file location for the users repo when serialized."*/
    private static final File USERS_REPO_FILE = new File("./users.ser");

    /** The file location for the users repo when serialized."*/
    private static final File CALENDAR_FILE= new File("./cal.ser");

    /** A debugging mode where files are not saved if true. False by default */
    private static final boolean DEBUG_FILE_MODE = true;

    /** The Master UserRepo */
    private static UserRepo myUsers;

    /** The Master Calendar */
    private static Calendar myCalendar;

    /**
     * Start the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        myUsers = new UserRepo();
        myCalendar = new Calendar();

        System.out.println("Welcome to AuctionCentral");

        //read in serialized objects
        readCalAndUsers();

        //createTestData();

        //start the UI
        UIMain ui = new UIMain(myUsers, myCalendar);
        ui.start();

        //save serialized objects when UI is done
        if(!DEBUG_FILE_MODE) writeCalAndUsers();

    }

    /**
     * Read the UserRepo and Calendar files if they exist and assign to myUsers and myCalendar
     */
    private static void readCalAndUsers() {
        //read UserRepo
        if(USERS_REPO_FILE.isFile()) {
            try {
                myUsers = (UserRepo) readFile(USERS_REPO_FILE);
            } catch ( Exception e) {
                System.out.println("Yeah something went wrong reading a file. ");
                e.printStackTrace();
            }
        }
        //read Calendar
        if(CALENDAR_FILE.isFile()) {
            try {
                myCalendar = (Calendar) readFile(CALENDAR_FILE);
            } catch ( Exception e) {
                System.out.println("Yeah something went wrong reading a file. ");
                e.printStackTrace();
            }
        }
    }

    /**
     * Write the UserRepo and Calendar files if they exist and assign to myUsers and myCalendar
     */
    private static void writeCalAndUsers() {

        try {
            writeFile(myCalendar, CALENDAR_FILE);
        } catch ( Exception e) {
            System.out.println("Yeah something went wrong writing the file. ");
            CALENDAR_FILE.delete();
            e.printStackTrace();
        }

        try {
            writeFile(myUsers, USERS_REPO_FILE);
        } catch ( Exception e) {
            System.out.println("Yeah something went wrong writing the file. ");
            USERS_REPO_FILE.delete();
            e.printStackTrace();
        }
    }

    /**
     * Read in a serialized object from the disk
     * @param theFile the file to read
     * @return a generic Object
     */
    private static Object readFile(File theFile) {
        //https://www.tutorialspoint.com/java/java_serialization.htm
        Object obj;
        try {
            FileInputStream fileIn = new FileInputStream(theFile);
            ObjectInputStream in = new ObjectInputStream(fileIn);
            obj = in.readObject();
            in.close();
            fileIn.close();
        } catch(IOException e) {
            e.printStackTrace();
            return null;
        } catch(ClassNotFoundException e) {
            System.out.println("Class not found. Abort");
            e.printStackTrace();
            return null;
        }
        return obj;
    }

    /**
     * Write a serialized object to the disk
     * @param theObject to write
     * @param theFile to write to
     */
    private static void writeFile(Object theObject, File theFile) {
        //https://www.tutorialspoint.com/java/java_serialization.htm
        try {
            FileOutputStream fileOut = new FileOutputStream(theFile);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(theObject);
            out.close();
            fileOut.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Create a bunch of random test data
     */
    private static void createTestData() {
        // 2 staff
        myUsers.registerUser(new Staff("Jane Smith", "jsmith"));
        myUsers.registerUser(new Staff("Bobby Joe", "bj"));

        // 5 bidders
        myUsers.registerUser(new Bidder("Leonardo", "leo", "55", "Address", "leo@email.com"));
        myUsers.registerUser(new Bidder("Katy", "katy", "234234", "Somewhere", "katy@email.com"));
        myUsers.registerUser(new Bidder("Joey", "joey", "436523", "Here", "joey@email.com"));
        myUsers.registerUser(new Bidder("Monica", "monica", "1", "There", "monica@email.com"));
        myUsers.registerUser(new Bidder("Rachel", "rachel", "32409237849", "Nowhere", "rachel@email.com"));


        //22 future standard Auctions and contacts
        for(int i = 0; i < 22; i++) {
            Contact c = new Contact("John Doe the " + i,
                    "con" + i,
                    "253-54" + i,
                    "contact" + i + "@somewhere.edu",
                    i + " Main St",
                    "Big Ole Company #" + i);

            myUsers.registerUser(c);
            myCalendar.addAuction(new Auction(LocalDateTime.now().plusDays(i), c));
        }

        //2 Unique Auctions with items
        Contact bill = new Contact("Bill Gates",
                "billy",
                "111-111-1111",
                "bill@somewhere.edu",
                "123 Main St",
                "Bill Gates Foundation");
        myUsers.registerUser(bill);
        Auction billsAuction = new Auction(LocalDateTime.now().plusDays(11), bill);
        billsAuction.addItem(new AuctionItem("Apple", "Fresh", 1, 50.00));
        billsAuction.addItem(new AuctionItem("Orange", "Gross", 1, 4.73));
        billsAuction.addItem(new AuctionItem("Banana", "Acceptable", 1, 11.00));
        billsAuction.addItem(new AuctionItem("Grapes", "Pretty much just raisins", 1, 11.00));
        myCalendar.addAuction(billsAuction);

        Contact steve = new Contact("Steve Jobs",
                "steve",
                "222-222-2222",
                "steve@somewhere.edu",
                "123 Broadway St",
                "Apples Foundation");
        myUsers.registerUser(steve);
        Auction stevesAuction = new Auction(LocalDateTime.now().plusDays(14), steve);
        stevesAuction.addItem(new AuctionItem("Rock", "Fresh", 1, 10.00));
        stevesAuction.addItem(new AuctionItem("Twig", "Crunchy", 1, 50.73));
        stevesAuction.addItem(new AuctionItem("Boulder", "Large", 1, 11000.00));
        stevesAuction.addItem(new AuctionItem("A big river", "Moist", 1, 11000000.00));
        stevesAuction.addItem(new AuctionItem("Everything", "Big", 1, 78235982398740.00));

        myCalendar.addAuction(stevesAuction);


    }
}
