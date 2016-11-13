import java.time.LocalDateTime;

import static org.junit.Assert.*;

/**
 * The unit tests for Calendar.
 * @author Miguel Barreto
 * @version 11 Nov, 2016
 */
public class TestCalendar {

    private Calendar calendar;
    private Contact jd;
    private Auction futureJD1, futureJD2, futureJD3;

    @org.junit.Before
    public void setUp() throws Exception {
        calendar = new Calendar();

        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somehwere.edu",
                "123 Main St",
                "Veridian Dynamics");
        futureJD1 = new Auction(LocalDateTime.now().plusWeeks(2), jd);
        futureJD2 = new Auction(LocalDateTime.now().plusWeeks(3), jd);
        futureJD3 = new Auction(LocalDateTime.now().plusWeeks(4), jd);

        //more than one auction within past year?
    }

    public Contact genContact(int id) {
        return new Contact("John Doe",
                "johndoe" + id,
                "253-867-5309",
                "contact@somehwere.edu",
                "123 Main St",
                "Veridian Dynamics");
    }

    @org.junit.Test
    public void testValidateAuctionFirstContactAuction() throws Exception {
        assertTrue(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionSecondContactAuction() throws Exception {
        calendar.addAuction(futureJD1);
        assertFalse(calendar.validateAuction(futureJD2));
    }

    //THIS TEST SHOULD NOT EVEN BE POSSIBLE TO TEST ONCE EVERYTHING IS WORKING
    @org.junit.Test
    public void testValidateAuctionThirdContactAuction() throws Exception {
        calendar.addAuction(futureJD1);
        calendar.addAuction(futureJD2);
        assertFalse(calendar.validateAuction(futureJD3));
    }

    @org.junit.Test
    public void testValidateAuctionNoneInPastYear() throws Exception {
        assertTrue(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionLessThanPastYear() throws Exception {
        Auction pastJD6m = new Auction(LocalDateTime.now().minusMonths(6), jd); // - 6months ago
        calendar.addAuction(pastJD6m);
        assertFalse(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionOneDayLessThanPastYear() throws Exception {
        Auction pastJDlt1y = new Auction(LocalDateTime.now().minusYears(1).plusDays(1), jd); // - today minus ( 1 year - 1 day)
        calendar.addAuction(pastJDlt1y);
        assertFalse(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionExactlyPastYear() throws Exception {
        Auction pastJD1y = new Auction(LocalDateTime.now().minusYears(1), jd); // - today minus one year exactly
        calendar.addAuction(pastJD1y);
        assertTrue(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionOneDayMoreThanPastYear() throws Exception {
        Auction pastJDgt1y = new Auction(LocalDateTime.now().minusYears(1).minusDays(1), jd); // today minus (1 year + 1 day)
        calendar.addAuction(pastJDgt1y);
        assertTrue(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when23Exist() throws Exception {
        for(int i = 0; i < 23; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertTrue(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when24Exist() throws Exception {
        for(int i = 0; i < 24; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertTrue(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when25Exist() throws Exception {
        for(int i = 0; i < 25; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertFalse(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when26Exist() throws Exception {
        for(int i = 0; i < 26; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertFalse(calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testGetFutureAuctionTotalTodayTomorrow() throws Exception {
        calendar.addAuction(new Auction(LocalDateTime.now(), jd));
        calendar.addAuction(futureJD1);
        assertEquals(1, calendar.getFutureAuctionTotal());
    }

    @org.junit.Test
    public void testGetFutureAuctionTotalZero() throws Exception {
        assertEquals(0, calendar.getFutureAuctionTotal());
    }

    @org.junit.Test
    public void testGetFutureAuctionTotal5() throws Exception {
        for(int i = 0; i < 5; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertEquals(5, calendar.getFutureAuctionTotal());
    }

}
