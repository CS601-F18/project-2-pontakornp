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

public class ReviewSubscriber implements Subscriber<Review> {
	private String fileName;
	private String type;
	private Path path;
	private Charset cs;
	private Gson gson;
	private BufferedWriter writer;
	
	public ReviewSubscriber(String fileName, String type) throws IOException, FileNotFoundException{
		this.fileName = fileName;
		this.type = type;
		this.path = Paths.get(fileName);
		this.cs = Charset.forName("ISO-8859-1");
		this.gson = new Gson();
		this.writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName),cs));
	}
	
	public void onEvent(Review review) {
		String reviewRecord = gson.toJson(review);
		if((review.getUnixReviewTime() > 1362268800 && type == "new") || 
			(review.getUnixReviewTime() <= 1362268800 && type == "old")) {
			try {
				writer.write(reviewRecord+"\n");
			} catch (IOException e) {
				System.out.print("There is an issue when writing to file. Please try again.");
			}
		}
	}

	public void shutdown() {
		try {
			writer.close();
		} catch (IOException e) {
			System.out.println("Cannot close file.");
		}
	}
}