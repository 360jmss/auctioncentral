/**
 * Created by Jessica on 11/10/2016.
 */
public class Bidder extends User {

    /**
     * A registered bidder's phone number as a string.
     */
    private String myPhoneNumber;
    /**
     * A registered bidder's address as a string.
     */
    private String myAddress;
    /**
     * A registered bidder's email address as a string.
     */
    private String myEmail;
    /**
     * A registered bidder's payment information as a string. Set to "default in the constructor.
     */
    private String myPaymentInfo;
    /**
     * A registered bidder's list of bids.
     */
    private List<Bid> myBids;

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
        myBids = new List<Bid>();
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
}
