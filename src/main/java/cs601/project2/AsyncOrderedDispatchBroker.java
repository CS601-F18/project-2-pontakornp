package cs601.project2;

import java.util.ArrayList;

/**
 * 
 * @author pontakornp
 *
 * AsyncOrderedDispatchBroker - one of the three brokers for this project.
 * Is an intermediary that pass on Amazon review item to from publisher to subscriber.
 * 
 * Works asynchronously by receiving review items from publisher, put item in the blocking queue, 
 * and return back to publisher right away without waiting for the process of sending to subscribers to be done,
 * while another thread tries to take from the queue if there is an item and send to subscribers in order.
 * 
 * Uses blocking queue, ReviewBlockingQueue, as a helper to manage the synchronization of items inside it.
 * Uses helper class, AsyncOrderedDispatchBrokerHandler, as a thread to take the item from the queue and send to subscriber.
 *
 */
public class AsyncOrderedDispatchBroker implements Broker<Review>{
	private ArrayList<Subscriber<Review>> subscribers;
	private ReviewBlockingQueue rbq;
	private AsyncOrderedDispatchBrokerHandler handler;
	private Thread handlerThread;
	
	public AsyncOrderedDispatchBroker(int blockingQueueSize, int pollTimeout) {
		this.subscribers = new ArrayList<Subscriber<Review>>();
		this.rbq = new ReviewBlockingQueue(blockingQueueSize);
		this.handler = new AsyncOrderedDispatchBrokerHandler(subscribers, rbq, pollTimeout);
		this.handlerThread = new Thread(handler);
		handlerThread.start();
	}
	
	/**
	 * Publish method for publisher to send items to registered subscribers.
	 * Puts review item in the queue.
	 */
	@Override
	public void publish(Review review) {
		rbq.put(review);
	}
	
	/**
	 * Subscribe method for subscribers to receive future published items.
	 * Adds subscriber to a list.
	 */
	@Override
	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}
	
	/**
	 * Shutdown the helper class instance and wait for the thread to stop.
	 */
	@Override
	public void shutdown() {
		handler.shutdown();
		try {
			handlerThread.join();
		} catch (InterruptedException e) {
			System.out.println("Please try again.");
		}
	}
}