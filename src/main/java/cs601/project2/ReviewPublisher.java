package cs601.project2;

public class ReviewPublisher implements Runnable{
	private boolean publishing;
	
	public ReviewPublisher() {
		this.publishing = true;
	}
	
	public void run() {
		//call blocker publish

		while(publishing) {
			
		}
	}
	
	public void stop() {
		publishing = false;
	}
	
	
}
