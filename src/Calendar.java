import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a Calendar, which manages and schedules the auctions.
 * @author Miguel Barreto
 * @version 11 Nov, 2016
 */
public class Calendar implements Serializable {

    /** A list of all Auctions. */
    private List<Auction> myAuctions;

    /**
     * The constructor for the Calendar.
     */
    Calendar() {
        myAuctions = new ArrayList<Auction>();
    }

    /**
     * Overloaded constructor for the Calendar when passing in a list of Auctions.
     * @param theStartTime the starting time
     */
    Calendar(List<Auction> AucList) {
        myAuctions = AucList;
    }

    /**
     * Add an auction to the list of auctions.
     * @param auction a valid and complete Auction
     */
    public void addAuction(Auction auction) {
        myAuctions.add(auction);
    }

    /**
     * Get the list of all auctions
     * @return a list of Auctions
     */
    public List<Auction> getAuctions(LocalDateTime theTime) {
        return myAuctions;
    }
}
