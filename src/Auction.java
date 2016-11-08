import java.util.Date;
import java.util.List;

/**
 * This class represents an auction, and holds its necessary information and related AuctionItems.
 * @author Simon DeMartini
 * @version Nov 7 2016
 */
public class Auction {

    /** The start time of the auction.*/
    private Date myStartTime;

    /** The ending time of the auction. */
    private Date myEndTime;

    /** The estimated number of items for the auction. */
    private int myEstItems;

    /** A comment about the auction. */
    private String myComment;

    /** A list of all AuctionItems for this auction. */
    private List<AuctionItem> myItems;


    /**
     * The constructor for the auction. All fields are required.
     * @param theStartTime
     * @param theEndTime
     */
    Auction(Date theStartTime, Date theEndTime) {
        //TODO make Auction constructor
    }

    /**
     * Add an item to the list of items for this auction.
     * @param item a valid and complete AuctionItem
     */
    public void addItem(AuctionItem item) {
        //TODO add items
    }

    /**
     * Checks if an auction is currently able to be bid on by bidders. (Up to 24 hrs before the auction)
     * @return true if active, false otherwise
     */
    public boolean isActive() {
        //TODO do we need this?
        return false;
    }
    
    /**
     * Get the start date and time of the auction.
     * @return the start date and time of the auction.
     */
    public Date getStartTime() {
        return myStartTime;
    }

    /**
     * Set the starting time and date of the auction.
     * @param theStartTime the starting time and date.
     */
    public void setStartTime(Date theStartTime) {
        this.myStartTime = theStartTime;
    }

    /**
     * Get the end date and time of the auction.
     * @return the end date and time of the auction.
     */
    public Date getEndTime() {
        return myEndTime;
    }

    /**
     * Set the ending time and date of the auction.
     * @param theEndTime the ending time and date.
     */
    public void setEndTime(Date theEndTime) {
        this.myEndTime = theEndTime;
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
}
