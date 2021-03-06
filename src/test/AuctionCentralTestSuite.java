package test; /**
 * The TestSuite.
 * @author Samantha Ong
 * @version 11/11/2016.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses ({
        //Unit Tests
        CalendarTest.class,
        AuctionTest.class,
        BidderTest.class,
        StaffTest.class,
        ContactTest.class,
        AuctionItemTest.class,
        UserRepoTest.class,
        //Acceptance Tests
        CancelAuctionRequestAcceptanceTest.class,
        SubmitAuctionRequestAcceptanceTest.class,
        RemoveInventoryItemFromAuctionAcceptanceTest.class,
        AddInventoryItemForAuctionAcceptanceTest.class,
        ViewUpcomingAuctionsAcceptanceTest.class,
        ChangeMaxNumberOfAuctionsAcceptanceTest.class,
        CancelBidAcceptanceTest.class,
        BidOnAnItemAcceptanceTest.class,
})

 /**
 * A required class place holder for test suite.
 */
public class AuctionCentralTestSuite {

}

