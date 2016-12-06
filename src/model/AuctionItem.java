package model;

import java.io.Serializable;
import java.util.Formatter;
import java.util.HashMap;
import java.util.Locale;

/**
 * This class represents an model.AuctionItem and holds necessary information
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

    /**The list of bids on this AuctionItem*/
    private HashMap<String, Double> myBidList;

    /**The highest bidder.*/
    private String myHighestBidder;

    /**The items uniqueID*/
    private int myUniqueID;

    /**
     * The constructor of model.AuctionItem.
     * @param theName the name of the auciton item.
     * @param theCondition the condition of the auction item.
     * @param theSize the size of the auction item.
     * @param theMinBid the min bid of the auction item.
     */
    public AuctionItem(String theName, String theCondition, int theSize, Double theMinBid) {
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
     * Returns the string indicating the size of this auction item.
     * @return the size description.
     */
    public String getSizeString() {
        if(mySize == 1)
            return "Small";
        else if(mySize == 2)
            return "Medium";
        else if(mySize == 3)
            return "Large";
        else
            return "";
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
        Double result;
        if(myBidList.get(theBidderName) == null) {
            result = 0.0;
        } else {
            result = myBidList.get(theBidderName);
        }
        return result;
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
     * @return true if it is a valid bid, false otherwise.
     */
    public boolean addBid(String theBidderName,Double theBid) {
        if (isValidBidPrice(theBid)) {
            myBidList.put(theBidderName, theBid);
            if (isHighestBid(theBid)) {
                myHighestBidder = theBidderName;
            }
        }

        return isValidBidPrice(theBid);

    }

    /**
     * Cancels a bid by removing it from the list of bidders.
     * Created by Jessica Sills on November 27th, 2016.
     * @param theBidderName the name of the bidder.
     * @return 0 if the bid was successfully removed, 1 if otherwise.
     */
    public int cancelBid(String theBidderName) {
        if (myBidList.containsKey(theBidderName)) {
            // If the about to be cancelled bid is currently the highest:
            if (isHighestBid(myBidList.get(theBidderName))) {
                myBidList.remove(theBidderName);
                // Look for the next highest bidder.
                String tempHighBidder = (String) myBidList.keySet().toArray()[0];
                for (String name : myBidList.keySet()) {
                    if (myBidList.get(name) > myBidList.get(tempHighBidder)) {
                        tempHighBidder = name;
                    }
                }
                myHighestBidder = tempHighBidder;
                // Otherwise:
            } else {
                myBidList.remove(theBidderName);
            }
            return 0; // The bid was successfully removed.
        } else {
            return 1; // The bid was not successfully removed because it did not exist in myBidList.
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AuctionItem that = (AuctionItem) o;

        if (mySize != that.mySize) return false;
        if (myUniqueID != that.myUniqueID) return false;
        if (myName != null ? !myName.equals(that.myName) : that.myName != null) return false;
        if (myCondition != null ? !myCondition.equals(that.myCondition) : that.myCondition != null) return false;
        if (myMinBid != null ? !myMinBid.equals(that.myMinBid) : that.myMinBid != null) return false;
        if (myDonorName != null ? !myDonorName.equals(that.myDonorName) : that.myDonorName != null) return false;
        if (myDescription != null ? !myDescription.equals(that.myDescription) : that.myDescription != null)
            return false;
        if (myComment != null ? !myComment.equals(that.myComment) : that.myComment != null) return false;
        if (myBidList != null ? !myBidList.equals(that.myBidList) : that.myBidList != null) return false;
        return myHighestBidder != null ? myHighestBidder.equals(that.myHighestBidder) : that.myHighestBidder == null;

    }

    @Override
    public int hashCode() {
        int result = myName != null ? myName.hashCode() : 0;
        result = 31 * result + (myCondition != null ? myCondition.hashCode() : 0);
        result = 31 * result + mySize;
        result = 31 * result + (myMinBid != null ? myMinBid.hashCode() : 0);
        result = 31 * result + (myDonorName != null ? myDonorName.hashCode() : 0);
        result = 31 * result + (myDescription != null ? myDescription.hashCode() : 0);
        result = 31 * result + (myComment != null ? myComment.hashCode() : 0);
        result = 31 * result + (myBidList != null ? myBidList.hashCode() : 0);
        result = 31 * result + (myHighestBidder != null ? myHighestBidder.hashCode() : 0);
        result = 31 * result + myUniqueID;
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
        return String.format("%5d%20s%20s%20.2f\n", getUniqueID(), getName(), getCondition(), getMinBid());
    }

}