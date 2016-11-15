import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents an AuctionItem and holds necessary information
 * that an item holds.
 * @author Samantha Ong
 * @version 11/13/2016.
 */
public class AuctionItem implements Serializable{
    /**The number for which an instance of an item will be taken from.*/
    private static int ID = 0;

    /**The name of the AucitonItem*/
    private String myName;

    /**The condition of the item*/
    private String myCondition;

    /**The size of an item*/
    private int mySize;

    /**The minimum acceptable bid*/
    private Double myMinBid;

    /**The donor name, optional*/
    private String myDonorName;

    /**The item description for bidders*/
    private String myDescription;

    /**The comment for auction central staff*/
    private String myComment;

    /**The list of bids on this AucitonItem*/
    private HashMap<String, Double> myBidList;

    /**The highest bidder.*/
    private String myHighestBidder;

    /**The items uniqueID*/
    private int myUniqueID;

    /**
     * The constructor of AuctionItem.
     * @param theName the name of the auciton item.
     * @param theCondition the condition of the auction item.
     * @param theSize the size of the auction item.
     * @param theMinBid the min bid of the auction item.
     */
    AuctionItem(String theName, String theCondition, int theSize, Double theMinBid) {
        myName = theName;
        myCondition = theCondition;
        mySize = theSize;
        myMinBid = theMinBid;
        myDonorName = "";
        myDescription = "";
        myComment = "";
        myBidList = new HashMap<String, Double>();
        myHighestBidder = "";
        myUniqueID = ID;
        ID++;
    }

    /**
     * Returns the name of this auction item.
     * @return the name.
     */
    public String getName() {
        return myName;
    }

    /**
     * Returns the condition of this auction item.
     * @return the condition.
     */
    public String getCondition() {
        return myCondition;
    }

    /**
     * Returns the size of this auction item.
     * @return the size.
     */
    public int getSize() {
        return mySize;
    }

    /**
     * Returns the minimum bid of this auction item.
     * @return the minimum bid.
     */
    public Double getMinBid() {
        return myMinBid;
    }

    /**
     * Returns the donor name of this auction item.
     * @return the donors name.
     */
    public String getDonorName() {
        return myDonorName;
    }

    /**
     * Returns the description of this auction item.
     * @return the description.
     */
    public String getDescription() {
        return myDescription;
    }

    /**
     * Returns the comment of this auciton item.
     * @return the comment.
     */
    public String getComment() {
        return myComment;
    }

    /**
     * Returns the unique id of this item.
     * @return the unique id of the item.
     */
    public int getUniqueID() {
        return myUniqueID;
    }

    /**
     * Returns the highest bid on the item.
     * @return the highest bid.
     */
    public Double getHighestBid() {
        return myBidList.get(myHighestBidder);
    }

    /**
     * Returns the users bid.
     * @param theBidderName the user's name.
     * @return the users bid.
     */
    public Double getBid(String theBidderName) {
        return myBidList.get(theBidderName);
    }

    /**
     * Sets the donor name of this auction item.
     * @param theDonorName the donors name.
     */
    public void setDonorName(String theDonorName) {
        myDonorName = theDonorName;
    }

    /**
     * Sets the description of this auction item.
     * @param theDescription the description.
     */
    public void setDescription(String theDescription) {
        myDescription = theDescription;
    }

    /**
     * Sets the comment of this auction item.
     * @param theComment the comment.
     */
    public void setComment(String theComment) {
        myComment = theComment;
    }

    /**
     * Adds a bid to the list of bidder.
     * @param theBidderName the name of the bidder.
     * @param theBid the bid value.
     */
    public void addBid(String theBidderName,Double theBid) {
        if(isValidBidPrice(theBid)) {
            myBidList.put(theBidderName, theBid);
            if(isHighestBid(theBid)) {
                myHighestBidder = theBidderName;
            }
        }

    }

    /**
     * Checks if the new bid being added is the highest bid.
     * @param theBid the bid to compare with the highest bid.
     * @return true if the bid is the highest bid; false otherwise.
     */
    public boolean isHighestBid(Double theBid) {
        boolean result = false;
        if(myBidList.get(myHighestBidder) == null || myBidList.get(myHighestBidder) < theBid) {
            result = true;
        }
        return result;
    }
    /**
     * Checks if the bid price is valid, greater than
     * @param theBid the bid to check.
     * @return true if valid; false otherwise.
     */
    public boolean isValidBidPrice(Double theBid) {
        boolean result = false;
        if (theBid != null && theBid >= myMinBid) {
            result = true;
        }
        return result;
    }

    @Override
    public String toString() {
        return myUniqueID + myName + myCondition + myMinBid;
    }

}