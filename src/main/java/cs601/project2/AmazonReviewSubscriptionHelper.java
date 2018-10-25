package cs601.project2;

/**
 * 
 * @author pontakornp
 *
 * This helps for the main class separate the instantiation process of broker and running 2 publisher threads.
 * This helps make the main method compact.
 */
public class AmazonReviewSubscriptionHelper<T> {
	Config config;
	
	public AmazonReviewSubscriptionHelper(Config config) {
		this.config = config;
	}
	
	public Broker<T> instantiatesBroker() {
		Broker<T> broker;
		String brokerType = config.getBrokerType();
		if(brokerType.equals("AsyncOrdered")) {
			int blockingQueueSize = config.getBlockingQueueSize();
			int pollTimeout = config.getPollTimeout();
			broker = new AsyncOrderedDispatchBroker<T>(blockingQueueSize, pollTimeout);
		} else if(brokerType.equals("AsyncUnordered")) {
			int nThreads = config.getNThreads();
			broker = new AsyncUnorderedDispatchBroker<T>(nThreads);
		} else {
			brokerType = "SyncOrdered";
			broker = new SynchronousOrderedDispatchBroker<T>();
		}
		return broker;
	}
	
	public boolean runPublisherThreads(ReviewPublisher<T> p1, ReviewPublisher<T> p2) {
		Thread publisherThread1 = new Thread(p1);
		Thread publisherThread2 = new Thread(p2);
		publisherThread1.start();
		publisherThread2.start();
		try {
			publisherThread1.join();
			publisherThread2.join();
		} catch (InterruptedException e) {
			System.out.println("Please try again.");
			return false;
		}
		return true;
	}
}
