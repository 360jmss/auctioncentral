import java.io.*;
import java.time.LocalDateTime;

/**
 * This class is the starting point for the AuctionCentral program and is responsible for storing and saving the
 * serialized objects.
 * @author Simon DeMartini
 * @version Nov 7 2016
 */
public class AuctionCentral implements Serializable {

    /** The file location for the users repo when serialized."*/
    private static final File USERS_REPO_FILE = new File("./users.ser");

    /** The file location for the users repo when serialized."*/
    private static final File CALENDAR_FILE= new File("./cal.ser");

    /** The Master UserRepo */
    private static UserRepo myUsers;

    /** The Master Calendar */
    private static Calendar myCalendar;

    /**
     * Constructor for AuctionCentral.
     */
    public AuctionCentral() {
        myUsers = new UserRepo();
        myCalendar = new Calendar();
    }

    /**
     * Start the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        //TODO Start up
        System.out.println("Welcome to AuctionCentral");

        //read in serialized objects
        readCalAndUsers();

        //start the UI
        Contact jd = new Contact("John Doe",
                "johndoe4",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");
        Auction futureJD1 = new Auction(LocalDateTime.now().plusWeeks(2), jd);
        myCalendar.addAuction(futureJD1);
        myUsers.registerUser(jd);

        //save serialized objects when UI is done
        writeCalAndUsers();

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
        } else {
            myUsers = new UserRepo();
        }
        //read Calendar
        if(CALENDAR_FILE.isFile()) {
            try {
                myCalendar = (Calendar) readFile(CALENDAR_FILE);
            } catch ( Exception e) {
                System.out.println("Yeah something went wrong reading a file. ");
                e.printStackTrace();
            }
        } else {
            myCalendar = new Calendar();
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
}
