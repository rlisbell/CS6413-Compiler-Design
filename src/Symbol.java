
/**
 * Contains all information (attributes) for a single symbol
 * @author Mike, Ryan
 */
public class Symbol {	
	
	/**
	 * enum that specifies the different options for what sort of token we might have 
	 * @author bobboau
	 */
	public enum Type {
		PAREN_OPEN, PAREN_CLOSE, BRACKET_OPEN, BRACKET_CLOSE, 
		COLON, SEMICOLON, PERIOD, COMMA, 
		REL_OP, ADD_OP, MUL_OP, ASSIGN_OP, NOT_OP,
		IF, THEN, ELSE, 
		WHILE, DO,
		VAR_START,
		FUNCTION, PROCEDURE, 
		ARRAY, OF, 
		BEGIN, END, 
		INTEGER_TYPE, REAL_TYPE, 
		PROGRAM_START, EOF,
		NUMBER_LITERAL, 
		IDENTIFIER, 
		ERROR
	}
	
	/**
	 * the lexeme for this symbol, redundant, but may be useful
	 */
	private String lexeme;
	
	/**
	 * type of token this symbol is
	 */
	private Symbol.Type type;
	
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
		
	public Symbol(String _lexeme, Symbol.Type _type, DataType _data_type, SemanticType _semantic_type, String _value, int _scope) {
		lexeme = _lexeme;
		type = _type;
		data_type = _data_type;
		semantic_type = _semantic_type;
		value = _value;
		scope = _scope;
	}
		
	public static Symbol makeSymbol(String _lexeme, Symbol.Type _token_type, DataType _data_type, SemanticType _semantic_type, String _value, int _scope) {
		Symbol symbol = new Symbol(_lexeme, _token_type, _data_type, _semantic_type, _value, _scope);
		return symbol;
	}

	/**
	 * @return the lexeme
	 */
	public String getLexeme() {
		return lexeme;
	}


	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}
	
	
	
	
	
	
	
}
