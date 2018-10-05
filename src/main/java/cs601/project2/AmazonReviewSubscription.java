package cs601.project2;

public class AmazonReviewSubscription {

	public static void main(String[] args) {
//		if(args.length != 4) {
//			System.out.println("Incorrect input format.\n"
//				+ "Please try again.");
//			return;
//		}
		String fileName1 = "home_kitchen_sample.json";
		String fileName2 = "app_android_sample.json";
//		SynchronousOrderedDispatchBroker broker = new SynchronousOrderedDispatchBroker();
		AsyncOrderedDispatchBroker broker = new AsyncOrderedDispatchBroker();
//		AsynUnorderedDispatchBroker broker = new AysncUnorderedDispatchBroker();
		Config config = new Config();
		config.setFileNames();
		fileName1 = config.getInputFileName1();
		fileName2 = config.getInputFileName2();
		ReviewPublisher p1 = new ReviewPublisher(broker, fileName1);
		ReviewPublisher p2 = new ReviewPublisher(broker, fileName2);
		ReviewSubscriber s1 = new ReviewSubscriber();
		ReviewSubscriber s2 = new ReviewSubscriber();

		//subscribe to broker
		broker.subscribe(s1);
		broker.subscribe(s2);
		//start timer
		
		Thread publisherThread1 = new Thread(p1);
		Thread publisherThread2 = new Thread(p2);
		Thread aodbThread = new Thread(broker);
		aodbThread.start();
		publisherThread1.start();
		publisherThread2.start();
		try {
			publisherThread1.join();
			publisherThread2.join();
		} catch (InterruptedException e) {
			System.out.println("Pleas try again.");
		}
		broker.shutdown();
		System.out.println("Done");
	}

}
