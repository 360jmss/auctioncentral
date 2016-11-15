import static org.junit.Assert.*;

/**
 * The test of auction item.
 * @author Samantha Ong
 * @version 11/11/2016.
 */
public class TestAuctionItem {

    private AuctionItem apple;

    @org.junit.Before
    public void setUp() throws Exception {
        apple = new AuctionItem("apple", 1, 1, 0.1);
    }

    @org.junit.Test
    public void addBidHighestBid() throws Exception {
        apple.addBid("Sam", 1.0);
        Double one = 1.0;
        assertEquals(one, apple.getHighestBid());
    }


}
