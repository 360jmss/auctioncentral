import java.io.Serializable;
import java.util.HashMap;

/**
 * This class represents an AuctionItem and holds necessary information
 * that an item holds.
 * @author Samantha Ong
 * @version 11/11/2016.
 */
public class AuctionItem implements Serializable{

    /**The name of the AucitonItem*/
    private String myName;

    /**The condition of the item*/
    private int myCondition;

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

    /**
     * The highest bidder.
     */
    private Double myHighestBid;

    /**
     * The constructor of AuctionItem.
     * @param theName the name of the auciton item.
     * @param theCondition the condition of the auction item.
     * @param theSize the size of the auction item.
     * @param theMinBid the min bid of the auction item.
     */
    AuctionItem(String theName, int theCondition, int theSize, Double theMinBid) {
        myName = theName;
        myCondition = theCondition;
        mySize = theSize;
        myMinBid = theMinBid;
        myDonorName = "";
        myDescription = "";
        myComment = "";
        myBidList = new HashMap<String, Double>();
        myHighestBid = 0.0;
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
    public int getCondition() {
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
    public String getComent() {
        return myComment;
    }

    public Double getHighestBid() {
        return myHighestBid;
    }

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
        myBidList.put(theBidderName, theBid);
        if (myHighestBid < theBid) {
            myHighestBid = theBid;
        }
    }

}