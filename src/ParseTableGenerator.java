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
		String[] production_strings = null;
		String[] symbol_strings = null;
		Symbol working_sym = null;
		Map<Symbol, List<Symbol>> value_map = null;

		while((line = reader.readLine()) != null) {
			line_number++;
			if(line.isEmpty()) continue; //skip blanks lines
			
			split_line = line.split(":",2); //only split into two
			if(split_line[0].equals("Type")) {
				type = split_line[1];
			}
			else if(split_line[0].equals("Production")) {
				production = new LinkedList<Symbol>();
				production_strings = split_line[1].split("\\|");
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
			}
			else if(split_line[0].equals("Symbol")) {
				value_map = new HashMap<Symbol, List<Symbol>>();
				symbol_strings = split_line[1].split("\\|");
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
				//populate the outer map
				parse_table.put(
						type, value_map
					);
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
