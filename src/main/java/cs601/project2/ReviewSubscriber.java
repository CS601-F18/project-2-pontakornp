package cs601.project2;

public class ReviewSubscriber implements Subscriber<Review>, Runnable {
	
	// save to a file
	public void onEvent(Review review) {
		System.out.println(review);
	}
	
	// run
	public void run() {
		
	}
}
