package test;

import model.Auction;
import model.Calendar;
import model.Contact;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

/**
 * The unit tests for Calendar.
 * @author Miguel Barreto, Simon DeMartini
 * @version 11 Nov, 2016
 */
public class CalendarTest {

    private Calendar calendar;
    private Contact jd;
    private Auction futureJD1, futureJD2, futureJD3, futureJD4;

    @org.junit.Before
    public void setUp() throws Exception {
        calendar = new Calendar();

        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");
        futureJD1 = new Auction(LocalDateTime.now().plusWeeks(2), jd);
        futureJD2 = new Auction(LocalDateTime.now().plusWeeks(3), jd);
        futureJD3 = new Auction(LocalDateTime.now().plusWeeks(4), jd);
        futureJD4 = new Auction(LocalDateTime.now().plusWeeks(5), jd);
        //more than one auction within past year?
    }

    /**
     * Generate a quick new and unique contact for testing
     * @param id some number to make the username unique
     * @return a new contact
     */
    public Contact genContact(int id) {
        return new Contact("John Doe",
                "johndoe" + id,
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics" + id);
    }

    @org.junit.Test
    public void testGetAuctions() throws Exception {
        calendar.addAuction(futureJD1);
        calendar.addAuction(futureJD2);
        calendar.addAuction(futureJD3);
        calendar.addAuction(futureJD4);
        assertThat(calendar.getAuctions(), is(calendar.getAuctions(LocalDateTime.now())));
    }

    @org.junit.Test
    public void testGetAuctionsOneMonth() throws Exception {
        calendar.addAuction(futureJD1);
        calendar.addAuction(futureJD2);
        calendar.addAuction(futureJD3);
        calendar.addAuction(futureJD4);
        assertThat(calendar.getAuctions(), not(calendar.getAuctionsOneMonth(LocalDateTime.now())));
    }

    @org.junit.Test
    public void testValidateAuctionFirstContactAuction() throws Exception {
        assertEquals(0, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionSecondContactAuction() throws Exception {
        calendar.addAuction(futureJD1);
        assertEquals(1, calendar.validateAuction(futureJD2));
    }

    //THIS TEST SHOULD NOT EVEN BE POSSIBLE TO TEST ONCE EVERYTHING IS WORKING
    @org.junit.Test
    public void testValidateAuctionThirdContactAuction() throws Exception {
        calendar.addAuction(futureJD1);
        calendar.addAuction(futureJD2);
        assertEquals(1, calendar.validateAuction(futureJD3));
    }

    @org.junit.Test
    public void testValidateAuctionNoneInPastYear() throws Exception {
        assertEquals(0, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionLessThanPastYear() throws Exception {
        Auction pastJD6m = new Auction(LocalDateTime.now().minusMonths(6), jd); // - 6months ago
        calendar.addAuction(pastJD6m);
        assertEquals(1,  calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionOneDayLessThanPastYear() throws Exception {
        Auction pastJDlt1y = new Auction(LocalDateTime.now().minusYears(1).plusDays(1), jd); // - today minus ( 1 year - 1 day)
        calendar.addAuction(pastJDlt1y);
        assertEquals(1, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionExactlyPastYear() throws Exception {
        Auction pastJD1y = new Auction(LocalDateTime.now().minusYears(1), jd); // - today minus one year exactly
        calendar.addAuction(pastJD1y);
        assertEquals(0, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionOneDayMoreThanPastYear() throws Exception {
        Auction pastJDgt1y = new Auction(LocalDateTime.now().minusYears(1).minusDays(1), jd); // today minus (1 year + 1 day)
        calendar.addAuction(pastJDgt1y);
        assertEquals(0, calendar.validateAuction(futureJD1));
    }


    @org.junit.Test
    public void testValidateAuctionMax25when23Exist() throws Exception {
        for(int i = 0; i < 23; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertEquals(0, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when24Exist() throws Exception {
        for(int i = 0; i < 24; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertEquals(0, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when25Exist() throws Exception {
        for(int i = 0; i < 25; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertEquals(5, calendar.validateAuction(futureJD1));
    }

    @org.junit.Test
    public void testValidateAuctionMax25when26Exist() throws Exception {
        for(int i = 0; i < 26; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertEquals(5, calendar.validateAuction(futureJD1));
    }


    @org.junit.Test
    public void testValidateAuctionWeekAheadWith1Week() throws Exception {
        Auction future1w = new Auction(LocalDateTime.now().plusWeeks(1), jd);
        assertEquals(0, calendar.validateAuction(future1w));
    }

    @org.junit.Test
    public void testValidateAuctionWeekAheadWith1Week1day() throws Exception {
        Auction future1w = new Auction(LocalDateTime.now().plusWeeks(1).plusDays(1), jd);
        assertEquals(0, calendar.validateAuction(future1w));
    }

    @org.junit.Test
    public void testValidateAuctionWeekAheadWith6days() throws Exception {
        Auction future1w = new Auction(LocalDateTime.now().plusDays(6), jd);
        assertEquals(2, calendar.validateAuction(future1w));
    }

    @org.junit.Test
    public void testValidateAuctionWeekAheadWith2days() throws Exception {
        Auction future1w = new Auction(LocalDateTime.now().plusDays(2), jd);
        assertEquals(2, calendar.validateAuction(future1w));
    }

    @org.junit.Test
    public void testValidateAuctionWeekAheadWithToday() throws Exception {
        Auction future1w = new Auction(LocalDateTime.now(), jd);
        assertEquals(2, calendar.validateAuction(future1w));
    }

    @org.junit.Test
    public void testValidateAuctionWeekAheadWithYesterday() throws Exception {
        Auction future1w = new Auction(LocalDateTime.now().minusDays(1), jd);
        assertEquals(2, calendar.validateAuction(future1w));
    }

    @org.junit.Test
    public void testValidateAuctionMonthMaxExactly() throws Exception {
        Auction future1m = new Auction(LocalDateTime.now().plusMonths(1), jd);
        assertEquals(0, calendar.validateAuction(future1m));
    }

    @org.junit.Test
    public void testValidateAuctionMonthMaxMinus1Day() throws Exception {
        Auction future1m = new Auction(LocalDateTime.now().plusMonths(1).minusDays(1), jd);
        assertEquals(0, calendar.validateAuction(future1m));
    }

    @org.junit.Test
    public void testValidateAuctionMonthMaxPlus1Day() throws Exception {
        Auction future1m = new Auction(LocalDateTime.now().plusMonths(1).plusDays(1), jd);
        assertEquals(3, calendar.validateAuction(future1m));
    }

    @org.junit.Test
    public void testValidateAuctionMonthMaxPlus1Week() throws Exception {
        Auction future1m = new Auction(LocalDateTime.now().plusMonths(1).plusWeeks(1), jd);
        assertEquals(3, calendar.validateAuction(future1m));
    }

    @org.junit.Test
    public void testValidateAuction2PerDayWith1() throws Exception {
        Auction future2 = new Auction(LocalDateTime.now().plusWeeks(2), genContact(1));
        calendar.addAuction(futureJD1);
        assertEquals(0, calendar.validateAuction(future2));
    }

    @org.junit.Test
    public void testValidateAuction2PerDayWith2() throws Exception {
        Auction future2 = new Auction(LocalDateTime.now().plusWeeks(2), genContact(1));
        Auction future3 = new Auction(LocalDateTime.now().plusWeeks(2), genContact(2));
        calendar.addAuction(futureJD1);
        calendar.addAuction(future2);
        assertEquals(4, calendar.validateAuction(future3));
    }

    @org.junit.Test
    public void testValidateAuction2PerDayWith3() throws Exception {
        Auction future2 = new Auction(LocalDateTime.now().plusWeeks(2), genContact(1));
        Auction future3 = new Auction(LocalDateTime.now().plusWeeks(2), genContact(2));
        Auction future4 = new Auction(LocalDateTime.now().plusWeeks(2), genContact(3));
        calendar.addAuction(futureJD1);
        calendar.addAuction(future2);
        calendar.addAuction(future3);
        assertEquals(4, calendar.validateAuction(future4));
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

    @org.junit.Test
    public void testGetFutureAuctionOneMonthTodayTomorrow() throws Exception {
        calendar.addAuction(new Auction(LocalDateTime.now(), jd));
        calendar.addAuction(futureJD1);
        assertEquals(1, calendar.getFutureAuctionTotal());
    }

    @org.junit.Test
    public void testGetFutureAuctionOneMonthZero() throws Exception {
        assertEquals(0, calendar.getFutureAuctionTotal());
    }

    @org.junit.Test
    public void testGetFutureAuctionOneMonth7() throws Exception {
        for(int i = 0; i < 7; i++) {
            calendar.addAuction(new Auction(LocalDateTime.now().plusDays(i + 1), genContact(i)));
        }
        assertEquals(7, calendar.getFutureAuctionTotal());
    }

    @org.junit.Test
    public void testContactsAuctionNoAuction() throws Exception {
        assertNull(calendar.getContactsAuction(jd));
    }

    @org.junit.Test
    public void testContactsAuctionOneAuction() throws Exception {
        calendar.addAuction(futureJD2);
        assertEquals(futureJD2, calendar.getContactsAuction(jd));
    }

    @org.junit.Test
    public void testContactsAuctionMultipleAuctions() throws Exception {
        calendar.addAuction(futureJD2);
        calendar.addAuction(futureJD3);
        assertEquals(futureJD3, calendar.getContactsAuction(jd));
    }

    @org.junit.Test
    public void testGetAuctionTotalOnDayNone() throws Exception {
        assertEquals(0, calendar.getAuctionTotalOnDay(LocalDate.now()));
    }

    @org.junit.Test
    public void testGetAuctionTotalOnDayOne() throws Exception {
        calendar.addAuction(futureJD2);
        assertEquals(1, calendar.getAuctionTotalOnDay(futureJD2.getStartTime().toLocalDate()));
    }

    @org.junit.Test
    public void testGetAuctionTotalOnDayTwo() throws Exception {
        calendar.addAuction(futureJD2);
        calendar.addAuction(new Auction(LocalDateTime.now().plusWeeks(3), jd));
        assertEquals(2, calendar.getAuctionTotalOnDay(futureJD2.getStartTime().toLocalDate()));
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
        //add a second to compensate for the time difference between creating this auction and cancelling it
        Auction auction2 = new Auction(LocalDateTime.now().plusDays(2).plusSeconds(1), jd);
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
