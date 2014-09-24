import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 
 */

/**
 * @author bobboau
 * class for the top level organization of the compiler
 *
 */
public class Compiler {
	
	Scanner scanner;

	/**
	 * @param String args - Paths to files to be scanned
	 */
	public static void main(String[] args) {
		for(String file_name : args){
			try {
				Compiler compiler = new Compiler(file_name);
				compiler.compile(file_name+"-compiled");
				//we can come up with something better than this I think
			} catch (IOException e) {
				System.out.println("could not open file "+file_name+" for reading");
				System.out.println(e.toString());
			} 
		}
	}
	
	private void compile(String out_file_name) {
		File file = new File(out_file_name);

	    try {
		    file.createNewFile();
			BufferedWriter writer = new BufferedWriter(new FileWriter(file));
			Token next_token;
			while((next_token = scanner.getNextToken()) != null){
				System.out.println(next_token.print());
				writer.write(next_token.print());
				writer.newLine();
			}
		} catch (IOException e) {
			System.out.println("could not open file "+out_file_name+" for writting");
			System.out.println(e.toString());
		}
	    
	}

	Compiler(String in_file_name) throws IOException{
		Path file_path = Paths.get(in_file_name);
		Charset charset = Charset.forName("UTF-8");
		BufferedReader reader = Files.newBufferedReader(file_path,charset);
		
		scanner = new Scanner(reader);
	}
}
