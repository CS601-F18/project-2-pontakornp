package cs601.project2;

import java.io.IOException;

/**
 * 
 * @author pontakornp
 *
 * Main class for Project 2 in CS601 Principles of Software Development class at USF.
 * Uses Publishers/ Subscribers design to implement logics to separate old and new Amazon reviews files.
 * 
 * Includes the use of 3 different kinds of broker (SyncOrdered, AsyncOrdered, and AsyncUnordered) 
 * as in intermediary between publishers and subscribers.
 * 
 * Only the broker knows who the publisher or subscriber is, but publisher and subscriber do not know each other.
 * Implements 2 publisher and 2 subscribers according to project specifications.
 * 
 * There are fields that must be specified in config.json file:
 * 1. brokerType - SyncOrdered, AsyncOrderd, or AsyncUnordered. SyncOrdered is set as default type.
 * 2. input/ output filenames - 2 input and 2 output filenames.
 * 3. blockingQueueSize - size of blocking queue used in AsyncOrdered
 * 4. pollTimeout - timeout of poll method of blocking queue class used in AsyncOrdered
 * 5. nThreads - number of Threads used in AsyncUnordered
 * 
 */
public class AmazonReviewSubscription {
	public static void main(String[] args) throws IOException{
		// instantiates config
		Config config = new Config();
		if(!config.setVariables()) {
			return;
		}
		String brokerType = config.getBrokerType();
		String inputFileName1 = config.getInputFileName1();
		String inputFileName2 = config.getInputFileName2();
		String outputFileName1 = config.getOutputFileName1();
		String outputFileName2 = config.getOutputFileName2();
		long separatedUnixReviewTime = config.getSeparatedUnixReviewTime();
		int blockingQueueSize = config.getBlockingQueueSize();
		int pollTimeout = config.getPollTimeout();
		int nThreads = config.getNThreads();
		// instantiates broker
		Broker<Review> broker;
		if(brokerType.equals("AsyncOrdered")) {
			broker = new AsyncOrderedDispatchBroker(blockingQueueSize, pollTimeout);
		} else if(brokerType.equals("AsyncUnordered")) {
			broker = new AsyncUnorderedDispatchBroker(nThreads);
		} else {
			brokerType = "SyncOrdered";
			broker = new SynchronousOrderedDispatchBroker();
		}
		// instantiates publishers and subscribers
		ReviewPublisher p1 = new ReviewPublisher(broker, inputFileName1);
		ReviewPublisher p2 = new ReviewPublisher(broker, inputFileName2);
		ReviewSubscriber s1 = new ReviewSubscriber(separatedUnixReviewTime, outputFileName1);
		ReviewSubscriber s2 = new ReviewSubscriber(separatedUnixReviewTime, outputFileName2);
		// subscribers subscribe
		broker.subscribe(s1);
		broker.subscribe(s2);
		// starts timer
		long start = System.currentTimeMillis();
		// run publishers threads
		Thread publisherThread1 = new Thread(p1);
		Thread publisherThread2 = new Thread(p2);
		publisherThread1.start();
		publisherThread2.start();
		try {
			publisherThread1.join();
			publisherThread2.join();
			broker.shutdown();
			s1.shutdown();
			s2.shutdown();
		} catch (InterruptedException e) {
			System.out.println("Please try again.");
		}
		// stops timer
		long end = System.currentTimeMillis();
		System.out.println("Broker Type: " + brokerType);
		System.out.println("Runtime: " + (end-start) + " milliseconds" );
	}
}