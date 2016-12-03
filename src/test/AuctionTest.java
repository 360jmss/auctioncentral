package test;

import model.Auction;
import model.AuctionItem;
import model.Contact;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * This is a JUnit test class for the Auction
 * Revised by Samantha Ong
 * @author Simon DeMartini
 * @version Nov 21 2016
 */
public class AuctionTest {

    private Auction aJan5;
    private Contact jd;
    private Auction aJan2;
    private Contact sd;

    @org.junit.Before
    public void setUp() throws Exception {
        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");

        sd = new Contact("Sam Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");

        aJan5 = new Auction(LocalDateTime.of(2017, Month.JANUARY, 5, 9, 30), jd);
        aJan5.setComment("Sample model.Auction");
        aJan5.setEstItems(5);
        aJan5.addItem(new AuctionItem("apple", "1", 1, 10.0));

        aJan2 = new Auction(LocalDateTime.of(2017, Month.JANUARY, 2, 9, 30), sd);
        aJan2.setComment("Sample model.Auction for Sam's other tests");
        aJan2.addItem(new AuctionItem("apple", "1", 1, 10.0));
        aJan2.addItem(new AuctionItem("cheese", "1", 1, 10.0));
    }

    @org.junit.Test
    public void testAddItem() throws Exception {
        AuctionItem ai = new AuctionItem("apple", "1", 1, 50.00);
        aJan5.addItem(ai);
        assertEquals(2, aJan5.getItems().size());
    }

    @org.junit.Test
    public void testToString() throws Exception {
        assertEquals("2017-01-05 Veridian Dynamics", aJan5.toString());
    }

    /*
     * By Samantha Ong
     */
    @org.junit.Test
    public void testValidateItemOnEquals() {
        assertTrue(aJan5.validateItem(new AuctionItem("chocolates", "1", 1, 10.0)));
    }

    /*
     * By Samantha Ong
     */
    @org.junit.Test
    public void testValidateItemOnNotEquals() {
        assertFalse(aJan2.validateItem(new AuctionItem("apple", "1", 1, 10.0)));
    }

    /*
     * By Sam
     */
    @org.junit.Test
    public void testIsValidItemIndexOnLessThanZero() {
        assertFalse(aJan2.isValidItemIndex(-1));
    }

    /*
     * By Sam
     */
    @org.junit.Test
    public void testIsValidItemIndexOnEqualToZero() {
        assertTrue(aJan2.isValidItemIndex(0));
    }

    /*
     * By Sam
     */
    @org.junit.Test
    public void testIsValidItemIndexOnLessThanListSize() {
        assertTrue(aJan2.isValidItemIndex(1));
    }

    /*
     * By Sam
     */
    @org.junit.Test
    public void testIsValidItemIndexOnGreaterThanListSize() {
        assertFalse(aJan2.isValidItemIndex(2));
    }

}