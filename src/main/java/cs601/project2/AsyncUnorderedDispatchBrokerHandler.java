package cs601.project2;

/**
 * 
 * @author pontakornp
 *
 * Helper class for AsyncUnorderedDispatchBroker.
 * Works as a thread to be instantiated in the thread pool to send item to current subscribers.
 *
 */
public class AsyncUnorderedDispatchBrokerHandler implements Runnable {
	private Subscriber<Review> subscriber;
	private Review review;
	
	public AsyncUnorderedDispatchBrokerHandler(Subscriber<Review> subscriber, Review review) {
		this.subscriber = subscriber;
		this.review = review;
	}
	
	/**
	 * Run thread to send items to registered subscribers.
	 */
	@Override
	public void run() {
		subscriber.onEvent(review);
	}
}