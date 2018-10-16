package cs601.project2;

import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class AsyncUnorderedDispatchBroker implements Broker<Review> {
	private ArrayList<Subscriber<Review>> subscribers;
	private ExecutorService pool;
	
	public AsyncUnorderedDispatchBroker(int nThreads) {
		this.subscribers = new ArrayList<Subscriber<Review>>();
		this.pool = Executors.newFixedThreadPool(nThreads);
	}
	
	@Override
	public void publish(Review review) {
		for(Subscriber<Review> subscriber: subscribers) {
			pool.execute(new AsyncUnorderedDispatchBrokerHandler(subscriber, review));
		}
	}
	
	@Override
	public void subscribe(Subscriber<Review> subscriber) {
		subscribers.add(subscriber);
	}
	
	@Override
	public void shutdown() {
		pool.shutdown();
		while(!pool.isTerminated()) {
			
		}
//		try {
//			pool.awaitTermination(100, TimeUnit.MILLISECONDS);
//		} catch (InterruptedException e) {
//			System.out.println("shutdown");
//		}
	}

}
