import java.util.Hashtable;

/**
 * Represents the symbol table
 * @author Mike, Ryan
 */
public class SymbolTable extends Hashtable<String, LexemeTerminal> {

	/**
	 * For Hashtable throws
	 */
	private static final long serialVersionUID = -2427108052657869701L;
	
	/**
	 * overloading put() to work cleanly with symbols
	 * @param symbol
	 */
	private void put(LexemeTerminal symbol) {
		super.put(symbol.getLexeme(), symbol);
	}
	
	/**
	 * Initialize symbol table to its default state with all keywords/operators set
	 * @return
	 */
	public static SymbolTable initializeSymbolTable() {
		SymbolTable symbol_table = new SymbolTable();
		symbol_table.put(new LexemeTerminal("("));
		symbol_table.put(new LexemeTerminal(")"));
		symbol_table.put(new LexemeTerminal("["));
		symbol_table.put(new LexemeTerminal("]"));
		symbol_table.put(new LexemeTerminal(":"));
		symbol_table.put(new LexemeTerminal(";"));
		symbol_table.put(new LexemeTerminal("."));
		symbol_table.put(new LexemeTerminal(","));
		symbol_table.put(new RelationalOperatorSymbol("<>"));
		symbol_table.put(new RelationalOperatorSymbol("<="));
		symbol_table.put(new RelationalOperatorSymbol(">="));
		symbol_table.put(new RelationalOperatorSymbol("="));
		symbol_table.put(new RelationalOperatorSymbol("<"));
		symbol_table.put(new RelationalOperatorSymbol(">"));
		symbol_table.put(new AdditionOperatorSymbol("+"));
		symbol_table.put(new AdditionOperatorSymbol("-"));
		symbol_table.put(new MultiplicationOperatorSymbol("DIV"));
		symbol_table.put(new MultiplicationOperatorSymbol("MOD"));
		symbol_table.put(new MultiplicationOperatorSymbol("/"));
		symbol_table.put(new MultiplicationOperatorSymbol("*"));
		symbol_table.put(new AssignmentOperatorSymbol(":="));
		symbol_table.put(new LexemeTerminal("NOT"));
		symbol_table.put(new LexemeTerminal("IF"));
		symbol_table.put(new LexemeTerminal("THEN"));
		symbol_table.put(new LexemeTerminal("ELSE"));
		symbol_table.put(new LexemeTerminal("WHILE"));
		symbol_table.put(new LexemeTerminal("DO"));
		symbol_table.put(new LexemeTerminal("VAR"));
		symbol_table.put(new LexemeTerminal("FUNCTION"));
		symbol_table.put(new LexemeTerminal("PROCEDURE"));
		symbol_table.put(new LexemeTerminal("ARRAY"));
		symbol_table.put(new LexemeTerminal("OF"));
		symbol_table.put(new LexemeTerminal("BEGIN"));
		symbol_table.put(new LexemeTerminal("END"));
		symbol_table.put(new LexemeTerminal("INTEGER"));
		symbol_table.put(new LexemeTerminal("REAL"));
		symbol_table.put(new LexemeTerminal("PROGRAM"));
		symbol_table.put(new LexemeTerminal("."));
		
		return symbol_table;
	}
	
	/**
	 * Get an existing symbol, or create a new one and return that
	 * @param lexeme
	 * @return
	 * @throws LexemeTerminal.LexemeTerminalException 
	 */
	public LexemeTerminal getSymbol(String lexeme) throws LexemeTerminal.LexemeTerminalException {
		LexemeTerminal fetched_symbol = this.get(lexeme);
		if(fetched_symbol == null) {
			fetched_symbol = LexemeTerminal.makeSymbol(lexeme);
			this.put(lexeme, fetched_symbol);
		}
		return fetched_symbol;
	}
}
