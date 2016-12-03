package model;

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

    /** Maximum number of auctions in 1 day. */
    private static final int AUCS_PER_DAY = 2;

    /** Maximum number of auctions allowed. Can be changed by staff. */
    private static int myAuctionTotal = 25;

    /**
     * The constructor for the model.Calendar.
     */
    public Calendar() {
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
     * Cancel and remove an auction from the calendar, if it is allowed.
     * @param auction a valid and complete Auction
     */
    public void cancelAuction(Auction auction) {
        //TODO Return boolean instead of exception?
        if(auction.isCancelable()) {
            myAuctions.remove(auction);
        } else {
            throw new IllegalArgumentException("This auction is not allowed to be cancelled at this time");
        }
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
     * Sets the maximum number of auctions allowed by the system.
     *  Intended to only be used by staff members.
     * @param theAuctionTotal the new maximum number of auctions
     */
    public void setAuctionTotal(int theAuctionTotal) {
        myAuctionTotal = theAuctionTotal;
    }

    /**
     * Check to see if an auction can fit in the model.Calendar with the correct rules.
     *  - Maximum of one future auction for a non-profit
     *  - No auctions withing past year
     *  - Max two auctions per day
     *  - Max 25 auctions
     *  - Cannot schedule more than one month into future
     *  - model.Auction must be one week from the day it is scheduled
     *  @author Simon DeMartini
     *  @param theAuction a valid and complete model.Auction
     *  @return 0 if the auction meets the above requirements, otherwise
     *          1 if the contact has too many auctions
     *          2 if it is less than one week from today
     *          3 if it is more than one month from today
     *          4 if there are too many auctions scheduled that day
     *          5 if there are too many auctions scheduled total
     */
    public int validateAuction(Auction theAuction) {
        if(!isAuctionAllowedForContact(theAuction)) return 1;
        if(!isMoreThanOneWeekOut(theAuction)) return 2;
        if(!isLessThanOneMonthOut(theAuction)) return 3;
        if(!isAuctionNotTooMuchForOneDay(theAuction)) return 4;
        if(!isAuctionTotalLessThanMax()) return 5;
        return 0;
    }

    /**
     * Helper method to check if there are less the the max number of auctions scheduled.
     */
    private boolean isAuctionTotalLessThanMax() {
        return getFutureAuctionTotal() < myAuctionTotal;
    }

    /**
     *  Helper method to check if the auction to be scheduled is less than one week from today.
     *  @param theAuction the auction to test
     *  @author Simon DeMartini
     */
    private boolean isMoreThanOneWeekOut(Auction theAuction) {
        LocalDate weekOut = LocalDate.now().plusWeeks(1).minusDays(1);
        return theAuction.getStartTime().toLocalDate().isAfter(weekOut);
    }

    /**
     *  Helper method to check if the auction to be scheduled is max one month out.
     *  @param theAuction the auction to test
     *  @author Simon DeMartini
     */
    private boolean isLessThanOneMonthOut(Auction theAuction) {
        LocalDate monthOut = LocalDate.now().plusMonths(1).plusDays(1);
        return theAuction.getStartTime().toLocalDate().isBefore(monthOut);
    }

    /**
     *  Helper method to check if there is an auction already schedule for that day.
     *  @param theAuction the auction to test
     *  @author Simon DeMartini
     */
    private boolean isAuctionNotTooMuchForOneDay(Auction theAuction) {
        int num = 0;
        for(Auction a : myAuctions) {
            if(a.getStartTime().toLocalDate().equals(theAuction.getStartTime().toLocalDate())) {
                num++;
            }

            if (num >= AUCS_PER_DAY) return false;
        }
        return true;
    }

    /**
     * Helper method to determine if an auction has already been scheduled in the past year by a contact, and to make
     * sure only one future auction per contact.
     * @param theAuction the auction to test
     * @author Simon DeMartini
     */
    private boolean isAuctionAllowedForContact(Auction theAuction) {
        LocalDateTime pastCutoff = LocalDateTime.now().minusYears(1);
        for(Auction a : myAuctions) {
            if(a.getContact().equals(theAuction.getContact())) {
                if(a.getStartTime().isAfter(pastCutoff)) return false;
            }
        }
        return true;
    }

    /**
     * Return the number of future auctions scheduled after today.
     * @return the number of auctions
     * @author Simon DeMarini
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
     * @author Simon DeMartini
     */
    public Auction getContactsAuction(Contact theContact) {
        Auction auction = null;
        for( Auction a : myAuctions) {
            if(a.getContact().equals(theContact)) {
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
     * Return the number of auctions on a particular day
     */
    public int getAuctionTotalOnDay(LocalDate theDate) {
        int num = 0;
        for(Auction a : myAuctions) {
            if(a.getStartTime().toLocalDate().equals(theDate)) {
                num++;
            }
        }
        return num;
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
