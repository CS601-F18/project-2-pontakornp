package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedDispatchBrokerHandler implements Runnable{
	private ArrayList<Subscriber<Review>> subscribers;
	private ReviewBlockingQueue rbq;
	private boolean running;
	
	public AsyncOrderedDispatchBrokerHandler(ArrayList<Subscriber<Review>> subscribers, ReviewBlockingQueue rbq) {
		this.subscribers = subscribers;
		this.rbq = rbq;
		this.running = true;
	}
	
	@Override
	public void run() {
		while(running) {
			Review review = rbq.poll(2);
			if(review != null) {
				for(Subscriber<Review> subscriber: subscribers) {
					subscriber.onEvent(review);
				}
			}
		}
	}
	
	public void shutdown() {
		running = false;
	}
	
}
