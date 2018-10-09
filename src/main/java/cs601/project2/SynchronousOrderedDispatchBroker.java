package cs601.project2;

import java.util.ArrayList;

public class SynchronousOrderedDispatchBroker implements Broker<Review> {
	private ArrayList<Subscriber<Review>> subscribers;
	
	public SynchronousOrderedDispatchBroker() {
		this.subscribers = new ArrayList<Subscriber<Review>>();
	}
	
	public synchronized void publish(Review review) {
		for(Subscriber<Review> subscriber: subscribers) {
			ReviewSubscriber rs = (ReviewSubscriber)subscriber;
			if(review.getUnixReviewTime() > 1362268800) {
				if(rs.getType() == "new") {
					subscriber.onEvent(review);
					return;
				}
			} else {
				if(rs.getType() == "old") {
					subscriber.onEvent(review);
					return;
				}
			}
		}
	}

	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}

	public void shutdown() {
		//return
	}
}