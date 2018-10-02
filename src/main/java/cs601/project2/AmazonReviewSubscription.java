package cs601.project2;

public class AmazonReviewSubscription {

	public static void main(String[] args) {
		if(args.length != 4) {
			System.out.println("Incorrect input format.\n"
				+ "Please try again.");
			return;
		}
		ReviewPublisher publisher = new ReviewPublisher();
		ReviewSubscriber subscriber = new ReviewSubscriber();
		Thread p1 = new Thread(publisher);
		SynchronousOrderedDispatchBroker sodb = new SynchronousOrderedDispatchBroker();
//		AsyncOrderedDispatchBroker aodb = new AsyncOrderedDispatchBroker();
//		AsynUnorderedDispatchBroker audb = new AysncUnorderedDispatchBroker();
		//subscribe to broker
		//start timer
		p1.start();
	}

}
