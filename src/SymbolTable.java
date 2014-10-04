import java.util.Hashtable;

/**
 * Represents the symbol table
 * @author Mike, Ryan
 */
public class SymbolTable extends Hashtable<String, Symbol> {

	/**
	 * For Hashtable throws
	 */
	private static final long serialVersionUID = -2427108052657869701L;
	
	/**
	 * Initialize symbol table to its default state with all keywords/operators set
	 * @return
	 */
	public static SymbolTable initializeSymbolTable() {
		SymbolTable symbol_table = new SymbolTable();
		//This is just an idea of how this will work
		symbol_table.put("(", Symbol.makeSymbol("(",Symbol.Type.PAREN_OPEN,Symbol.DataType.NONE,Symbol.SemanticType.OPERATOR,"",0));
		return symbol_table;
	}
	
	/**
	 * Get an existing symbol, or create a new one and return that
	 * @param lexeme
	 * @return
	 */
	public Symbol getSymbol(String lexeme) {
		Symbol fetched_symbol = this.get(lexeme);
		if(fetched_symbol == null) {
			fetched_symbol = Symbol.makeSymbol(lexeme);
			this.put(lexeme, fetched_symbol);
		}
		return fetched_symbol;
	}
}
