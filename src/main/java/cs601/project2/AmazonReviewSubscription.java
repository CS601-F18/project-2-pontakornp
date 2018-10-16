package cs601.project2;

import java.io.IOException;

public class AmazonReviewSubscription {

	public static void main(String[] args) throws IOException{
		
		Config config = new Config();
		config.setVariables();
		String inputFileName1 = config.getInputFileName1();
		String inputFileName2 = config.getInputFileName2();
		String outputFileName1 = config.getOutputFileName1();
		String outputFileName2 = config.getOutputFileName2();
//		String outputFileName1 = "new_reviews2.json";
//		String outputFileName2 = "old_reviews2.json";
//		String inputFileName1 = "home_kitchen_sample.json";
//		String inputFileName2 = "app_android_sample.json";
		
		int blockingQueueSize = config.getBlockingQueueSize();
		int pollTimeout = config.getPollTimeout();
		int nThreads = config.getNThreads();
		//broker
//		SynchronousOrderedDispatchBroker broker = new SynchronousOrderedDispatchBroker();
//		AsyncOrderedDispatchBroker broker = new AsyncOrderedDispatchBroker(blockingQueueSize, pollTimeout);
		AsyncUnorderedDispatchBroker broker = new AsyncUnorderedDispatchBroker(nThreads);
		
		ReviewPublisher p1 = new ReviewPublisher(broker, inputFileName1);
		ReviewPublisher p2 = new ReviewPublisher(broker, inputFileName2);
		ReviewSubscriber s1 = new ReviewSubscriber(outputFileName1, "new");
		ReviewSubscriber s2 = new ReviewSubscriber(outputFileName2, "old");
		//subscribe to broker
		broker.subscribe(s1);
		broker.subscribe(s2);
		
		//start timer
		long start = System.currentTimeMillis();
		
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
		
		//stop timer
		long end = System.currentTimeMillis();
		
		System.out.println("Time: " + Math.round((end-start) / 1000) + " seconds" );
		System.out.println("Time: " + (end-start) + " miliseconds" );
		System.out.println("Done");
	}

}
