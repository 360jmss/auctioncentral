import java.io.Serializable;

/**
 * Bidder holds information necessary for a bidder who wishes to use AuctionCentral.
 * This information includes a phone number, an email, an address, their payment information, and a list of bids if
 * they currently have any. These are in addition to their name and username.
 *
 * @author Jessica Sills
 * @version Nov 12 2016
 */
public class Bidder extends User {

    /** A registered bidder's phone number as a string.*/
    private String myPhoneNumber;

    /** A registered bidder's address as a string.*/
    private String myAddress;

    /** A registered bidder's email address as a string.*/
    private String myEmail;

    /** A registered bidder's payment information as a string. Set to "default in the constructor.*/
    private String myPaymentInfo;

    /** A registered bidder's list of bids.*/
    //private List<Bid> myBids;

    /**
     * Constructs a bidder object. The bidder must have a name, username, phone number, address, and email address
     * associated with them. In addition, they will have payment information but it will be updated when they bid on an
     * item.
     *
     * @param theName The name of the user.
     * @param theUsername The username of the user.
     * @param thePhoneNumber The phone number of the bidder.
     * @param theAddress The address of the bidder.
     * @param theEmail The email address of the bidder.
     */
    public Bidder(String theName, String theUsername, String thePhoneNumber, String theAddress, String theEmail) {
        myName = theName;
        myUsername = theUsername;
        myPhoneNumber = thePhoneNumber;
        myEmail = theEmail;
        myAddress = theAddress;
        myPaymentInfo = "default";
        //myBids = new List<Bid>();
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
     * Retrieves the phone number of a bidder.
     *
     * @return The phone number of a bidder.
     */
    public String getPhoneNumber() {
        return myPhoneNumber;
    }

    /**
     * Retrieves the address of a bidder.
     *
     * @return The address of a bidder.
     */
    public String getAddress() {
        return myAddress;
    }

    /**
     * Retrieves the email address of a bidder.
     *
     * @return The email address of a bidder.
     */
    public String getEmail() {
        return myEmail;
    }

    /**
     * Retrieves the payment information of a bidder.
     *
     * @return The payment information of a bidder.
     */
    public String getPaymentInfo() {
        return myPaymentInfo;
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
     * Sets the phone number of a bidder.
     *
     * @param thePhoneNumber The phone number of a bidder.
     */
    public void setPhoneNumber(String thePhoneNumber) { myPhoneNumber = thePhoneNumber; }

    /**
     * Sets the address of a bidder.
     *
     * @param theAddress The address of a bidder.
     */
    public void setAddress(String theAddress) { myAddress = theAddress; }

    /**
     * Sets the email address of a bidder.
     *
     * @param theEmail The email address of a bidder.
     */
    public void setEmail(String theEmail) {
        myEmail = theEmail;
    }

    /**
     * Sets the payment information of a bidder.
     *
     * @param thePaymentInfo The payment information of a bidder.
     */
    public void setPaymentInfo(String thePaymentInfo) { myPaymentInfo = thePaymentInfo; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Bidder bidder = (Bidder) o;

        if (myPhoneNumber != null ? !myPhoneNumber.equals(bidder.myPhoneNumber) : bidder.myPhoneNumber != null)
            return false;
        if (myAddress != null ? !myAddress.equals(bidder.myAddress) : bidder.myAddress != null) return false;
        if (myEmail != null ? !myEmail.equals(bidder.myEmail) : bidder.myEmail != null) return false;
        return myPaymentInfo != null ? myPaymentInfo.equals(bidder.myPaymentInfo) : bidder.myPaymentInfo == null;

    }

    @Override
    public int hashCode() {
        int result = myPhoneNumber != null ? myPhoneNumber.hashCode() : 0;
        result = 31 * result + (myAddress != null ? myAddress.hashCode() : 0);
        result = 31 * result + (myEmail != null ? myEmail.hashCode() : 0);
        result = 31 * result + (myPaymentInfo != null ? myPaymentInfo.hashCode() : 0);
        return result;
    }
}
