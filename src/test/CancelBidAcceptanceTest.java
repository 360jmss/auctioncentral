package test;

import model.Auction;
import model.AuctionItem;
import model.Calendar;
import model.Contact;

import java.time.LocalDateTime;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Test cancelling a bid on an item
 * Created by Simon DeMartini on 12/5/16.
 */
public class CancelBidAcceptanceTest {

    private AuctionItem apple;

    private Calendar calendar;

    private Contact jd;

    @org.junit.Before
    public void setUp() throws Exception {
        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");
        apple = new AuctionItem("apple", "1", 1, 10.0);
        apple.addBid("Hannah", 55.0);
        calendar = new Calendar();
    }

    @org.junit.Test
    public void testCancelBidLessThan2Days() throws Exception {
        Auction tomAuction = new Auction(LocalDateTime.now().plusDays(1), jd);
        calendar.addAuction(tomAuction);
        tomAuction.addItem(apple);
        assertFalse(tomAuction.isCancelable());
        apple.cancelBid("Hannah");
    }

    @org.junit.Test
    public void testCancelBidExactly2Days() throws Exception {
        //add a second to compensate for the time difference between creating this auction and cancelling it
        Auction auction2 = new Auction(LocalDateTime.now().plusDays(2), jd);
        calendar.addAuction(auction2);
        auction2.addItem(apple);
        assertTrue(auction2.isCancelable());
        apple.cancelBid("Hannah");
    }

    @org.junit.Test
    public void testCancelBidMoreThan2Days() throws Exception {
        Auction auction3 = new Auction(LocalDateTime.now().plusDays(3), jd);
        calendar.addAuction(auction3);
        auction3.addItem(apple);
        assertTrue(auction3.isCancelable());
        apple.cancelBid("Hannah");
    }
}
