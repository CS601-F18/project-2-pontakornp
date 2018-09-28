package cs601.project2;

/**
 * 
 * @author pontakornp
 * 
 * This class represents review which is the child of customer engagement class.
 * It contains reviewer record in json format and unix review time.
 * 
 */
public class Review{
	private String reviewRecord;
	private long unixReviewTime;

	public Review(String reviewRecord, long unixReviewTime) {
		this.reviewRecord = reviewRecord;
		this.unixReviewTime = unixReviewTime;
	}
	
	public String getReviewerRecord() {
		return this.reviewRecord;
	}
	
	public void setReviewerRecord(String reviewRecord) {
		this.reviewRecord = reviewRecord;
	}
	
	public long getUnixReviewTime() {
		return this.unixReviewTime;
	}
	
	public void setUnixReviewTime(long unixReviewTime) {
		this.unixReviewTime = unixReviewTime;
	}
	
	public String toString() {
		return "Unix Review Time: " + unixReviewTime + "\n";
	}
}