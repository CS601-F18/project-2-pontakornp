package cs601.project2;

import java.util.ArrayList;

public class SynchronousOrderedDispatchBroker implements Broker<Review> {
	private ArrayList<Subscriber<Review>> subscribers;
	
	public SynchronousOrderedDispatchBroker() {
		subscribers = new ArrayList<Subscriber<Review>>();
	}
	
	public synchronized void publish(Review review) {
		
		for(Subscriber<Review> subscriber: subscribers) {
			subscriber.onEvent(review);
		}
	}

	public void subscribe(Subscriber<Review> subscriber) {
		this.subscribers.add(subscriber);
	}

	public void shutdown() {
		//return
	}
	
	///look at exampleeeeeeeeeeeeeeeeeeeeeeeee facebook

}