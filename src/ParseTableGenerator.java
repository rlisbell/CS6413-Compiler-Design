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
	public Map<String, Map<Symbol, List<Symbol>>> generateHashMap(String file_path) throws Exception {
		Map<String, Map<Symbol, List<Symbol>>> parse_table = new HashMap<String, Map<Symbol, List<Symbol>>>();
		File file = new File(file_path);
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		String type = null;
		String[] split_line = null;
		List<Symbol> production = new LinkedList<Symbol>();
		String[] production_strings = null;
		String[] symbol_strings = null;
		Symbol working_sym = null;
		Map<Symbol, List<Symbol>> value_map = new HashMap<Symbol, List<Symbol>>();
		while((line = reader.readLine()) != null) {
			if(line.isEmpty()) continue; //skip blanks lines
			
			split_line = line.split(":",2);
			if(split_line[0].equals("Type")) {
				type = split_line[1];
			}
			else if(split_line[0].equals("Program")) {
				production.clear();
				production_strings = split_line[1].split("|");
				for(String str : production_strings) {
					if(str.startsWith("LexemeTerminal")) {
						working_sym = this.createLexemeTerminal(this.extractArg(str));
					}
					else if(str.startsWith("NonTerminal")) {
						working_sym = this.createNonTerminal(this.extractArg(str));
					}
					else if(str.startsWith("AnySymbolOfClass")) {
						Class<? extends Symbol> forName = (Class<? extends Symbol>)Class.forName(this.extractArg(str));
						working_sym = this.createAnySymbolOfClass(forName);
					}
					else {
						reader.close();
						throw new Exception("Bad production: "+str);
					}
					production.add(working_sym);
				}
			}
			else if(split_line[0].equals("Symbol")) {
				value_map.clear();
				symbol_strings = split_line[1].split("|");
				for(String str : symbol_strings) {
					if(str.startsWith("LexemeTerminal")) {
						working_sym = this.createLexemeTerminal(this.extractArg(str));
					}
					else if(str.startsWith("AnySymbolOfClass")) {
						working_sym = this.createAnySymbolOfClass((Class<? extends Symbol>)Class.forName(this.extractArg(str)));
					}
					else {
						reader.close();
						throw new Exception("Bad production: "+str);
					}
					value_map.put(working_sym, production);
				}
				parse_table.put(
						type, value_map
					);
			}
			else {
				reader.close();
				throw new Exception("Bad line type: "+line);
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
	private String extractArg(String str) {
		return str.substring(str.indexOf('('),str.lastIndexOf(')'));
	}
	
	/**
	 * LexemeTerminal factory
	 * @param str
	 * @return
	 */
	private LexemeTerminal createLexemeTerminal(String str) {
		return new LexemeTerminal(str);
	}
	
	/**
	 * NonTerminal factory
	 * @param str
	 * @return
	 */
	private NonTerminal createNonTerminal(String str) {
		return new NonTerminal(str);
	}
	
	/**
	 * AnySymbolOfClass factory
	 * @param sym 
	 * @return
	 */
	private AnySymbolOfClass createAnySymbolOfClass(Class<? extends Symbol> sym) {
		return new AnySymbolOfClass(sym);
	}
}
