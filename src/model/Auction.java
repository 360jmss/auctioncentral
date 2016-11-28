package model;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an auction, and holds its necessary information and related AuctionItems.
 * @author Simon DeMartini
 * @version Nov 7 2016
 */
public class Auction implements Serializable {

    /** The start time of the auction.*/
    private LocalDateTime myStartTime;

    /** The estimated number of items for the auction. */
    private int myEstItems;

    /** A comment about the auction. */
    private String myComment;

    /** A list of all AuctionItems for this auction. */
    private List<AuctionItem> myItems;

    /** The contact person for the model.Auction. */
    private Contact myContact;

    /** How many days before an auction that auctions, bids, and items can be cancelled.*/
    private static final long DAYS_CANNOT_CANCEL = 2;


    /**
     * The constructor for the auction. All fields are required. Set any optional fields with setters.
     * @param theStartTime the starting time
     */
    public Auction(LocalDateTime theStartTime, Contact theContact) {
        myStartTime = theStartTime;
        myContact = theContact;

        myEstItems = 0;
        myComment = "";
        myItems = new ArrayList<AuctionItem>();
    }

    /**
     * Add an item to the list of items for this auction.
     * @param item a valid and complete model.AuctionItem
     */
    public void addItem(AuctionItem item) {
        myItems.add(item);
    }

    /**
     * Get the start date and time of the auction.
     * @return the start date and time of the auction.
     */
    public LocalDateTime getStartTime() {
        return myStartTime;
    }

    /**
     * Set the starting time and date of the auction.
     * @param theStartTime the starting time and date.
     */
    public void setStartTime(LocalDateTime theStartTime) {
        this.myStartTime = theStartTime;
    }

    /**
     * Get the estimated number of items for this auction.
     * @return the estimated number of items
     */
    public int getEstItems() {
        return myEstItems;
    }

    /**
     * Set the estimated number of items for this auction.
     * @param theEstItems the estimated number of items
     */
    public void setEstItems(int theEstItems) {
        this.myEstItems = theEstItems;
    }

    /**
     * Get the comments about this auction
     * @return the comments
     */
    public Contact getContact() {
        return myContact;
    }

    /**
     * Get the comments about this auction
     * @return the comments
     */
    public String getComment() {
        return myComment;
    }

    /**
     * Change the comments about this auction
     * @param theComment the new comment
     */
    public void setComment(String theComment) {
        this.myComment = theComment;
    }

    /**
     * Get the list of all items for this auction
     * @return a list of all items for this auction.
     */
    public List<AuctionItem> getItems() {
        return myItems;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Auction auction = (Auction) o;

        if (myEstItems != auction.myEstItems) return false;
        if (myStartTime != null ? !myStartTime.equals(auction.myStartTime) : auction.myStartTime != null) return false;
        if (myComment != null ? !myComment.equals(auction.myComment) : auction.myComment != null) return false;
        if (myItems != null ? !myItems.equals(auction.myItems) : auction.myItems != null) return false;
        return myContact != null ? myContact.equals(auction.myContact) : auction.myContact == null;

    }

    @Override
    public int hashCode() {
        int result = myStartTime != null ? myStartTime.hashCode() : 0;
        result = 31 * result + myEstItems;
        result = 31 * result + (myComment != null ? myComment.hashCode() : 0);
        result = 31 * result + (myItems != null ? myItems.hashCode() : 0);
        result = 31 * result + (myContact != null ? myContact.hashCode() : 0);
        return result;
    }

    /**
     * Gets the String representation of an auction containing the date and the organization name
     */
    public String toString() {
        return this.getStartTime().toLocalDate().toString() + " " + this.getContact().getOrganization();
    }

    /**
     * Validates whether or not an item is already in the system for this auction.
     * Revised by Samantha Ong
     * @author Jessica
     * @param theItem The item to be validated.
     * @return True if the item is not already in the system, false otherwise.
     */
    public boolean validateItem(AuctionItem theItem) {
        boolean validate = true;

        for (int i = 0; i < myItems.size(); i++) {
            if (theItem.getName().equals(myItems.get(i).getName())) {
                validate = false;
            }
        }

        return validate;
    }

    /**
     * Validates whether or not an item, auction, or bid can be cancelled.
     * Created by Jessica Sills on November 27th 2016.
     * @return Whether or not something can be cancelled.
     */
    public boolean isCancelable() {
        LocalDateTime today = LocalDateTime.now();
        if (today.isAfter(myStartTime.minusDays(DAYS_CANNOT_CANCEL))) {
            return false;
        } else {
            return true;
        }
    }

}
