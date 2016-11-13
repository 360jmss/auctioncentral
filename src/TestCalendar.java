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
    private Auction aJD1, aJD2, aJD3;

    @org.junit.Before
    public void setUp() throws Exception {
        calendar = new Calendar();

        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somehwere.edu",
                "123 Main St",
                "Veridian Dynamics");
        aJD1 = new Auction(LocalDateTime.now().plusWeeks(2), jd);
        aJD2 = new Auction(LocalDateTime.now().plusWeeks(3), jd);
        aJD3 = new Auction(LocalDateTime.now().plusWeeks(4), jd);
    }
    
    @org.junit.Test
    public void testValidateAuctionFirstContactAuction() throws Exception {
        assertTrue(calendar.validateAuction(aJD1));
    }

    @org.junit.Test
    public void testValidateAuctionSecondContactAuction() throws Exception {
        calendar.addAuction(aJD1);
        assertFalse(calendar.validateAuction(aJD2));
    }

    //THIS TEST SHOULD NOT EVEN BE POSSIBLE TO TEST ONCE EVERYTHING IS WORKING
    @org.junit.Test
    public void testValidateAuctionThirdContactAuction() throws Exception {
        calendar.addAuction(aJD1);
        calendar.addAuction(aJD2);
        assertFalse(calendar.validateAuction(aJD3));
    }


}
