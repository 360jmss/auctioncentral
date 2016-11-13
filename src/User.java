/**
 * Abstract class that encompasses Bidder, Staff, and Contact.
 *
 * @author Jessica Sills
 * @version Nov 12 2016
 */
public abstract class User {

    /** The name of a user as a string.*/
    public String myName;

    /** The username of a user as a string.*/
    public String myUsername;

    /**
     * Retrieves the name of a user.
     *
     * @return The name of a user.
     */
    public abstract String getName();

    /**
     * Retrieves the username of a user.
     *
     * @return The username of a user.
     */
    public abstract String getUsername();

    /**
     * Sets the name of a user.
     *
     * @param theName The name of a user.
     */
    public abstract void setName(String theName);

    /**
     * Sets the username of a user.
     *
     * @param theUsername The username of a user.
     */
    public abstract void setUsername(String theUsername);

}
