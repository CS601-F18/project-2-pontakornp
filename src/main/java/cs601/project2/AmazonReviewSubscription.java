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
		AmazonReviewSubscriptionHelper<Review> helper = new AmazonReviewSubscriptionHelper<Review>(config);
		long separatedUnixReviewTime = config.getSeparatedUnixReviewTime();
		// instantiates broker
		Broker<Review> broker = helper.instantiatesBroker();
		// instantiates publishers and subscribers
		ReviewPublisher<Review> p1 = new ReviewPublisher<Review>(broker, Review.class, config.getInputFileName1());
		ReviewPublisher<Review> p2 = new ReviewPublisher<Review>(broker, Review.class, config.getInputFileName2());
		ReviewSubscriber<Review> s1 = new ReviewSubscriber<Review>(separatedUnixReviewTime, config.getOutputFileName1());
		ReviewSubscriber<Review> s2 = new ReviewSubscriber<Review>(separatedUnixReviewTime, config.getOutputFileName2());
		// subscribers subscribe
		broker.subscribe(s1);
		broker.subscribe(s2);
		// starts timer
		long start = System.currentTimeMillis();
		// run publishers threads
		boolean areThreadsFinishRunning = helper.runPublisherThreads(p1, p2);
		if(!areThreadsFinishRunning) {
			return;
		}
		broker.shutdown();
		s1.shutdown();
		s2.shutdown();
		// stops timer
		long end = System.currentTimeMillis();
		System.out.println("Broker Type: " + config.getBrokerType());
		System.out.println("Runtime: " + (end-start) + " milliseconds" );
	}
}