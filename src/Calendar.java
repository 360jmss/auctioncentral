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

    /**
     * Check to see if an auction can fit in the Calendar with the correct rules.
     *  - Maximum of one future auction for a non-profit
     *  - No auctions withing past year
     *  - Max two auctions per day
     *  - Max 25 auctions
     *  - Cannot schedule more than one month into future
     *  - Auction must be one week from the day it is scheduled
     *  @author Simon DeMartini
     *  @param auction a valid and complete Auction
     *  @return true if the Auction meets the above requirements, false otherwise
     */
    public boolean validateAuction(Auction auction) {
        return isOnlyAuctionForNPO(auction.getContact());
    }

    /**
     * Helper method to determine if a contact already has an Auction scheduled
     * @param contact the contact person
     */
    private boolean isOnlyAuctionForNPO(Contact contact) {
        for(Auction a : myAuctions) {
            if(a.getContact() == contact) return false;
        }
        return true;
    }

}
