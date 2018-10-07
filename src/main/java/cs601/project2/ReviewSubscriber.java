package cs601.project2;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;

public class ReviewSubscriber implements Subscriber<Review> {
	// save to a file
	Config config;
	Path path1;
	Path path2;
	BufferedWriter writer1;
	BufferedWriter writer2;
	Charset cs;
	Gson gson;
	
	public ReviewSubscriber() throws IOException {
		config = new Config();
		config.setFileNames();
		path1 = Paths.get(config.getOutputFileName1());
		path2 = Paths.get(config.getOutputFileName2());
		cs = Charset.forName("ISO-8859-1");
		writer1 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config.getOutputFileName1()),cs));
		writer2 = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(config.getOutputFileName2()),cs));	
		gson = new Gson();
	}
	
	public void onEvent(Review review) {
		String reviewRecord = gson.toJson(review);
		if(review.getUnixReviewTime() <= 1362268800) {
//			writeToFile(review, "old");
			try {
				writer1.write(reviewRecord+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} else {
//			writeToFile(review, "new");
			try {
				writer2.write(reviewRecord+"\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void writeToFile(Review review, String type) {
		String reviewRecord = gson.toJson(review);
		if(type == "old") {
//			Syst	em.out.println(reviewRecord);
			try {
				writer1.write(reviewRecord);
				writer1.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}else if(type == "new") {
//			System.out.println(reviewRecord);
			try {
				writer2.write(reviewRecord);
				writer2.newLine();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
//		if(wr)
//		
////		config.setFileNames();
//		
////		Path path1 = Paths.get(config.getOutputFileName1());
////		Path path2 = Paths.get(config.getOutputFileName2());
//		try(
////			BufferedWriter writer1 = Files.newBufferedWriter(path1, cs);
////			BufferedWriter writer2 = Files.newBufferedWriter(path2, cs);
////			BufferedWriter writer1 = new BufferedWriter(new FileWriter(config.getOutputFileName1(), true));
////			BufferedWriter writer2 = new BufferedWriter(new FileWriter(config.getOutputFileName2(), true));
////			BufferedWriter writer1 = Files.newBufferedWriter(path1, cs, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
////			BufferedWriter writer2 = Files.newBufferedWriter(path2, cs, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
//		) {
//			Gson gson = new Gson();
//			try {
//				String reviewRecord = gson.toJson(review); // parse json to Review object
//				
//			} catch(JsonSyntaxException jse) {
//				// skip
//			}
//		}
//		catch(IOException ioe) {
////			System.out.println("Please try again with correct input.");
//			ioe.printStackTrace();
//		}
	}
}
