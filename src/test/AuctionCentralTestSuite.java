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
        AddInventoryItemForAuction.class,
})

 /**
 * A required class place holder for test suite.
 */
public class AuctionCentralTestSuite {

}

