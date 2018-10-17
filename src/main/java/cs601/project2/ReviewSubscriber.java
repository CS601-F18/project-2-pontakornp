package cs601.project2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;

import com.google.gson.Gson;

/**
 * 
 * @author pontakornp
 *
 * Review Subscriber class is used in publisher/ subscriber design pattern.
 * 
 * Contains method onEvent that write items to file and shutdown to close the buffered writer.
 *
 */
public class ReviewSubscriber implements Subscriber<Review> {
	private long separatedUnixReviewTime;
	private String fileName;
	private Charset cs;
	private Gson gson;
	private BufferedWriter writer;
	private Config config;
	private String outputFileName1;
	private String outputFileName2;
	
	public ReviewSubscriber(long separatedUnixReviewTime, String fileName) {
		this.separatedUnixReviewTime = separatedUnixReviewTime;
		this.fileName = fileName;
		this.cs = Charset.forName("ISO-8859-1");
		this.gson = new Gson();
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),cs));
		} catch (IOException e) {
			System.out.println("Please try again with correct output file name.");
		}
		this.config = new Config();
		this.config.setVariables();
		this.outputFileName1 = config.getOutputFileName1();
		this.outputFileName2 = config.getOutputFileName2();
	}
	
	/**
	 * Writes review items by separating them into two files by unix review time specified in the config file.
	 */
	@Override
	public void onEvent(Review review) {
		String reviewRecord = gson.toJson(review);
		if((review.getUnixReviewTime() > separatedUnixReviewTime && fileName.equals(outputFileName1)) || 
			(review.getUnixReviewTime() <= separatedUnixReviewTime && fileName.equals(outputFileName2))) {
			try {
				writer.write(reviewRecord+"\n");
			} catch (IOException e) {
				System.out.print("There is an issue when writing to file. Please try again.");
			}
		}
	}
	
	/**
	 * Closes buffered writer.
	 */
	public void shutdown() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("There is an issue when closing the file. Please try again.");
		}
	}
}