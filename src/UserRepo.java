import java.io.Serializable;
import java.util.HashMap;

/**
 * This class manages the master list of all users in the system.
 * @author Simon DeMartini
 * @version Nov 13 2016
 */
public class UserRepo implements Serializable {

    /** The master list of all users that can log in.*/
    private HashMap<String, User> myUsers;

    /**
     * The constructor for a new UserRepo
     */
    public UserRepo() {
        myUsers = new HashMap<>();
    }

    /**
     * Login in as a user based on their username
     *
     * @param theUsername the user's username
     * @return the valid User, null if the user does not exist
     */
    public User loginUser(String theUsername) {
        return myUsers.get(theUsername);
    }

    /**
     * Register a new user. If the username already exists, return false
     * @param theUser a valid and complete user
     * @return whether the user was able to be registered or not
     *
     */
    public boolean registerUser(User theUser) {
        //check if it exists already
        if(myUsers.containsKey(theUser.getUsername())) return false;
        //add it to the list
        myUsers.put(theUser.getUsername(), theUser);
        return true;
    }
}
