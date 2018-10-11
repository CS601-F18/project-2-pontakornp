package cs601.project2;

import java.util.ArrayList;

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
		//
	}
}