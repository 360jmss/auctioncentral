import java.io.Serializable;

/**
 * Contact holds information necessary for a contact of a non-profit organization who wishes to use AuctionCentral.
 * This information includes a business phone number, a business email, a business address, the name of their
 * organization, and an auction, if they currently have one scheduled. These are in addition to their name and
 * username.
 *
 * @author Jessica Sills
 * @version Nov 12 2016
 */

public class Contact extends User {

    /** The contact person's business phone number as a string.*/
    private String myBusinessPhoneNumber;

    /** The contact person's business email as a string.*/
    private String myBusinessEmail;

    /** The contact person's business address as a string.*/
    private String myBusinessAddress;

    /** The contact person's organization name as a string.*/
    private String myOrganization;

    /** The contact person's Auction.*/
    private Auction myAuction;

    /**
     * Constructs a Contact object. The contact must have a name, username, business phone number, business email,
     * business address, and organization associated with it. In addition, they will have an Auction, but it will be
     * updated when they enter an auction.
     *
     * @param theName The name of the user.
     * @param theUsername The username of the user.
     * @param theBusinessPhoneNumber The business phone number of the contact.
     * @param theBusinessEmail The business email of the contact.
     * @param theBusinessAddress The business address of the contact.
     * @param theOrganization The name of the organization of the contact.
     */
    public Contact(String theName, String theUsername, String theBusinessPhoneNumber,
                   String theBusinessEmail, String theBusinessAddress,
                   String theOrganization) {
        myName = theName;
        myUsername = theUsername;
        myBusinessPhoneNumber = theBusinessPhoneNumber;
        myBusinessEmail = theBusinessEmail;
        myBusinessAddress = theBusinessAddress;
        myOrganization = theOrganization;
        //myAuction = new Auction();
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
     * Retrieves the username of a user.
     *
     * @return The username of a user.
     */
    public String getUsername() {
        return myUsername;
    }

    /**
     * Retrieves the business phone number of a contact.
     *
     * @return The business phone number of a contact.
     */
    public String getBusinessPhoneNumber() {
        return myBusinessPhoneNumber;
    }

    /**
     * Retrieves the business address of a contact.
     *
     * @return The business address of a contact.
     */
    public String getBusinessAddress() {
        return myBusinessAddress;
    }

    /**
     * Retrieves the business email of a contact.
     *
     * @return The business email of a contact.
     */
    public String getBusinessEmail() {
        return myBusinessEmail;
    }

    /**
     * Retrieves the name of the organization of the contact.
     *
     * @return The name of the organization of the contact.
     */
    public String getOrganization() {
        return myOrganization;
    }

    /**
     * Retrieves the Auction that the contact has scheduled.
     *
     * @return The Auction that a contact has scheduled.
     */
    public Auction getAuction() {
        return myAuction;
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

    /**
     * Sets the business phone number of a contact.
     *
     * @param theBusinessPhoneNumber The business phone number of a contact.
     */
    public void setBusinessPhoneNumber(String theBusinessPhoneNumber) {
        myBusinessPhoneNumber = theBusinessPhoneNumber;
    }

    /**
     * Sets the business address of a contact.
     *
     * @param theBusinessAddress The business address of a contact.
     */
    public void setBusinessAddress(String theBusinessAddress) {
        myBusinessAddress = theBusinessAddress;
    }

    /**
     * Sets the business email of a contact.
     *
     * @param theBusinessEmail The business email of a contact.
     */
    public void setBusinessEmail(String theBusinessEmail) {
        myBusinessEmail = theBusinessEmail;
    }

    /**
     * Sets the name of the organization of the contact.
     *
     * @param theOrganization The name of the organization of the contact.
     */
    public void setOrganization(String theOrganization) {
        myOrganization = theOrganization;
    }

    /**
     * Sets the Auction that the contact has scheduled.
     *
     * @param theAuction The Auction that a contact has scheduled.
     */
    public void setAuction(Auction theAuction) {
        myAuction = theAuction;
    }
}
