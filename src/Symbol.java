import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Contains all information (attributes) for a single symbol
 * @author Mike, Ryan
 */
public class Symbol {	
	
	/**
	 * Matches valid identifiers
	 */
	public static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^([a-z]\\w*)", Pattern.CASE_INSENSITIVE);
	/**
	 * Matches valid number literals
	 */
	public static final Pattern NUMERIC_PATTERN = Pattern.compile("^(\\d+(\\.\\d+)?(E[+-]?\\d+)?)", Pattern.CASE_INSENSITIVE);
	
	/**
	 * enum that specifies the different options for what sort of symbol we might have 
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
	 * type of symbol this symbol is
	 */
	private Symbol.Type type;
	
	/**
	 * data type of this symbol (determined... when?)
	 */
	private DataType data_type;
	
	/**
	 * semantic type of this symbol (determined during semantic analysis)
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
	public enum DataType {UNKNOWN, NONE, PROGRAM, PROCEDURE, INTEGER, REAL, ARRAY};
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
	
	/**
	 * Constructs a complete symbol manually, primarily used for symbol table initialization
	 * @param _lexeme
	 * @param _symbol_type
	 * @param _data_type
	 * @param _semantic_type
	 * @param _value
	 * @param _scope
	 * @return
	 */
	public static Symbol makeSymbol(String _lexeme, Symbol.Type _symbol_type, DataType _data_type, SemanticType _semantic_type, String _value, int _scope) {
		Symbol symbol = new Symbol(_lexeme, _symbol_type, _data_type, _semantic_type, _value, _scope);
		return symbol;
	}
	
	/**
	 * Makes a new symbol on the fly as new lexemes are discovered
	 * @param _lexeme
	 * @return
	 */
	public static Symbol makeSymbol(String _lexeme) {
		Type type = determineType(_lexeme);
		DataType data_type = determineDataType(_lexeme);
		SemanticType semantic_type = determineSemanticType(_lexeme);
		String value = extractValue(_lexeme);
		int scope = 0;//worry about this later
		return new Symbol(_lexeme, type, data_type, semantic_type, value, scope);
	}
	
	/**
	 * If we don't know the type, it can only be an identifier or number literal
	 * Exceptions arise if we messed up the symbol table init, or we find an invalid character
	 * @param lexeme
	 * @return
	 */
	private static Type determineType(String lexeme) {
		Matcher identifier_matcher = IDENTIFIER_PATTERN.matcher(lexeme);
		Matcher numeric_matcher = NUMERIC_PATTERN.matcher(lexeme);
		Type return_type = null;
		if(identifier_matcher.matches()){
			 return_type = Type.IDENTIFIER;
		}
		else if (numeric_matcher.matches()) {
			return_type = Type.NUMBER_LITERAL;
		}
		else {
			//throw error
		}
		return return_type;
	}
	
	/**
	 * May not be determinable until parsing?
	 * @param lexeme
	 * @return
	 */
	private static DataType determineDataType(String lexeme) {
		return DataType.UNKNOWN;
	}
	
	/**
	 * May not be determinable until parsing?
	 * @param lexeme
	 * @return
	 */
	private static SemanticType determineSemanticType(String lexeme) {
		return SemanticType.UNKNOWN;
	}
	
	/**
	 * Need to return actual numeric value for numbers
	 * This may not be possible until we know a symbol's data type?
	 * @param lexeme
	 * @return
	 */
	private static String extractValue(String lexeme) {
		return "0";
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
