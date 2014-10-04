
/**
 * Contains all information (attributes) for a single symbol
 * @author Mike, Ryan
 */
public class Symbol {	
	/**
	 * the lexeme for this symbol, redundant, but may be useful
	 */
	private String lexeme;
	
	/**
	 * type of token this symbol is
	 */
	private Token.Type token_type;
	
	/**
	 * data type of this token (determined... when?)
	 */
	private DataType data_type;
	
	/**
	 * semantic type of this token (determined during semantic analysis)
	 */
	private SemanticType semantic_type;
	
	/**
	 * Value (for literals)
	 */
	private String value;
	
	/**
	 * Scope (not sure how to handle this)
	 */
	private int scope;
	
	/**
	 * These two are ripped form the slides for JASON, needs to be updated for our version of Pascal
	 */
	public enum DataType {UNKNOWN, NONE, PROGRAM, PROCEDURE, INTEGER, REAL};
	public enum SemanticType {UNKNOWN, KEYWORD, PROGRAM, 
		PARAMETER, VARIABLE, TEMPVAR, CONSTANT, ENUM, STRUCT, 
		UNION, PROCEDURE, FUNCTION, LABEL, LITERAL, OPERATOR};
		
	public Symbol(String _lexeme, Token.Type _token_type, DataType _data_type, SemanticType _semantic_type, String _value, int _scope) {
		lexeme = _lexeme;
		token_type = _token_type;
		data_type = _data_type;
		semantic_type = _semantic_type;
		value = _value;
		scope = _scope;
	}
		
	public static Symbol makeSymbol(String _lexeme, Token.Type _token_type, DataType _data_type, SemanticType _semantic_type, String _value, int _scope) {
		Symbol symbol = new Symbol(_lexeme, _token_type, _data_type, _semantic_type, _value, _scope);
		return symbol;
	}
	
	
	
	
	
	
	
	
	
	
}
