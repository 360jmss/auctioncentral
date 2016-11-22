package test;

import model.AuctionItem;
import model.Bidder;

import static org.junit.Assert.*;

/**
 * The test for Bidder.
 *
 * @author Jessica Sills
 * @version November 21 2016
 */
public class BidderTest {

    private Bidder bidder1;
    private Bidder bidder2;
    private Bidder bidder3;
    private AuctionItem ai;

    @org.junit.Before
    public void setUp() throws Exception {
        bidder1 = new Bidder("bidder1", "bid1", "8675309", "address1", "email1");
        bidder2 = new Bidder("bidder1", "bid2", "8675309", "address1", "email1");
        bidder3 = new Bidder("bidder1", "bid1", "8675309", "address1", "email1");
        ai = new AuctionItem("ai", "1", 1, 10.0);
    }

    @org.junit.Test
    public void testEqualsOnNotEqualUsernames() throws Exception {
        assertFalse(bidder1.equals(bidder2));
    }

    @org.junit.Test
    public void testEqualsOnEqualUsernames() throws Exception {
        assertTrue(bidder1.equals(bidder3));
    }

    @org.junit.Test
    public void testEqualsOnDifferentObjects() throws Exception {
        assertFalse(bidder1.equals(ai));
    }
}
