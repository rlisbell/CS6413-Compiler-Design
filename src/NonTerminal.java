import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Represents a NonTerminal
 * Handles productions logic
 */
public class NonTerminal extends Symbol {
	
	//parse table to generate productions
	static Map<String ,Map<Symbol, List<Symbol>>> parse_table = new HashMap<String, Map<Symbol, List<Symbol>>>();
	
	//name / type of this nonterminal
	private String type;
	
	/**
	 * constructor
	 * @param type
	 */
	public NonTerminal(String type) {
		this.type = type;
	}
	
	/**
	 * Load in the parse table;
	 * @param file
	 * @throws IOException 
	 * @throws ParseTableException 
	 * @throws ClassNotFoundException 
	 * @throws Exception
	 */
	public static void loadParseTable(String file) throws ClassNotFoundException, ParseTableGenerator.ParseTableException, IOException {
		parse_table = ParseTableGenerator.generateHashMap(file);
	}

	/**
	 * Returns a list of symbols representing a production
	 * @param next_token
	 * @return
	 * @throws UnexpectedTokenException
	 */
	public List<Symbol> getProduction(Token next_token) throws UnexpectedTokenException {
		if(!parse_table.containsKey(this.type)) {
			throw new UnexpectedTokenException(next_token);
		}
		if(!parse_table.get(this.type).containsKey(next_token.getTerminal())) {
			throw new UnexpectedTokenException(next_token);
		}
		return parse_table.get(this.type).get(next_token.getTerminal());
	}
	
	/**
	 * prints terminal type
	 */
	public String print() {
		//30 column aligned for prettification
		return String.format("%-30s", type);
	}

	/**
	 * equivalence
	 */
	@Override
	public boolean equals(Symbol other) {
		if(other.getClass().equals(getClass())){
			 return ((NonTerminal)other).type.equals(type);
		}
		else{
			return false;
		}
	}

}
