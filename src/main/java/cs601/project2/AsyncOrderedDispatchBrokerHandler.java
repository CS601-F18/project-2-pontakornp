package cs601.project2;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author pontakornp
 *
 * Helper class for AsyncOrderedDispatchBroker.
 * Is a thread that take (poll method) item and the blocking queue and send it to subscribers if available.
 *
 */
public class AsyncOrderedDispatchBrokerHandler<T> implements Runnable{
	private CopyOnWriteArrayList<Subscriber<T>> subscribers;
	private CS601BlockingQueue<T> rbq;
	private int pollTimeout;
	private boolean running;
	
	public AsyncOrderedDispatchBrokerHandler(CopyOnWriteArrayList<Subscriber<T>> subscribers, CS601BlockingQueue<T> rbq, int pollTimeout) {
		this.subscribers = subscribers;
		this.rbq = rbq;
		this.pollTimeout = pollTimeout;
		this.running = true;
	}
	
	/**
	 * Run thread to take item and send to registered subscribers.
	 * 
	 */
	@Override
	public void run() {
		while(running) {
			sendItemToSubscriber();
		}
		// if there is any left over item in the queue that is not send to subscribers, send those items to subscribers
		while(!rbq.isEmpty()) {
			sendItemToSubscriber();
		}
		
	}
	
	/**
	 * Send items to registered subscribers
	 */
	public void sendItemToSubscriber() {
		T item = rbq.poll(pollTimeout); // Calls poll method of blocking queue to get review item or null if timeout has reached.
		// If review has value, send item to registered subscribers.
		if(item != null) {
			for(Subscriber<T> subscriber: subscribers) {
				subscriber.onEvent(item);
			}
		}
	}
	
	/**
	 * Changes running value to false if the blocking queue is empty.
	 */
	public void shutdown() {
		running = false;
	}
}