package test;

import model.AuctionItem;
import model.Staff;

import static org.junit.Assert.*;

/**
 * The test for Staff.
 *
 * @author Jessica Sills
 * @version November 21 2016
 */
public class StaffTest {

    private Staff staff1;
    private Staff staff2;
    private Staff staff3;
    private AuctionItem ai;

    @org.junit.Before
    public void setUp() throws Exception {
        staff1 = new Staff("staff1", "s1");
        staff2 = new Staff("staff2", "s2");
        staff3 = new Staff("staff3", "s1");
        ai = new AuctionItem("ai", "1", 1, 10.0);
    }

    @org.junit.Test
    public void testEqualsOnNotEqualUsernames() throws Exception {
        assertFalse(staff1.equals(staff2));
    }

    @org.junit.Test
    public void testEqualsOnEqualUsernames() throws Exception {
        assertTrue(staff1.equals(staff3));
    }

    @org.junit.Test
    public void testEqualsOnDifferentObjects() throws Exception {
        assertFalse(staff1.equals(ai));
    }
}