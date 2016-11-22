package test;

import model.Auction;
import model.AuctionItem;
import model.Contact;

import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * This is a JUnit test class for the Auction
 * Revised by Samnantha Ong
 * @author Simon DeMartini
 * @version Nov 21 2016
 */
public class AuctionTest {

    private Auction aJan5;
    private Contact jd;

    @org.junit.Before
    public void setUp() throws Exception {
        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");

        aJan5 = new Auction(LocalDateTime.of(2017, Month.JANUARY, 5, 9, 30), jd);
        aJan5.setComment("Sample model.Auction");
        aJan5.setEstItems(5);
        aJan5.addItem(new AuctionItem("apple", "1", 1, 10.0));
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

    /**
     * @author Samantha Ong
     */
    @org.junit.Test
    public void testValidateItemOnEquals() {
        assertTrue(aJan5.validateItem(new AuctionItem("chocolates", "1", 1, 10.0)));
    }

    /**
     * @author Samantha Ong
     */
    @org.junit.Test
    public void testValidateItemOnNotEquals() {
        assertFalse(aJan5.validateItem(new AuctionItem("apple", "1", 1, 10.0)));
    }

}