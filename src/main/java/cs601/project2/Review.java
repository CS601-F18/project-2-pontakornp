package cs601.project2;

/**
 * 
 * @author pontakornp
 * 
 * This class represents Amazon review.
 * It contains review records in json format.
 * 
 * Example of review record:
 * {"reviewerID": "APYOBQE6M18AA", "asin": "0615391206", "reviewerName": "Martin Schwartz", "helpful": [0, 0], "reviewText": "My daughter wanted this book and the price on Amazon was the best.  She has already tried one recipe a day after receiving the book.  She seems happy with it.", "overall": 5.0, "summary": "Best Price", "unixReviewTime": 1382140800, "reviewTime": "10 19, 2013"}
 */
public class Review{
	private String reviewerID;
	private String asin;
	private String reviewerName;
	private int[] helpful;
	private String reviewText;
	private double overall;
	private String summary;
	private long unixReviewTime;
	private String reviewTime;
	
	public long getUnixReviewTime() {
		return this.unixReviewTime;
	}
	
	public void setUnixReviewTime(long unixReviewTime) {
		this.unixReviewTime = unixReviewTime;
	}
	
	public String toString() {
		return "Reviewer ID: " + reviewerID + "\n"
				+ "ASIN: " + asin + "\n"
				+ "Reviewer name: " + reviewerName + "\n"
				+ "Helpful: " + helpful[0] + ", " + helpful[1] + "\n"
				+ "Review text: " + reviewText + "\n"
				+ "Overall score: " + overall + "\n"
				+ "Summary: " + summary + "\n"
				+ "Unix review time: " + unixReviewTime + "\n"
				+ "Review time: " + reviewTime;		
	}
}