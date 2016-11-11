/**
 * Created by Jessica on 11/10/2016.
 */
public abstract class User {

    /**
     * The name of a user as a string.
     */
    public String myName;
    /**
     * The username of a user as a string.
     */
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

}
