package cs601.project2;

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


	public synchronized boolean isEmpty() {
		return size == 0;
	}
}


// keep taking when q empty then block if queue not empty continue

// somethign to notify waiting item, and waiting 

// taker is waiting cuz q is empty

//
//while run true{
//	if q is empty
//	taker wait
//	else
//	take
//}
//
//notify it when broker put item in queue