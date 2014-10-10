import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Contains all information (attributes) for a single symbol
 * @author Mike, Ryan
 */
public class Symbol {	
	
	/**
	 * common base for all Symbol exceptions
	 */
	public static class SymbolException extends Exception {
		private static final long serialVersionUID = -4457162023556548921L;

		public SymbolException(String message){
			super(message);
		}
	}
	
	/**
	 * common base for all Symbol exceptions
	 */
	public static class UnexpectedSymbolException extends SymbolException {
		private static final long serialVersionUID = 4153515580979985084L;

		public UnexpectedSymbolException(String message){
			super(message);
		}
	}
	
	/**
	 * Matches valid identifiers
	 */
	public static final Pattern IDENTIFIER_PATTERN = Pattern.compile("^([a-z]\\w*)$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Matches valid integer literals
	 * numerals not followed by a dot (not including the dot in the match)
	 */
	public static final Pattern INTEGER_PATTERN = Pattern.compile("^\\d+(?!\\.)(E[+-]?\\d+)?$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * Matches valid real literals
	 */
	public static final Pattern REAL_PATTERN = Pattern.compile("^(\\d+\\.\\d+(E[+-]?\\d+)?)$", Pattern.CASE_INSENSITIVE);
	
	/**
	 * enum that specifies the different options for what sort of symbol we might have 
	 */
	public enum Type {
		PAREN_OPEN, PAREN_CLOSE, BRACKET_OPEN, BRACKET_CLOSE, 
		COLON, SEMICOLON, PERIOD, COMMA, 
		REL_OP, ADD_OP, MUL_OP, ASSIGN_OP, NOT_OP,
		IF, THEN, ELSE, 
		WHILE, DO,
		VAR,
		FUNCTION, PROCEDURE, 
		ARRAY, OF, 
		BEGIN, END, 
		INTEGER_TYPE, REAL_TYPE, 
		PROGRAM_START, EOF,
		INTEGER_LITERAL, REAL_LITERAL, 
		IDENTIFIER, 
		FINAL_DOT,
		ERROR
	}
	
	/**
	 * the lexeme for this symbol, redundant, but may be useful
	 */
	private String lexeme;
	
	/**
	 * type of symbol this symbol is
	 */
	private Type type;

	/**
	 * constructor
	 * @param _lexeme
	 * @param _type
	 */
	public Symbol(String _lexeme, Symbol.Type _type) {
		lexeme = _lexeme;
		type = _type;
	}
	
	/**
	 * Makes a new symbol on the fly as new lexemes are discovered
	 * @param _lexeme
	 * @return
	 * @throws UnexpectedSymbolException 
	 */
	public static Symbol makeSymbol(String _lexeme) throws UnexpectedSymbolException {
		Type type = determineType(_lexeme);
		switch(type){
			case INTEGER_LITERAL:
				//Going through double because Integer does not like scientific notation (e.g. 2342e2)
				return new LiteralSymbol<Integer>(_lexeme, type, Double.valueOf(_lexeme).intValue());
			case REAL_LITERAL:
				return new LiteralSymbol<Double>(_lexeme, type, Double.valueOf(_lexeme));
			default:
				return new Symbol(_lexeme, type);
		}
	}
	
	/**
	 * If we don't know the type, it can only be an identifier or number literal
	 * Exceptions arise if we messed up the symbol table init, or we find an invalid character
	 * @param lexeme
	 * @return
	 * @throws UnexpectedSymbolException 
	 */
	private static Type determineType(String lexeme) throws UnexpectedSymbolException {
		Matcher identifier_matcher = IDENTIFIER_PATTERN.matcher(lexeme);
		Matcher integer_matcher = INTEGER_PATTERN.matcher(lexeme);
		Matcher real_matcher = REAL_PATTERN.matcher(lexeme);
		Type return_type = null;
		if(identifier_matcher.matches()){
			 return_type = Type.IDENTIFIER;
		}
		else if (real_matcher.matches()) {
			return_type = Type.REAL_LITERAL;
		}
		else if (integer_matcher.matches()) {
			return_type = Type.INTEGER_LITERAL;
		}
		else {
			throw new UnexpectedSymbolException("Unexpected symbol "+lexeme);
		}
		return return_type;
	}

	/**
	 * @return the lexeme
	 */
	public String getLexeme() {
		return lexeme;
	}

	/**
	 * @return the symbol type
	 */
	public Type getType() {
		return type;
	}
}
