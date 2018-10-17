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
 * Serves as the config class that read from config.json file
 * Contains variables that helps neglect hard coding in the implementations.
 *
 */
public class Config {
	private String inputFileName1;
	private String inputFileName2;
	private String outputFileName1;
	private String outputFileName2;
	private long separatedUnixReviewTime;
	private int blockingQueueSize;
	private int pollTimeout;
	private int nThreads;
	
	public void setInputFileName1(String inputFileName1) {
		this.inputFileName1 = inputFileName1;
	}
	
	public String getInputFileName1() {
		return inputFileName1;
	}
	
	public void setInputFileName2(String inputFileName2) {
		this.inputFileName2 = inputFileName2;
	}
	
	public String getInputFileName2() {
		return inputFileName2;
	}
	
	public void setOutputFileName1(String outputFileName1) {
		this.outputFileName1 = outputFileName1;
	}
	
	public String getOutputFileName1() {
		return outputFileName1;
	}
	
	public void setOutputFileName2(String outputFileName2) {
		this.outputFileName2 = outputFileName2;
	}
	
	public String getOutputFileName2() {
		return outputFileName2;
	}
	
	public void setSeparatedUnixReviewTime(long separatedUnixReviewTime) {
		this.separatedUnixReviewTime = separatedUnixReviewTime;
	}
	
	public long getSeparatedUnixReviewTime() {
		return separatedUnixReviewTime;
	}
	
	public void setBlockingQueueSize(int blockingQueueSize) {
		this.blockingQueueSize = blockingQueueSize;
	}
	
	public int getBlockingQueueSize() {
		return blockingQueueSize;
	}
	
	public void setPollTimeout(int pollTimeout) {
		this.pollTimeout = pollTimeout;
	}
	
	public int getPollTimeout() {
		return pollTimeout;
	}
	
	public void setNThreads(int nThreads) {
		this.nThreads = nThreads;
	}
	
	public int getNThreads() {
		return nThreads;
	}

	/**
	 * Set variables from the config.json file and assign them to variables in this class.
	 */
	public void setVariables() {
		Charset cs = Charset.forName("ISO-8859-1");
		Path path = Paths.get("config.json");
		Config config = new Config();
		try(
			BufferedReader reader = Files.newBufferedReader(path, cs);
		) {
			String line;
			Gson gson = new Gson();
			while((line = reader.readLine()) != null) {
				try {
					config = gson.fromJson(line, Config.class); // parse filenames from config file to config object
					this.inputFileName1 = config.inputFileName1;
					this.inputFileName2 = config.inputFileName2;
					this.outputFileName1 = config.outputFileName1;
					this.outputFileName2 = config.outputFileName2;
					this.separatedUnixReviewTime = config.separatedUnixReviewTime;
					this.blockingQueueSize = config.blockingQueueSize;
					this.pollTimeout = config.pollTimeout;
					this.nThreads = config.nThreads;
				} catch(JsonSyntaxException jse) {
					// skip
				}
			}
		}
		catch(IOException ioe) {
			System.out.println("Please try again with correct config file.");
		}
	}
}
