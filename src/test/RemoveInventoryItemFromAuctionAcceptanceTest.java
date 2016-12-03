package test;
import model.Auction;
import model.AuctionItem;
import model.Calendar;

import static org.junit.Assert.*;

import model.Contact;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;


/**
 * This is a JUnit test class for the Remove Inventory Item from an auction by a Contact Person Acceptance Test
 * @author Samantha Ong
 * @version Nov 30 2016
 */
public class RemoveInventoryItemFromAuctionAcceptanceTest {
    private Calendar calendar;
    private Contact jd;

    @Before
    public void setUp() {
        calendar = new Calendar();
        jd = new Contact("John Doe",
                "johndoe",
                "253-867-5309",
                "contact@somewhere.edu",
                "123 Main St",
                "Veridian Dynamics");


    }

    @Test
    public void testRemoveItemFromAuctionLessThan2Days() {
        Auction auction = new Auction(LocalDateTime.now().plusDays(1), jd);
        auction.addItem(new AuctionItem("apple", "1", 1, 50.00));
        calendar.addAuction(auction);
        assertEquals(1, calendar.getAuctions().get(0).removeItem(0));
    }

    @Test
    public void testRemoveItemFromAuctionExactly2Days() {
        Auction auction = new Auction(LocalDateTime.now().plusDays(2), jd);
        auction.addItem(new AuctionItem("apple", "1", 1, 50.00));
        calendar.addAuction(auction);
        assertEquals(1, calendar.getAuctions().get(0).removeItem(0));

    }

    @Test
    public void testRemoveItemFromAuctionMoreThan2Days() {
        Auction auction = new Auction(LocalDateTime.now().plusDays(3), jd);
        auction.addItem(new AuctionItem("apple", "1", 1, 50.00));
        calendar.addAuction(auction);
        assertEquals(0, calendar.getAuctions().get(0).removeItem(0));
    }



}
