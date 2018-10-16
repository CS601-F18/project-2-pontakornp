package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedDispatchBroker implements Broker<Review>{
	private ArrayList<Subscriber<Review>> subscribers;
	private ReviewBlockingQueue rbq;
	private AsyncOrderedDispatchBrokerHandler handler;
	private Thread handlerThread;
	
	public AsyncOrderedDispatchBroker() {
		this.subscribers = new ArrayList<Subscriber<Review>>();
		this.rbq = new ReviewBlockingQueue(32);
		this.handler = new AsyncOrderedDispatchBrokerHandler(subscribers, rbq);
		this.handlerThread = new Thread(handler);
		handlerThread.start();
	}
	
	@Override
	public void publish(Review review) {
		rbq.put(review);
	}
	
	@Override
	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}
	
	@Override
	public void shutdown() {
		handler.shutdown();
		try {
			handlerThread.join();
		} catch (InterruptedException e) {
			System.out.println("Please try again.");
		}
	}
}
