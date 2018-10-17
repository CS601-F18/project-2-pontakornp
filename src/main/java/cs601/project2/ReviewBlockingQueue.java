package cs601.project2;

/**
 * 
 * @author pontakornp
 *
 * Review Blocking Queue has the same logic as the blocking queue in java library.
 * 
 * Manages the synchronization of putting or taking items from the queue.
 * Also includes poll method that is similar to take method but wit
 */
public class ReviewBlockingQueue {
	private Review[] reviews;
	private int start;
	private int end;
	private int size;

	public ReviewBlockingQueue(int size) {
		this.reviews = new Review[size];
		this.start = 0;
		this.end = -1;
		this.size = 0;
	}

	/**
	 * Put an item in the queue if the queue is not full.
	 * If the queue is full, wait until there is space in the queue,
	 * then put that item in the queue.
	 * 
	 * @param review
	 */
	public synchronized void put(Review review) {
		while(size == reviews.length) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}				
		int next = (end+1)%reviews.length;
		reviews[next] = review;
		end = next;		
		size++;
		if(size == 1) {
			this.notifyAll();
		}
		
	}

	/**
	 * Returns an item if the queue is not empty.
	 * If the queue is empty, wait until an item has been added to the queue,
	 * then return that item.
	 * 
	 * @return item
	 */
	public synchronized Review take() {
		while(size == 0) {
			try {
				this.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}

		Review review = reviews[start];
		start = (start+1)%reviews.length;
		size--;
		if(size == reviews.length-1) {
			this.notifyAll();
		}
		return review;
	}

	/**
	 * 
	 * Checks if queue is empty or not
	 * 
	 * @return true or false
	 */
	public synchronized boolean isEmpty() {
		return size == 0;
	}
	
	/**
	 * 
	 * @param timeout - time in millisecond
	 * @return item
	 * 
	 * Works similarly to take method but with a timeout
	 * 
	 * 3 scenarios that can happen when calling poll method:
	 * 1. When queue has an item, return the item
	 * 2. When queue is empty, but the timeout has not reached, wait for item to be added in the queue
	 * 3. When queue is empty, but the timeout has expired, return null
	 */
	public synchronized Review poll(long timeout) {
		long pollStart = System.currentTimeMillis();
		long pollEnd = 0;
		while(size == 0) {
			try {
				this.wait(timeout);
			} catch (InterruptedException e) {
				System.out.println("Please try again.");
			}
			pollEnd = System.currentTimeMillis();
			if((pollEnd - pollStart) >= timeout) {
				return null;
			}
			timeout = timeout - (pollEnd - pollStart);
		}
		Review review = reviews[start];
		start = (start+1)%reviews.length;
		size--;
		if(size == reviews.length-1) {
			this.notifyAll();
		}
		return review;
	}
}