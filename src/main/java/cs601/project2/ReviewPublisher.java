package cs601.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

/**
 * 
 * @author pontakornp
 *
 * Review Publisher class is used for publisher/ subscriber design.
 * 
 * Works as a thread to send review items to broker to be sent to current subscribers
 * 
 */
public class ReviewPublisher implements Runnable{
	private Broker<Review> broker;
	private boolean running;
	private Charset cs;
	private Path path;
	
	public ReviewPublisher(Broker<Review> broker, String fileName) {
		this.broker = broker;
		this.running = true;
		this.cs = Charset.forName("ISO-8859-1");
		this.path = Paths.get(fileName);
	}
	
	@Override
	public void run() {
		while(running) {
			try(
				BufferedReader reader = Files.newBufferedReader(path, cs);
			) {
				String line;
				Gson gson = new Gson();
				while((line = reader.readLine()) != null) {
					try {
						Review review = gson.fromJson(line, Review.class); // parse json to Review object
						broker.publish(review);
					} catch(JsonSyntaxException jse) {
						// skip
					}
				}
			}
			catch(IOException ioe) {
				System.out.println("Please try again with correct input files.");
			}
			running = false;
		}
	}
}