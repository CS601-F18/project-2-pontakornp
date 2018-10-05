package cs601.project2;

public class AmazonReviewSubscription {

	public static void main(String[] args) {
//		if(args.length != 4) {
//			System.out.println("Incorrect input format.\n"
//				+ "Please try again.");
//			return;
//		}
		
		SynchronousOrderedDispatchBroker broker = new SynchronousOrderedDispatchBroker();
//		AsyncOrderedDispatchBroker broker = new AsyncOrderedDispatchBroker();
//		AsynUnorderedDispatchBroker broker = new AysncUnorderedDispatchBroker();
		Config config = new Config();
		config.setFileNames();
//		fileName1 = config.getInputFileName1();
//		fileName2 = config.getInputFileName2();
//		String fileName1 = "home_kitchen_sample.json";
//		String fileName2 = "app_android_sample.json";
		String fileName1 = "reviews_Home_and_Kitchen_5.json";
		String fileName2 = "reviews_Apps_for_Android_5.json";
		ReviewPublisher p1 = new ReviewPublisher(broker, fileName1);
		ReviewPublisher p2 = new ReviewPublisher(broker, fileName2);
		ReviewSubscriber s1 = new ReviewSubscriber();
		ReviewSubscriber s2 = new ReviewSubscriber();

		//subscribe to broker
		broker.subscribe(s1);
		broker.subscribe(s2);
		//start timer
		long start = System.currentTimeMillis();
		;
		Thread publisherThread1 = new Thread(p1);
		Thread publisherThread2 = new Thread(p2);
//		Thread brokerThread = new Thread(broker);
//		brokerThread.start();
		publisherThread1.start();
		publisherThread2.start();
		try {
			publisherThread1.join();
			publisherThread2.join();
//			broker.shutdown();
//			brokerThread.join();
		} catch (InterruptedException e) {
			System.out.println("Please try again.");
		}
		//stop timer
		long end = System.currentTimeMillis();
		System.out.println("Time: " + Math.round((end-start) / 1000) + " seconds" );
		System.out.println("Done");
	}

}
