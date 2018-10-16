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
		int count = 1;
		while(running) {
			Review review = rbq.poll(20);
			if(review != null) {
				for(Subscriber<Review> subscriber: subscribers) {
					subscriber.onEvent(review);
				}
			} else {
				System.out.println(count++);
			}
		}
	}
	
	public void shutdown() {
		while(!rbq.isEmpty()) {
//			running = true;
		}
		running = false;
	}
	
}
