package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedDispatchBroker implements Broker<Review>, Runnable{
	private ReviewBlockingQueue blockingQueue;
	private ArrayList<Subscriber<Review>> subscriberList;
	private boolean running;
	
	public AsyncOrderedDispatchBroker() {
		blockingQueue = new ReviewBlockingQueue(32);
		subscriberList = new ArrayList<Subscriber<Review>>();
		running = true;
	}
	public void publish(Review review) {
		blockingQueue.put(review);
		synchronized(subscriberList) {
			subscriberList.notify();
		}
	}
	
	public void subscribe(Subscriber<Review> subscriber) {
		subscriberList.add(subscriber);
	}
	
	public void shutdown() {
		while(!blockingQueue.isEmpty()) {
			running = true;
		}
		running = false;
	}
	
	public void run() {
		while(running) {
			if(blockingQueue.isEmpty()) {
				synchronized(subscriberList) {
					try {
						subscriberList.wait();
					} catch (InterruptedException e) {
						System.out.println("Please try again.");
					} catch (Exception e) {
						System.out.println("for checking");
					}
				}
			} else {
				Review review = blockingQueue.take();
				for(Subscriber<Review> subscriber: subscriberList) {
					subscriber.onEvent(review);
				}
			}
		}
	}
}
