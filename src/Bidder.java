/**
 * Created by Jessica on 11/10/2016.
 */
public class Bidder extends User {

    private String myPhoneNumber;
    private String myEmail;
    private String myAddress;
    private String myPaymentInfo;
    private List<Bid> myBids;

    public Bidder(String theName, String theUsername, String thePhoneNumber,
                  String theEmail, String theAddress) {
        myName = theName;
        myUsername = theUsername;
        myPhoneNumber = thePhoneNumber;
        myEmail = theEmail;
        myAddress = theAddress;
        myPaymentInfo = "default";
        myBids = new List<Bid>();
    }
}
