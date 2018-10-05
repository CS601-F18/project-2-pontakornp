package cs601.project2;

import java.util.ArrayList;

public class SynchronousOrderedDispatchBroker implements Broker<Review> {
	private ArrayList<Subscriber<Review>> subscriberList = new ArrayList<Subscriber<Review>>();
//	int count = 0; // temporary use
//	public SynchronousOrderedDispatchBroker() {
//		subscriberList 
//	}
	public synchronized void publish(Review review) {
//		System.out.println(++count); // temporary use
		for(Subscriber<Review> subscriber: subscriberList) {
			subscriber.onEvent(review);
		}
	}

	public void subscribe(Subscriber<Review> subscriber) {
		this.subscriberList.add(subscriber);
	}

	public void shutdown() {
		//return
	}

}