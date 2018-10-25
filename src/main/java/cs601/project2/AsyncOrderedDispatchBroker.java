package cs601.project2;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 
 * @author pontakornp
 *
 * AsyncOrderedDispatchBroker - one of the three brokers for this project.
 * Is an intermediary that pass on Amazon review item to from publisher to subscriber.
 * 
 * Works asynchronously by receiving review items from publisher, put item in the blocking queue, 
 * and return back to publisher right away without waiting for the process of sending to subscribers to be done,
 * while another thread tries to take from the queue if there is an item and send to subscribers in order.
 * 
 * Uses blocking queue, ReviewBlockingQueue, as a helper to manage the synchronization of items inside it.
 * Uses helper class, AsyncOrderedDispatchBrokerHandler, as a thread to take the item from the queue and send to subscriber.
 *
 */
public class AsyncOrderedDispatchBroker<T> implements Broker<T>{
	private CopyOnWriteArrayList<Subscriber<T>> subscribers;
	private CS601BlockingQueue<T> rbq;
	private AsyncOrderedDispatchBrokerHandler<T> handler;
	private Thread handlerThread;
	
	public AsyncOrderedDispatchBroker(int blockingQueueSize, int pollTimeout) {
		this.subscribers = new CopyOnWriteArrayList<Subscriber<T>>();
		this.rbq = new CS601BlockingQueue<T>(blockingQueueSize);
		this.handler = new AsyncOrderedDispatchBrokerHandler<T>(subscribers, rbq, pollTimeout);
		this.handlerThread = new Thread(handler);
		handlerThread.start();
	}
	
	/**
	 * Publish method for publisher to send items to registered subscribers.
	 * Puts review item in the queue.
	 */
	@Override
	public void publish(T item) {
		rbq.put(item);
	}
	
	/**
	 * Subscribe method for subscribers to receive future published items.
	 * Adds subscriber to a list.
	 */
	@Override
	public void subscribe(Subscriber<T> subscriber) {
		subscribers.add(subscriber);
	}
	
	/**
	 * Shutdown the helper class instance and wait for the thread to stop.
	 */
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