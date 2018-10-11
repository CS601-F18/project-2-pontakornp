package cs601.project2;

import java.util.ArrayList;

public class AsyncOrderedDispatchBroker implements Broker<Review>{
	private ArrayList<Subscriber<Review>> subscribers;
	private ReviewBlockingQueue rbq;
	private AsyncOrderedDispatchBrokerHandler handler;
	
	public AsyncOrderedDispatchBroker() {
		this.subscribers = new ArrayList<Subscriber<Review>>();
		this.rbq = new ReviewBlockingQueue(16);
		this.handler = new AsyncOrderedDispatchBrokerHandler(subscribers, rbq);
		startHandlerThread();
	}
	
	public void startHandlerThread() {
		Thread handlerThread = new Thread(handler);
		handlerThread.start();
		try {
			handlerThread.join();
		} catch (InterruptedException e) {
			System.out.println("Please try again.");
		}
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
		while(!rbq.isEmpty()) {
//			running = true;
		}
		handler.shutdown();
	}
	
//	@Override
//	public void run() {
//		while(running) {
//			Review review = rbq.poll(2);
//			if(review != null) {
//				for(Subscriber<Review> subscriber: subscribers) {
//					subscriber.onEvent(review);
//				}
//			}
//		}
//	}
}

// publisher keep call publish method pass review
// keep item in queue
// another thread takes out from the queue
// broker keep send item to subscriber if queue is not empty
