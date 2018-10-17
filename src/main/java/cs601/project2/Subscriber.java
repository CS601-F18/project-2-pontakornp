package cs601.project2;

/**
 * 
 * @author pontakornp
 *
 * @param <T> - generic item
 * 
 * Subscriber interface is use for publisher/ subscriber design pattern.
 * Contains onEvent method.
 * 
 */
public interface Subscriber<T> {

	/**
	 * Called by the Broker when a new item
	 * has been published.
	 * @param item
	 */
	public void onEvent(T item);
	
}
