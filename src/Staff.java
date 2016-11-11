/**
 * Created by Jessica on 11/10/2016.
 */
public class Staff extends User {

    /**
     * Constructs a Staff object. The staff must have a name and username associated with it.
     *
     * @param theName The name of the user.
     * @param theUsername The username of the user.
     */
    public Staff(String theName, String theUsername) {
        myName = theName;
        myUsername = theUsername;
    }

    /**
     * Retrieves the name of a user.
     *
     * @return The name of a user.
     */
    public String getName() {
        return myName;
    }

    /**
     * Retrieves the username of a user
     *
     * @return The username of a user.
     */
    public String getUsername() {
        return myUsername;
    }
}
