/**
 * Created by Jessica on 11/10/2016.
 */

public class Contact extends User {
    private String myBusinessPhoneNumber;
    private String myBusinessEmail;
    private String myBusinessAddress;
    private String myOrganization;
    private Auction myAuction;

    public Contact(String theName, String theUsername, String theBusinessPhoneNumber,
                   String theBusinessEmail, String theBusinessAddress,
                   String theOrganization) {
        myName = theName;
        myUsername = theUsername;
        myBusinessPhoneNumber = theBusinessPhoneNumber;
        myBusinessEmail = theBusinessEmail;
        myBusinessAddress = theBusinessAddress;
        myOrganization = theOrganization;
        myAuction = new Auction();
    }

}
