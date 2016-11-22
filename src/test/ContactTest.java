package test;

import model.AuctionItem;
import model.Contact;

import static org.junit.Assert.*;

/**
 * The test for Contact.
 *
 * @author Jessica Sills
 * @version November 21 2016
 */
public class ContactTest {

    private Contact contact1;
    private Contact contact2;
    private Contact contact3;
    private Contact contact4;
    private Contact contact5;
    private AuctionItem ai;

    @org.junit.Before
    public void setUp() throws Exception {
        contact1 = new Contact("contact1", "c1", "8675309", "address1", "email1", "Bill");
        contact2 = new Contact("contact1", "c2", "8675309", "address1", "email1", "Bill");
        contact3 = new Contact("contact1", "c1", "8675309", "address1", "email1", "Bill");
        contact4 = new Contact("contact1", "c1", "8675309", "address1", "email1", "Melinda");
        contact5 = new Contact("contact1", "c2", "8675309", "address1", "email1", "Melinda");
        ai = new AuctionItem("ai", "1", 1, 10.0);
    }

    @org.junit.Test
    public void testEqualsOnNotEqualUsernamesButEqualOrganizations() throws Exception {
        assertTrue(contact1.equals(contact2));
    }

    @org.junit.Test
    public void testEqualsOnEqualUsernamesAndEqualOrganizations() throws Exception {
        assertTrue(contact1.equals(contact3));
    }

    @org.junit.Test
    public void testEqualsOnEqualUsernamesButNotEqualOrganizations() throws Exception {
        assertTrue(contact1.equals(contact4));
    }

    @org.junit.Test
    public void testEqualsOnNotEqualUsernamesAndNotEqualOrganizations() throws Exception {
        assertFalse(contact1.equals(contact5));
    }

    @org.junit.Test
    public void testEqualsOnDifferentObjects() throws Exception {
        assertFalse(contact1.equals(ai));
    }
}