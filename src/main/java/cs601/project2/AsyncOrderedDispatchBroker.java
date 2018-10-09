package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedDispatchBroker implements Broker<Review>, Runnable{
	private ReviewBlockingQueue blockingQueue;
	private ArrayList<Subscriber<Review>> subscribers;
	private boolean running;
	
	public AsyncOrderedDispatchBroker() {
		this.blockingQueue = new ReviewBlockingQueue(128);
		this.subscribers = new ArrayList<Subscriber<Review>>();
		this.running = true;
	}
	
	public void publish(Review review) {
		blockingQueue.put(review);
	}
	
	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}
	
	public void shutdown() {
		while(!blockingQueue.isEmpty()) {
			running = true;
		}
		for(Subscriber<Review> subscriber: subscribers) {
			((ReviewSubscriber)subscriber).closeWriter();
		}
		running = false;
	}
	
	public void run() {
		while(running) {
			Review review = blockingQueue.poll(100);
			if(review != null) {
				for(Subscriber<Review> subscriber: subscribers) {
					if(review.getUnixReviewTime() > 1362268800) {
						if(((ReviewSubscriber)subscriber).getType() == "new") {
							subscriber.onEvent(review);
							break;
						}
					} else {
						if(((ReviewSubscriber)subscriber).getType() == "old") {
							subscriber.onEvent(review);
							break;
						}
					}
				}
			}
		}
	}
}

// publisher keep call publish method pass review
// keep item in queue
// another thread takes out from the queue
// broker keep send item to subscriber if queue is not empty
