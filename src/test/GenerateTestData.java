package test;

import model.*;

import java.time.LocalDateTime;

/**
 * Generate a bunch of test data to be serialized
 * @author Simon DeMartini
 */
public class GenerateTestData {

    /** The UserRepo */
    private static UserRepo myUsers;

    /** The Calendar */
    private static Calendar myCalendar;

    public GenerateTestData() {
        myUsers = new UserRepo();
        myCalendar = new Calendar();
        createTestData();
    }

    /**
     * Create a bunch of random test data
     */
    private static void createTestData() {
        // 2 staff
        myUsers.registerUser(new Staff("Jane Smith", "jsmith"));
        myUsers.registerUser(new Staff("Bobby Joe", "bj"));

        // 5 bidders
        myUsers.registerUser(new Bidder("Leonardo", "leo", "55", "Address", "leo@email.com"));
        myUsers.registerUser(new Bidder("Katy", "katy", "234234", "Somewhere", "katy@email.com"));
        myUsers.registerUser(new Bidder("Joey", "joey", "436523", "Here", "joey@email.com"));
        myUsers.registerUser(new Bidder("Monica", "monica", "1", "There", "monica@email.com"));
        myUsers.registerUser(new Bidder("Rachel", "rachel", "32409237849", "Nowhere", "rachel@email.com"));


        //22 future standard Auctions and contacts
        for(int i = 0; i < 22; i++) {
            Contact c = new Contact("John Doe the " + i,
                    "con" + i,
                    "253-54" + i,
                    "contact" + i + "@somewhere.edu",
                    i + " Main St",
                    "Big Ole Company #" + i);

            myUsers.registerUser(c);
            myCalendar.addAuction(new Auction(LocalDateTime.now().plusDays(i), c));
        }

        //One auction-less contact
        Contact tom = new Contact("Tom Sawyer",
                "tom",
                "555-555-5555",
                "tom@somewhere.com",
                "555 Alder St",
                "HuckFinn Company");
        myUsers.registerUser(tom);
        //One auction-less contact
        Contact tom2 = new Contact("Tommy Boy",
                "tom2",
                "555-555-5555",
                "tom@somewhere.com",
                "555 Alder St",
                "Big Company");
        myUsers.registerUser(tom2);

        //2 Unique Auctions with items
        Contact bill = new Contact("Bill Gates",
                "billy",
                "111-111-1111",
                "bill@somewhere.edu",
                "123 Main St",
                "Bill Gates Foundation");
        myUsers.registerUser(bill);
        Auction billsAuction = new Auction(LocalDateTime.now().plusDays(11), bill);
        billsAuction.addItem(new AuctionItem("Apple", "Good", 1, 50.00));
        billsAuction.addItem(new AuctionItem("Orange", "Very Good", 1, 4.73));
        billsAuction.addItem(new AuctionItem("Banana", "Acceptable", 1, 11.00));
        billsAuction.addItem(new AuctionItem("Grapes", "Like New", 1, 11.00));
        myCalendar.addAuction(billsAuction);

        Contact steve = new Contact("Steve Jobs",
                "steve",
                "222-222-2222",
                "steve@somewhere.edu",
                "123 Broadway St",
                "Apples Foundation");
        myUsers.registerUser(steve);
        Auction stevesAuction = new Auction(LocalDateTime.now().plusDays(14), steve);
        stevesAuction.addItem(new AuctionItem("Rock", "New", 1, 10.00));
        stevesAuction.addItem(new AuctionItem("Twig", "Like New", 1, 50.73));
        stevesAuction.addItem(new AuctionItem("Boulder", "Very Good", 2, 1100.00));
        stevesAuction.addItem(new AuctionItem("A big river", "Good", 3, 1200.00));
        stevesAuction.addItem(new AuctionItem("Everything", "Acceptable", 2, 1832.03));

        myCalendar.addAuction(stevesAuction);

        //past auction within past year
        Contact joe = new Contact("Joe",
                "joe",
                "333-222-2222",
                "joe@somewhere.edu",
                "555 Broadway St",
                "Past Auction within past year");
        myUsers.registerUser(joe);
        Auction joeAuction = new Auction(LocalDateTime.now().minusYears(1).plusDays(5), joe);
        myCalendar.addAuction(joeAuction);

        //past auction greater than past year
        Contact kevin = new Contact("kevin",
                "kevin",
                "333-55-2222",
                "kevin@somewhere.edu",
                "555 Main St",
                "Past Auction more than past year");
        myUsers.registerUser(kevin);
        Auction kevinAuction = new Auction(LocalDateTime.now().minusYears(1).minusDays(5), kevin);
        myCalendar.addAuction(kevinAuction);
    }

    /**
     * Get the generated test UserRepo
     * @return a UserRepo with test data
     */
    public UserRepo getUserRepo() {
        return myUsers;
    }

    /**
     * Get the generated test UserRepo
     * @return a UserRepo with test data
     */
    public Calendar getCalendar() {
        return myCalendar;
    }

}
