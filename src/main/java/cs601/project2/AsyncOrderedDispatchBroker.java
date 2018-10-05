package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedDispatchBroker implements Broker<Review>, Runnable{
	private ReviewBlockingQueue blockingQueue = new ReviewBlockingQueue(32);
	private ArrayList<Subscriber<Review>> subscriberList = new ArrayList<Subscriber<Review>>();
	private boolean running = true;
	private String dummy = "";
	
	public void publish(Review review) {
		blockingQueue.put(review);
		dummy.notify(); //notify taker that review is added
	}
	
	public void subscribe(Subscriber<Review> subscriber) {
		this.subscriberList.add(subscriber);
	}
	
	public void shutdown() {
		running = false;
	}
	
	public void run() {
		while(running) {
			if(blockingQueue.isEmpty()) {
				try {
					dummy.wait();
				} catch (InterruptedException e) {
//					e.printStackTrace();
					System.out.println("what happen");
				} catch (Exception e) {
					System.out.println("whawoaw;efja");
				}
			}
			for(Subscriber<Review> subscriber: subscriberList) {
				subscriber.onEvent(blockingQueue.take());
			}
		}
	}
}
