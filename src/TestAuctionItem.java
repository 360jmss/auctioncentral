import org.junit.Test;

import static org.junit.Assert.*;

/**
 * The test of auction item.
 * @author Samantha Ong
 * @version 11/4/2016.
 */
public class TestAuctionItem {

    private AuctionItem apple;

    @org.junit.Before
    public void setUp() throws Exception {
        apple = new AuctionItem("apple", "1", 1, 10.0);
        apple.addBid("Hannah", 55.0);
    }


    @org.junit.Test
    public void testIsValidPriceForBidOnNegativeBid() {
        assertFalse(apple.isValidBidPrice(-1.0));
    }

    @org.junit.Test
    public void testIsValidPriceForBidOnZeroBid() {
        assertFalse(apple.isValidBidPrice(0.0));
    }

    @org.junit.Test
    public void testIsValidPriceForBidOnEqualToMinBid() {
        assertTrue(apple.isValidBidPrice(10.0));
    }

    @org.junit.Test
    public void testIsValidPriceForBidOnGreaterThanMinBid() {
        assertTrue(apple.isValidBidPrice(50.0));
    }

    @org.junit.Test
    public void testIsValidPriceForBidOnLessThanMinBid() {
        assertFalse(apple.isValidBidPrice(2.0));
    }

    @org.junit.Test
    public void testIsHighestBidPriceOnLessThanHighestBid() {
        assertFalse(apple.isHighestBid(54.0));
    }

    @org.junit.Test
    public void testIsHighestBidPriceOnEqualToHighestBid() {
        assertFalse((apple.isHighestBid(55.0)));
    }

    @org.junit.Test
    public void testIsHighestBidPriceOnGreaterThanHighestBid() {
        assertTrue(apple.isHighestBid(57.0));
    }




}
