import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.List;
import java.io.*;

/**
 * Reads simple parse table in from file and generates 
 * a hashmap from it
 */
public class ParseTableGenerator {
	
	/**
	 * Error that origonated in reading the parse table file
	 *
	 */
	public static class ParseTableException extends Exception {
		private static final long serialVersionUID = -4579432542470048977L;

		public ParseTableException(String message){
			super(message);
		}
	}
	
	/**
	 * Creates a hash map from the provided file
	 * @param file_path
	 * @return
	 * @throws ParseTableException
	 * @throws IOException 
	 * @throws ClassNotFoundException 
	 */
	public static Map<String, Map<Symbol, List<Symbol>>> generateHashMap(String file_path) throws ParseTableException, IOException {

		File file = new File(file_path);
		BufferedReader reader = new BufferedReader(new FileReader(file));

		//some variables for upkeep
		Map<String, Map<Symbol, List<Symbol>>> parse_table = new HashMap<String, Map<Symbol, List<Symbol>>>();
		String line = null;
		int line_number = 0;
		String type = null;
		String[] split_line = null;
		List<Symbol> production = null;

		while((line = reader.readLine()) != null) {
			line_number++;
			if(line.isEmpty()) continue; //skip blanks lines
			
			split_line = line.split(":",2); //only split into two
			if(split_line[0].equals("Type")) {
				type = split_line[1];
			}
			else if(split_line[0].equals("Production")) {
				production = loadProduction(reader, line_number, split_line[1]);
			}
			else if(split_line[0].equals("Symbol")) {
				loadSymbol(reader, parse_table, line_number, type, split_line[1], production);
			}
			else {
				reader.close();
				throw new ParseTableException("Bad line type on line "+line_number+": "+line);
			}
		}
		reader.close();
		return parse_table;
	}

	/**
	 * loads the Symbol portion of the Parse Table file
	 * @param reader source of the parse table file
	 * @param parse_table output table to load into
	 * @param line_number what line of the parse table file are we on
	 * @param type the production name we are on
	 * @param line_symbols the text of all of the different Symbols to break on for the current production
	 * @param production the current production
	 * @throws IOException
	 * @throws ParseTableException
	 */
	private static void loadSymbol(BufferedReader reader, Map<String, Map<Symbol, List<Symbol>>> parse_table, int line_number, String type, String line_symbols, List<Symbol> production)
		throws IOException, ParseTableException
	{
		String[] symbol_strings;
		Map<Symbol, List<Symbol>> value_map;
		Symbol working_sym = null;
		if(!parse_table.containsKey(type)) {
			parse_table.put(type, new HashMap<Symbol, List<Symbol>>());
		}
		value_map = parse_table.get(type);
		symbol_strings = line_symbols.split("\\|");
		for(String str : symbol_strings) {
			if(str.startsWith("LexemeTerminal")) {
				working_sym = createLexemeTerminal(extractArg(str));
			}
			else if(str.startsWith("AnySymbolOfClass")) {
				try {
					working_sym = createAnySymbolOfClass((Class<? extends Symbol>)Class.forName(extractArg(str)));
				} catch (ClassNotFoundException e) {
					reader.close();
					throw new ParseTableException("Bad Class '"+extractArg(str)+"' on line "+line_number+": "+str);
				}
			}
			else {
				reader.close();
				throw new ParseTableException("Bad symbol on line "+line_number+": "+str);
			}
			//populate the inner map
			value_map.put(working_sym, production);
		}
	}

	/**
	 * loads the production from the parse table definition file
	 * @param reader source of the parse table file
	 * @param line_number what line of the parse table file are we on
	 * @param line_symbols the text of all of the different Symbols to break on for the current production
	 * @return an array of symbols to replace the current non-terminal type with
	 * @throws IOException
	 * @throws ParseTableException
	 */
	private static List<Symbol> loadProduction(BufferedReader reader, int line_number, String line_symbols)
		throws IOException, ParseTableException
	{
		List<Symbol> production;
		String[] production_strings;
		Symbol working_sym = null;
		production = new LinkedList<Symbol>();
		production_strings = line_symbols.split("\\|");
		for(String str : production_strings) {
			if(str.startsWith("LexemeTerminal")) {
				working_sym = createLexemeTerminal(extractArg(str));
			}
			else if(str.startsWith("NonTerminal")) {
				working_sym = createNonTerminal(extractArg(str));
			}
			else if(str.startsWith("AnySymbolOfClass")) {
				try {
					working_sym = createAnySymbolOfClass((Class<? extends Symbol>)Class.forName(extractArg(str)));
				} catch (ClassNotFoundException e) {
					reader.close();
					throw new ParseTableException("Bad Class '"+extractArg(str)+"' on line "+line_number+": "+str);
				}
			}
			else if(str.isEmpty()) {
				continue;
			}
			else {
				reader.close();
				throw new ParseTableException("Bad production on line "+line_number+": "+str);
			}
			production.add(working_sym);
		}
		//the parsing stack wants this in reverse order
		Collections.reverse(production);
		return production;
	}
	
	/**
	 * Pulls the argument out of a statement
	 * @param str
	 * @return
	 */
	private static String extractArg(String str) {
		return str.substring(str.indexOf('(')+1,str.lastIndexOf(')'));
	}
	
	/**
	 * LexemeTerminal factory
	 * @param str
	 * @return
	 */
	private static LexemeTerminal createLexemeTerminal(String str) {
		return new LexemeTerminal(str.substring(1,str.length()-1));
	}
	
	/**
	 * NonTerminal factory
	 * @param str
	 * @return
	 */
	private static NonTerminal createNonTerminal(String str) {
		return new NonTerminal(str);
	}
	
	/**
	 * AnySymbolOfClass factory
	 * @param sym 
	 * @return
	 */
	private static AnySymbolOfClass createAnySymbolOfClass(Class<? extends Symbol> sym) {
		return new AnySymbolOfClass(sym);
	}
}
