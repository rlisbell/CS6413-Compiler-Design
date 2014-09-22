import java.io.*;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.regex.*;

public class Scanner {
	
	//File to be scanned
	static Path filePath;
	//Character set to be used for IO
	static Charset charset;
	//IO buffer
	static BufferedReader reader;
	
	/**
	 * Drives the scanner
	 * @param String args - Path to file to be scanned
	 */
	public static void main(String[] args) {
		filePath = Paths.get(args[1]);
		charset = Charset.forName("UTF-8");
		reader = Files.newBufferedReader(filePath,charset);
		//should probably do this stuff in scan()
		char c;
		while ((c = (char) reader.read()) != -1) {
			scan(c);
			//do stuff with c
		}
	}
	
	private scan() {
	}
}