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
	
	public static SymbolTable initializeSymbolTable() {
		SymbolTable symbol_table = new SymbolTable();
		//This is just an idea of how this will work
		symbol_table.put("(", Symbol.makeSymbol("(",Token.Type.PAREN_OPEN,Symbol.DataType.NONE,Symbol.SemanticType.OPERATOR,"",0));
		return symbol_table;
	}
}
