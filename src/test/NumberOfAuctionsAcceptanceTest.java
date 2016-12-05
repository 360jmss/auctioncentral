package test;

import model.Auction;
import model.Calendar;
import model.Contact;

import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * This is a JUnit test class for retrieving the number of auctions in the system
 * @author Miguel Barreto
 * @version December 4 2016
 */
public class NumberOfAuctionsAcceptanceTest {

    private Calendar calendar;
    private Contact jd;
    private Auction futureJD;

    @org.junit.Before
    public void setUp() {
        calendar = new Calendar();

        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");
        futureJD = new Auction(LocalDateTime.now().plusWeeks(3), jd);
    }

    @org.junit.Test
    public void testGetAuctionTotalOnDayNone() {
        assertEquals(0, calendar.getAuctionTotalOnDay(LocalDate.now()));
    }

    @org.junit.Test
    public void testGetAuctionTotalOnDayOne() {
        calendar.addAuction(futureJD);
        assertEquals(1, calendar.getAuctionTotalOnDay(futureJD.getStartTime().toLocalDate()));
    }

    @org.junit.Test
    public void testGetAuctionTotalOnDayTwo() {
        calendar.addAuction(futureJD);
        calendar.addAuction(new Auction(LocalDateTime.now().plusWeeks(3), jd));
        assertEquals(2, calendar.getAuctionTotalOnDay(futureJD.getStartTime().toLocalDate()));
    }

}
