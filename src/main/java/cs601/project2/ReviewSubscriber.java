package cs601.project2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class ReviewSubscriber implements Subscriber<Review> {
	Config config = new Config();
	// save to a file
	public void onEvent(Review review) {
		
		if(review.getUnixReviewTime() <= 1362268800) {
			writeFile(review, "old");
		} else {
			writeFile(review, "new");
		}
	}

	private void writeFile(Review review, String type) {
		config.setFileNames();
		Charset cs = Charset.forName("ISO-8859-1");
		Path path1 = Paths.get(config.getOutputFileName1());
		Path path2 = Paths.get(config.getOutputFileName2());
		try(
			BufferedWriter writer1 = Files.newBufferedWriter(path1, cs);
			BufferedWriter writer2 = Files.newBufferedWriter(path2, cs);
		) {
			Gson gson = new Gson();
			try {
				String reviewRecord = gson.toJson(review); // parse json to Review object
				if(type == "old") {
					writer1.append(reviewRecord);
				}else if(type == "new") {
					writer2.append(reviewRecord);
				}
			} catch(JsonSyntaxException jse) {
				// skip
			}
		}
		catch(IOException ioe) {
			System.out.println("Please try again with correct input.");
		}
	}
}
