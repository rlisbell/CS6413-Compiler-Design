import java.io.PushbackReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Stack;
import java.util.List;


/**
 * @author Mike, Ryan
 * class for the top level organization of the compiler
 */
public class Compiler {
	
	/**********************\
	|* private properties *|
	\**********************/
	
	/**
	 * Yanks lexemes from file and converts to tokens
	 */
	private Scanner scanner;
	
	/**
	 * given a lexeme finds or creates an appropriate symbol
	 */
	private SymbolTable symbol_table;
	
	/****************\
	|* constructors *| 
	\****************/
	
	/**
	 * constructor
	 * @param in_file_name source file path
	 * @throws IOException
	 */
	Compiler(String in_file_name) throws IOException{
		Path file_path = Paths.get(in_file_name);
		Charset charset = Charset.forName("UTF-8");
		//PushbackReader allows us to unread characters easily and semantically
		PushbackReader reader = new PushbackReader(Files.newBufferedReader(file_path,charset));
		
		scanner = new Scanner(reader);
		symbol_table = SymbolTable.initializeSymbolTable();
	}

	
	/******************\
	|* public methods *| 
	\******************/
	
	/**
	 * main entry point of the application
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
			} catch (Token.TokenException|LexemeTerminal.LexemeTerminalException e) {
				System.out.println("error in "+file_name);
				System.out.println(e.toString());
			} catch (Symbol.UnexpectedTokenException e) {
				System.out.println("error in "+file_name);
				System.out.println(e.toString());
			} catch (ClassNotFoundException e) {
				System.out.println("error in "+file_name);
				System.out.println(e.toString());
			} catch (ParseTableGenerator.ParseTableException e) {
				System.out.println("error in "+file_name);
				System.out.println(e.toString());
			}
		}
	}
	
	/**
	 * process a file and output the result to the file identified in the parameter
	 * @param out_file_name destination path of output file
	 * @throws IOException
	 * @throws Token.TokenException 
	 * @throws LexemeTerminal.LexemeTerminalException 
	 * @throws ParseTableGenerator.:wParseTableException 
	 * @throws ClassNotFoundException 
	 */
	private void compile(String out_file_name) throws IOException, Token.TokenException, LexemeTerminal.LexemeTerminalException, Symbol.UnexpectedTokenException, ClassNotFoundException, ParseTableGenerator.ParseTableException {
		File file = new File(out_file_name);
		BufferedWriter writer = null;

	    try {
		    file.createNewFile();
			writer = new BufferedWriter(new FileWriter(file));
			NonTerminal.loadParseTable("resources/parse_table.tbl");

			//Parse through the program
			Token token;
			Symbol symbol;
			List<Symbol> production; //is this the correct type?
			Stack<Symbol> stack = new Stack<Symbol>(); //is this the correct type?
			stack.push(new NonTerminal("Program"));
			token = scanner.getNextToken(symbol_table);	
			while(!stack.empty()) {
				symbol = stack.pop();
				System.out.println(symbol.print());
				writer.write(symbol.print());
				writer.newLine();
				production = symbol.getProduction(token);
				for(Symbol production_sym : production) { //for Symbol or...?
					stack.push(production_sym);
				}
				if(symbol.shouldGetToken() ) { //is this the correct instanceof?
					token = scanner.getNextToken(symbol_table);
				}
			}
		} catch (IOException e) {
			System.out.println("could not open file "+out_file_name+" for writing");
			System.out.println(e.toString());
		}  catch (Scanner.ScannerException e) {
			System.out.println("Syntax Error");
			System.out.println(e.toString());
		} finally {
			writer.close();
		}
	}
}
