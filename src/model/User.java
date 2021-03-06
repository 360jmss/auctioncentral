package model;

import java.io.Serializable;

/**
 * Abstract class that encompasses model.Bidder, model.Staff, and model.Contact.
 *
 * @author Jessica Sills
 * @version Nov 12 2016
 */
public abstract class User implements Serializable{

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
