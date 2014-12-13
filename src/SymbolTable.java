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
	 * overloading put() to work cleanly with symbols
	 * @param symbol
	 */
	private void put(Symbol symbol) {
		super.put(symbol.getLexeme(), symbol);
	}
	
	/**
	 * Initialize symbol table to its default state with all keywords/operators set
	 * @return
	 */
	public static SymbolTable initializeSymbolTable() {
		SymbolTable symbol_table = new SymbolTable();
		symbol_table.put(new Symbol("("));
		symbol_table.put(new Symbol(")"));
		symbol_table.put(new Symbol("["));
		symbol_table.put(new Symbol("]"));
		symbol_table.put(new Symbol(":"));
		symbol_table.put(new Symbol(";"));
		symbol_table.put(new Symbol("."));
		symbol_table.put(new Symbol(","));
		symbol_table.put(new Symbol("<>"));
		symbol_table.put(new Symbol("<="));
		symbol_table.put(new Symbol(">="));
		symbol_table.put(new Symbol("="));
		symbol_table.put(new Symbol("<"));
		symbol_table.put(new Symbol(">"));
		symbol_table.put(new Symbol("+"));
		symbol_table.put(new Symbol("-"));
		symbol_table.put(new Symbol("DIV"));
		symbol_table.put(new Symbol("MOD"));
		symbol_table.put(new Symbol("/"));
		symbol_table.put(new Symbol("*"));
		symbol_table.put(new Symbol(":="));
		symbol_table.put(new Symbol("NOT"));
		symbol_table.put(new Symbol("IF"));
		symbol_table.put(new Symbol("THEN"));
		symbol_table.put(new Symbol("ELSE"));
		symbol_table.put(new Symbol("WHILE"));
		symbol_table.put(new Symbol("DO"));
		symbol_table.put(new Symbol("VAR"));
		symbol_table.put(new Symbol("FUNCTION"));
		symbol_table.put(new Symbol("PROCEDURE"));
		symbol_table.put(new Symbol("ARRAY"));
		symbol_table.put(new Symbol("OF"));
		symbol_table.put(new Symbol("BEGIN"));
		symbol_table.put(new Symbol("END"));
		symbol_table.put(new Symbol("INTEGER"));
		symbol_table.put(new Symbol("REAL"));
		symbol_table.put(new Symbol("PROGRAM"));
		symbol_table.put(new Symbol("."));
		
		return symbol_table;
	}
	
	/**
	 * Get an existing symbol, or create a new one and return that
	 * @param lexeme
	 * @return
	 * @throws Symbol.SymbolException 
	 */
	public Symbol getSymbol(String lexeme) throws Symbol.SymbolException {
		Symbol fetched_symbol = this.get(lexeme);
		if(fetched_symbol == null) {
			fetched_symbol = Symbol.makeSymbol(lexeme);
			this.put(lexeme, fetched_symbol);
		}
		return fetched_symbol;
	}
}
