package cs601.project2;

import java.util.ArrayList;

/**
 * 
 * @author pontakornp
 *
 *
 * SyncronousOrderedDispatchBroker - one of the three brokers for this project.
 * Is an intermediary that pass on Amazon review item to from publisher to subscriber.
 * 
 * Works synchronously by receiving review items from publisher and send items to current subscribers before returning to publishers.
 * Items are sent to each subscribers in the same order.
 *
 */
public class SynchronousOrderedDispatchBroker implements Broker<Review> {
	private ArrayList<Subscriber<Review>> subscribers;
	
	public SynchronousOrderedDispatchBroker() {
		this.subscribers = new ArrayList<Subscriber<Review>>();
	}
	
	@Override
	public synchronized void publish(Review review) {
		for(Subscriber<Review> subscriber: subscribers) {
			subscriber.onEvent(review);
		}
	}

	@Override
	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}

	@Override
	public void shutdown() {
		
	}
}