import java.time.LocalDateTime;
import java.time.Month;

import static org.junit.Assert.*;

/**
 * Created by simon on 11/10/16.
 */
public class TestAuction {

    private Auction aJan5;

    @org.junit.Before
    public void setUp() throws Exception {
        aJan5 = new Auction(LocalDateTime.of(2017, Month.JANUARY, 5, 9, 30));
        aJan5.setComment("Sample Auction");
        aJan5.setEstItems(5);
    }

    @org.junit.Test
    public void addItem() throws Exception {
        AuctionItem ai = new AuctionItem("Apple", 1, 1, 50.00);
        aJan5.addItem(ai);
        assertEquals(1, aJan5.getItems().size());
    }

}