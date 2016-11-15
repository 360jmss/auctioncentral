package test; /**
 * The TestSuite.
 * @author Samantha Ong
 * @version 11/11/2016.
 */

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses ({
        CalendarTest.class,
        AuctionTest.class,
        //TestUser.class,
        //TestBidder.class,
        //TestStaff.class,
        //TestContact.class,
        AuctionItemTest.class,
        UserRepoTest.class,
        //TestAuctionCentral.class
})

 /**
 * A required class place holder for test suite.
 */
public class AuctionCentralTestSuite {

}

