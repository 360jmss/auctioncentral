package test;

import model.*;

import java.time.LocalDateTime;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

/**
 * This is a JUnit test class for the Cancel Auction Request from a Contact Acceptance Test
 * @author Simon DeMartini
 * @version Nov 27 2016
 */
public class CancelAuctionRequestAcceptanceTest {
    private Calendar calendar;
    private Contact jd;

    @org.junit.Before
    public void setUp() throws Exception {
        calendar = new Calendar();

        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");

    }

    @org.junit.Test
    public void testCancelAuctionLessThan2Days() throws Exception {
        Auction tomAuction = new Auction(LocalDateTime.now().plusDays(1), jd);
        calendar.addAuction(tomAuction);
        try {
            calendar.cancelAuction(tomAuction);
            fail("IllegalArgumentException should be thrown");
        } catch (IllegalArgumentException e) {
            assertThat(e.getMessage(), is("This auction is not allowed to be cancelled at this time"));
        }
    }

    @org.junit.Test
    public void testCancelAuctionExactly2Days() throws Exception {
        Auction auction2 = new Auction(LocalDateTime.now().plusDays(2), jd);
        calendar.addAuction(auction2);
        calendar.cancelAuction(auction2);
        assertFalse(calendar.getAuctions().contains(auction2));
    }

    @org.junit.Test
    public void testCancelAuctionMoreThan2Days() throws Exception {
        Auction auction3 = new Auction(LocalDateTime.now().plusDays(3), jd);
        calendar.addAuction(auction3);
        calendar.cancelAuction(auction3);
        assertFalse(calendar.getAuctions().contains(auction3));
    }

}