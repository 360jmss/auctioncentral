import java.io.Serializable;
import java.time.LocalDate;
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
        myAuctions = new ArrayList<>();
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
    public List<Auction> getAuctions() {
        return myAuctions;
    }

    /**
     * Get the list of all auctions after specified date.
     * @param theTime The time of comparison, usually the current time
     * @return a filtered list of Auctions
     */
    public List<Auction> getAuctions(LocalDateTime theTime) {
        List<Auction> myNewAuctions = new ArrayList<>();
        for(Auction a : myAuctions) {
            if(a.getStartTime().isAfter(theTime)) {
                myNewAuctions.add(a);
            }
        }
        return myNewAuctions;
    }

    /**
     * Get the list of all auctions after specified date
     *  within a one month period.
     * @param theTime The time of comparison, usually the current time
     * @return a filtered list of Auctions
     */
    public List<Auction> getAuctionsOneMonth(LocalDateTime theTime) {
        List<Auction> myNewAuctions = new ArrayList<>();
        for(Auction a : myAuctions) {
            if(a.getStartTime().isAfter(theTime) && a.getStartTime().isBefore(theTime.plusMonths(1))) {
                myNewAuctions.add(a);
            }
        }
        return myNewAuctions;
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
     *  @param theAuction a valid and complete Auction
     *  @return true if the Auction meets the above requirements, false otherwise
     */
    public boolean validateAuction(Auction theAuction) {
        return checkAuctionsByContact(theAuction) &&
                checkBetweenWeekAndMonth(theAuction) &&
                checkAuctionTotalPerDate(theAuction) &&
                (getFutureAuctionTotal() < 25);
    }

    /**
     *  Helper method to check if the auction to be scheduled is between one week and month.
     *  @param theAuction the auction to test
     */
    private boolean checkBetweenWeekAndMonth(Auction theAuction) {
        LocalDate weekOut = LocalDate.now().plusWeeks(1).minusDays(1);
        LocalDate monthOut = LocalDate.now().plusMonths(1).plusDays(1);
        return theAuction.getStartTime().toLocalDate().isAfter(weekOut) &&
                theAuction.getStartTime().toLocalDate().isBefore(monthOut);
    }

    /**
     *  Helper method to check if there is an auction already schedule for that day.
     *  @param theAuction the auction to test
     */
    private boolean checkAuctionTotalPerDate(Auction theAuction) {
        int num = 0;
        for(Auction a : myAuctions) {
            if(a.getStartTime().toLocalDate().equals(theAuction.getStartTime().toLocalDate())) {
                num++;
            }

            if (num >= 2) return false;
        }
        return true;
    }

    /**
     * Helper method to determine if an auction has already been scheduled in the past year by a contact, and to make
     * sure only one future auction per contact.
     * @param theAuction the auction to test
     */
    private boolean checkAuctionsByContact(Auction theAuction) {
        LocalDateTime pastCutoff = LocalDateTime.now().minusYears(1);
        for(Auction a : myAuctions) {
            if(a.getContact() == theAuction.getContact()) {
                if(a.getStartTime().isAfter(pastCutoff)) return false;
            }
        }
        return true;
    }

    /**
     * Return the number of future auctions scheduled after today.
     * @return the number of auctions
     */
    public int getFutureAuctionTotal() {
        LocalDate today = LocalDate.now();
        int num = 0;
        for(Auction a : myAuctions) {
            if (a.getStartTime().toLocalDate().isAfter(today)) num++;
        }
        return num;
    }

    /**
     * Return the most recent or future auction for a contact.
     * @param theContact to find the auctions for
     * @return the auction with the latest date associated with that contact, null if it does not exist.
     */
    public Auction getContactsAuction(Contact theContact) {
        Auction auction = null;
        for( Auction a : myAuctions) {
            if(a.getContact() == theContact) {
                if(auction == null) {
                    auction = a;
                } else if (a.getStartTime().isAfter(auction.getStartTime())) {
                    auction = a;
                }
            }
        }
        return auction;
    }

    /**
     * Return the number of future auctions scheduled after today
     *  within a one month period.
     * @return the number of auctions
     */
    public int getFutureAuctionOneMonth() {
        LocalDate today = LocalDate.now();
        int num = 0;
        for(Auction a : myAuctions) {
            if (a.getStartTime().toLocalDate().isAfter(today) &&
                    a.getStartTime().toLocalDate().isBefore(today.plusMonths(1)))
                        num++;
        }
        return num;
    }

}
