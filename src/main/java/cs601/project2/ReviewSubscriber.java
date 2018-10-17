package cs601.project2;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

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
	private String type;
	private Path path;
	private Charset cs;
	private Gson gson;
	private BufferedWriter writer;
	
	public ReviewSubscriber(long separatedUnixReviewTime, String fileName, String type) {
		this.separatedUnixReviewTime = separatedUnixReviewTime;
		this.fileName = fileName;
		this.type = type;
		this.path = Paths.get(fileName);
		this.cs = Charset.forName("ISO-8859-1");
		this.gson = new Gson();
		try {
			this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),cs));
		} catch (IOException e) {
			System.out.println("There is an issue with an output file. Please try again.");
		}
	}
	
	/**
	 * Writes review items by separating them into two files by unix review time specified in the config.
	 */
	@Override
	public void onEvent(Review review) {
		String reviewRecord = gson.toJson(review);
		if((review.getUnixReviewTime() > separatedUnixReviewTime && type == "new") || 
			(review.getUnixReviewTime() <= separatedUnixReviewTime && type == "old")) {
			try {
				writer.write(reviewRecord+"\n");
			} catch (IOException e) {
				System.out.print("There is an issue when writing to file. Please try again.");
			}
		}
	}
	
	/**
	 * Closes buffered writer
	 */
	public void shutdown() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("There is an issue when closing the file. Please try again.");
		}
	}
}