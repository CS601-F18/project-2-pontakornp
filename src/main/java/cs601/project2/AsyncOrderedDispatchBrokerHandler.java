package cs601.project2;

import java.util.ArrayList;

/**
 * 
 * @author pontakornp
 *
 * Helper class for AsyncOrderedDispatchBroker.
 * Is a thread that take (poll method) item and the blocking queue and send it to subscribers if available.
 *
 */
public class AsyncOrderedDispatchBrokerHandler implements Runnable{
	private ArrayList<Subscriber<Review>> subscribers;
	private ReviewBlockingQueue rbq;
	private int pollTimeout;
	private boolean running;
	
	public AsyncOrderedDispatchBrokerHandler(ArrayList<Subscriber<Review>> subscribers, ReviewBlockingQueue rbq, int pollTimeout) {
		this.subscribers = subscribers;
		this.rbq = rbq;
		this.pollTimeout = pollTimeout;
		this.running = true;
	}
	
	@Override
	public void run() {
		while(running) {
			Review review = rbq.poll(pollTimeout);
			if(review != null) {
				for(Subscriber<Review> subscriber: subscribers) {
					subscriber.onEvent(review);
				}
			}
		}
	}
	
	/**
	 * Changes running value to false if the blocking queue is empty
	 */
	public void shutdown() {
		while(!rbq.isEmpty()) {
			
		}
		running = false;
	}
	
}
