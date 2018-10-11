package cs601.project2;

public class AsyncUnorderedDispatchBrokerHandler implements Runnable {
	private Subscriber<Review> subscriber;
	private Review review;
	
	public AsyncUnorderedDispatchBrokerHandler(Subscriber<Review> subscriber, Review review) {
		this.subscriber = subscriber;
		this.review = review;
	}
	
	public void run() {
		subscriber.onEvent(review);
	}
}
