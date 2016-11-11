import java.util.Set;

/**
 * This class is the starting point for the AuctionCentral program.
 * @author Simon DeMartini
 * @version Nov 7 2016
 */
public class AuctionCentral {

    /** The master list of all users that can log in.*/
    private Set<User> myUsers;

    /** The master calendar */
    private Calendar myCalendar;

    /**
     * Start the program.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        System.out.println("Welcome to AuctionCentral");
    }

    /**
     * Login in as a user based on their username
     *
     * @param username the user's username
     * @return the valid User
     */
    public User login(String username) {
        return null;
    }
    
}
