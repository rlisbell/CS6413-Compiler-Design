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
		symbol_table.put(new Symbol("(",Symbol.Type.PAREN_OPEN));
		symbol_table.put(new Symbol(")",Symbol.Type.PAREN_CLOSE));
		symbol_table.put(new Symbol("[",Symbol.Type.BRACKET_OPEN));
		symbol_table.put(new Symbol("]",Symbol.Type.BRACKET_CLOSE));
		symbol_table.put(new Symbol(":",Symbol.Type.COLON));
		symbol_table.put(new Symbol(";",Symbol.Type.SEMICOLON));
		symbol_table.put(new Symbol(".",Symbol.Type.PERIOD));
		symbol_table.put(new Symbol(",",Symbol.Type.COMMA));
		symbol_table.put(new Symbol("<>",Symbol.Type.REL_OP));
		symbol_table.put(new Symbol("<=",Symbol.Type.REL_OP));
		symbol_table.put(new Symbol(">=",Symbol.Type.REL_OP));
		symbol_table.put(new Symbol("=",Symbol.Type.REL_OP));
		symbol_table.put(new Symbol("<",Symbol.Type.REL_OP));
		symbol_table.put(new Symbol(">",Symbol.Type.REL_OP));
		symbol_table.put(new Symbol("+",Symbol.Type.ADD_OP));
		symbol_table.put(new Symbol("-",Symbol.Type.ADD_OP));
		symbol_table.put(new Symbol("DIV",Symbol.Type.MUL_OP));
		symbol_table.put(new Symbol("MOD",Symbol.Type.MUL_OP));
		symbol_table.put(new Symbol("/",Symbol.Type.MUL_OP));
		symbol_table.put(new Symbol("*",Symbol.Type.MUL_OP));
		symbol_table.put(new Symbol(":=",Symbol.Type.ASSIGN_OP));
		symbol_table.put(new Symbol("NOT",Symbol.Type.NOT_OP));
		symbol_table.put(new Symbol("IF",Symbol.Type.IF));
		symbol_table.put(new Symbol("THEN",Symbol.Type.THEN));
		symbol_table.put(new Symbol("ELSE",Symbol.Type.ELSE));
		symbol_table.put(new Symbol("WHILE",Symbol.Type.WHILE));
		symbol_table.put(new Symbol("DO",Symbol.Type.DO));
		symbol_table.put(new Symbol("VAR",Symbol.Type.VAR));
		symbol_table.put(new Symbol("FUNCTION",Symbol.Type.FUNCTION));
		symbol_table.put(new Symbol("PROCEDURE",Symbol.Type.PROCEDURE));
		symbol_table.put(new Symbol("ARRAY",Symbol.Type.ARRAY));
		symbol_table.put(new Symbol("OF",Symbol.Type.OF));
		symbol_table.put(new Symbol("BEGIN",Symbol.Type.BEGIN));
		symbol_table.put(new Symbol("END",Symbol.Type.END));
		symbol_table.put(new Symbol("INTEGER",Symbol.Type.INTEGER_TYPE));
		symbol_table.put(new Symbol("REAL",Symbol.Type.REAL_TYPE));
		symbol_table.put(new Symbol("PROGRAM",Symbol.Type.PROGRAM_START));
		symbol_table.put(new Symbol(".",Symbol.Type.FINAL_DOT));
		
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
