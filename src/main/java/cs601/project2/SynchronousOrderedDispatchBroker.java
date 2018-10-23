package cs601.project2;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author pontakornp
 *
 * SyncronousOrderedDispatchBroker - one of the three brokers for this project.
 * Is an intermediary that pass on Amazon review item to from publisher to subscriber.
 * 
 * Works synchronously by receiving review items from publisher and send items to current subscribers before returning to publishers.
 * Items are sent to each subscribers in the same order.
 *
 */
public class SynchronousOrderedDispatchBroker implements Broker<Review> {
	private CopyOnWriteArrayList<Subscriber<Review>> subscribers;
	
	public SynchronousOrderedDispatchBroker() {
		this.subscribers = new CopyOnWriteArrayList<Subscriber<Review>>();
	}
	
	/**
	 * Publisher to publishes review items to registered subscribers.
	 */
	@Override
	public synchronized void publish(Review review) {
		for(Subscriber<Review> subscriber: subscribers) {
			subscriber.onEvent(review);
		}
	}

	/**
	 * Subscribers to subscribes to receive published items.
	 */
	@Override
	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}

	/**
	 * Shutdown method. Nothing is done for sync ordered broker as no thread is used.
	 */
	@Override
	public void shutdown() {
		
	}
}