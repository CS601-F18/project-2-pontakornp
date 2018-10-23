package cs601.project2;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * @author pontakornp
 *
 * AsyncUnorderedDispatchBroker - one of the three brokers for this project.
 * Is an intermediary that pass on Amazon review item to from publisher to subscriber.
 * 
 * Works asynchronously by receiving review items from publisher, pass items to thread pool, 
 * and return back to publisher right away without waiting for process of sending items to subscribers to be done,
 * while thread pool send items to subscribers to subscriber in any order.
 * 
 * Uses thread pool to help in sending review items to subscribers
 * 
 */
public class AsyncUnorderedDispatchBroker implements Broker<Review> {
	private CopyOnWriteArrayList<Subscriber<Review>> subscribers;
	private ExecutorService pool;
	
	public AsyncUnorderedDispatchBroker(int nThreads) {
		this.subscribers = new CopyOnWriteArrayList<Subscriber<Review>>();
		this.pool = Executors.newFixedThreadPool(nThreads);
	}
	
	/**
	 * Uses thread pool to handle sending the published review items to registered subscribers.
	 */
	@Override
	public void publish(Review review) {
		for(Subscriber<Review> subscriber: subscribers) {
			pool.execute(new AsyncUnorderedDispatchBrokerHandler(subscriber, review));
		}
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
	 * Shutdown thread pool.
	 */
	@Override
	public void shutdown() {
		pool.shutdown();
		while(!pool.isTerminated()) {
			
		}
	}
}