package cs601.project2;

/**
 * 
 * @author pontakornp
 *
 * Helper class for AsyncUnorderedDispatchBroker.
 * Works as a thread to be instantiated in the thread pool to send item to current subscribers.
 *
 */
public class AsyncUnorderedDispatchBrokerHandler<T> implements Runnable {
	private Subscriber<T> subscriber;
	private T item;
	
	public AsyncUnorderedDispatchBrokerHandler(Subscriber<T> subscriber, T item) {
		this.subscriber = subscriber;
		this.item = item;
	}
	
	/**
	 * Run thread to send items to registered subscribers.
	 */
	@Override
	public void run() {
		subscriber.onEvent(item);
	}
}