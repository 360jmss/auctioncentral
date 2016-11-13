/**
 * Staff holds information necessary for a staff member of Auction Central. They need a name and username.
 *
 * @author Jessica Sills
 * @version Nov 12 2016
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

    /**
     * Sets the name of a user.
     *
     * @param theName The name of a user
     */
    public void setName(String theName) { myName = theName; }

    /**
     * Sets the username of a user.
     *
     * @param theUsername The username of a user.
     */
    public void setUsername(String theUsername) {
        myUsername = theUsername;
    }
}
