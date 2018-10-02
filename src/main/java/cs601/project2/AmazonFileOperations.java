package cs601.project2;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class AmazonFileOperations {

	/**
	 * Checks whether command line input is valid
	 * 
	 * @param args - expects arguments from the command line
	 * @return true or false
	 */
	public boolean isInputValid(String[] args) {
		if (args[0] == "" || !isFileExist(args[0])) {
			String msg = "Incorrect Review file name.\n";
			msg += "Please try again.";
			System.out.println(msg);
			return false;
		}
		return true;
	}
	
	/**
	 * 
	 * Reads from a file in the project directory given the file name, and return void
	 * @param fileName - expects the name of the input file
	 */
	public void readFile(String fileName) {
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
					
				} catch(JsonSyntaxException jse) {
					// skip
				}
			}
		}
		catch(IOException ioe) {
			System.out.println("Please try again with correct input.");
		}
	}
	
	public void writeFile(String fileName, String input) {
		
	}
	
	/**
	 * 
	 * helper method - check whether file exists or not from the filename
	 * @param filename
	 * @return
	 */
	private boolean isFileExist(String fileName) {
		if(!(new File(fileName).exists())) {
			return false;
		}
		return true;
	}

}
