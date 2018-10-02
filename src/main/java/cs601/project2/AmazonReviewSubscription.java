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
		SynchronousOrderedDispatchBroker sodb = new SynchronousOrderedDispatchBroker();
		ReviewPublisher p1 = new ReviewPublisher(sodb, fileName1);
		ReviewPublisher p2 = new ReviewPublisher(sodb, fileName2);
		ReviewSubscriber s1 = new ReviewSubscriber();
		ReviewSubscriber s2 = new ReviewSubscriber();
//		AsyncOrderedDispatchBroker aodb = new AsyncOrderedDispatchBroker();
//		AsynUnorderedDispatchBroker audb = new AysncUnorderedDispatchBroker();
		//subscribe to broker
		sodb.subscribe(s1);
		sodb.subscribe(s2);
		//start timer
		
		Thread t1 = new Thread(p1);
		Thread t2 = new Thread(p2);
		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch (InterruptedException e) {
			System.out.println("Pleas try again.");
		}
		sodb.shutdown();
		System.out.println("Done");
	}

}
