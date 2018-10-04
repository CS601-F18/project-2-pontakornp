package cs601.project2;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class Config {
	private String inputFileName1;
	private String inputFileName2;
	private String outputFileName1;
	private String outputFileName2;

	public Config() {
		inputFileName1 = "";
		inputFileName2 = "";
		outputFileName1 = "";
		outputFileName2 = "";
//		setFileNames();
	}
	
	public Config(String inputFileName1, String inputFileName2, String outputFileName1, String outputFileName2) {
		this.inputFileName1 = inputFileName1;
		this.inputFileName2 = inputFileName2;
		this.outputFileName1 = outputFileName1;
		this.outputFileName2 = outputFileName2;
	}
	
	public void setInputFileName1(String inputFileName1) {
		this.inputFileName1 = inputFileName1;
	}
	
	public String getInputFileName1() {
		return this.inputFileName1;
	}
	
	public void setInputFileName2(String inputFileName2) {
		this.inputFileName2 = inputFileName2;
	}
	
	public String setInputFileName2() {
		return this.inputFileName2;
	}
	
	public void setOutputFileName1(String outputFileName1) {
		this.outputFileName1 = outputFileName1;
	}
	
	public String getOutputFileName1() {
		return this.outputFileName1;
	}
	
	public void setOutputFileName2(String outputFileName2) {
		this.outputFileName2 = outputFileName2;
	}
	
	public String getOutputFileName2() {
		return this.outputFileName2;
	}

	public void setFileNames() {
		Charset cs = Charset.forName("ISO-8859-1");
		Path path = Paths.get("config.json");
		Config fileNames = new Config();
		try(
			BufferedReader reader = Files.newBufferedReader(path, cs);
		) {
			String line;
			Gson gson = new Gson();
			while((line = reader.readLine()) != null) {
				try {
					fileNames = gson.fromJson(line, Config.class); // parse filenames from config file to config object
					this.inputFileName1 = fileNames.inputFileName1;
					this.inputFileName2 = fileNames.inputFileName2;
					this.outputFileName1 = fileNames.outputFileName1;
					this.outputFileName2 = fileNames.outputFileName2;
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
