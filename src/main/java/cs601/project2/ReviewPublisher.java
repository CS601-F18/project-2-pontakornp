package cs601.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ReviewPublisher implements Runnable{
	private Broker<Review> broker;
	private String fileName;
	private boolean running;
	
	public ReviewPublisher(Broker<Review> broker, String fileName) {
		this.broker = broker;
		this.fileName = fileName;
		this.running = true;
	}
	
	/**
	 * 
	 * Reads from a file in the project directory given the file name, and return void
	 * @param fileName - expects the name of the input file
	 */
	public void run() {
		//call blocker publish
		while(running) {
			synchronized(this) {
				//check if msg to be publisher, if not keep blocking until the checker been notify
				Charset cs = Charset.forName("ISO-8859-1");
				Path path = Paths.get(fileName);
				try(
					BufferedReader reader = Files.newBufferedReader(path, cs);
				) {
					String line;
					Gson gson = new Gson();
					while((line = reader.readLine()) != null) {
						try {
							Review review = gson.fromJson(line, Review.class); // parse json to Review object
							
	//						synchronized(this) {
								broker.publish(review);
	//						}
							
						} catch(JsonSyntaxException jse) {
							// skip
						}
					}
				}
				catch(IOException ioe) {
					System.out.println("Please try again with correct input.");
				}
			}
			//if broker is shutdown
			// publisher = false;
			running = false;
		}
	}
	
	public void stop() {
		running = false;
	}
	
	

}
