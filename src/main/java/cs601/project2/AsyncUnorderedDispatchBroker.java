package cs601.project2;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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
public class AsyncUnorderedDispatchBroker<T> implements Broker<T> {
	private CopyOnWriteArrayList<Subscriber<T>> subscribers;
	private ExecutorService pool;
	
	public AsyncUnorderedDispatchBroker(int nThreads) {
		this.subscribers = new CopyOnWriteArrayList<Subscriber<T>>();
		this.pool = Executors.newFixedThreadPool(nThreads);
	}
	
	/**
	 * Uses thread pool to handle sending the published review items to registered subscribers.
	 */
	@Override
	public void publish(T item) {
		for(Subscriber<T> subscriber: subscribers) {
			pool.execute(new AsyncUnorderedDispatchBrokerHandler<T>(subscriber, item));
		}
	}
	
	/**
	 * Subscribe method for subscribers to receive future published items.
	 * Adds subscriber to a list.
	 */
	@Override
	public void subscribe(Subscriber<T> subscriber) {
		subscribers.add(subscriber);
	}
	
	/**
	 * Shutdown thread pool.
	 * reference: https://docs.oracle.com/javase/8/docs/api/java/util/concurrent/ExecutorService.html#awaitTermination-long-java.util.concurrent.TimeUnit-
	 */
	@Override
	public void shutdown() {
		pool.shutdown();
		try {
			// Wait a while for existing tasks to terminate
		     if (!pool.awaitTermination(60, TimeUnit.MILLISECONDS)) {
		    	 pool.shutdownNow(); // Cancel currently executing tasks
		     // Wait a while for tasks to respond to being cancelled
	    	 if (!pool.awaitTermination(60, TimeUnit.MILLISECONDS))
	    		 System.err.println("Pool did not terminate, please try again.");
		     }
		} catch (InterruptedException e) {
			System.out.println("Pool did not terminate properly, please try again.");
		}
	}
}