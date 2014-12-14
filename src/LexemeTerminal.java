import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * Contains all information (attributes) for a single symbol
 * @author Mike, Ryan
 */
public class LexemeTerminal extends Terminal {	
	
	/**
	 * common base for all Symbol exceptions
	 */
	public static class LexemeTerminalException extends Exception {
		private static final long serialVersionUID = -4457162023556548921L;

		public LexemeTerminalException(String message){
			super(message);
		}
	}
	
	/**
	 * common base for all Symbol exceptions
	 */
	public static class UnexpectedSymbolException extends LexemeTerminalException {
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
	 * the lexeme for this symbol, redundant, but may be useful
	 */
	private String lexeme;

	/**
	 * constructor
	 * @param _lexeme
	 * @param _type
	 */
	public LexemeTerminal(String _lexeme) {
		lexeme = _lexeme.toUpperCase();
	}
	
	/**
	 * Makes a new symbol on the fly as new lexemes are discovered
	 * If we don't know the type, it can only be an identifier or number literal
	 * Exceptions arise if we messed up the symbol table init, or we find an invalid character
	 * @param _lexeme
	 * @return
	 * @throws UnexpectedSymbolException 
	 */
	public static LexemeTerminal makeSymbol(String lexeme) throws UnexpectedSymbolException {
		Matcher identifier_matcher = IDENTIFIER_PATTERN.matcher(lexeme);
		Matcher integer_matcher = INTEGER_PATTERN.matcher(lexeme);
		Matcher real_matcher = REAL_PATTERN.matcher(lexeme);
		if(identifier_matcher.matches()){
			return new IdentifierSymbol(lexeme);
		}
		else if (real_matcher.matches()) {
			return new LiteralSymbol<Double>(lexeme, Double.valueOf(lexeme));
		}
		else if (integer_matcher.matches()) {
			//Going through double because Integer does not like scientific notation (e.g. 2342e2)
			return new LiteralSymbol<Integer>(lexeme, Double.valueOf(lexeme).intValue());
		}
		else {
			throw new UnexpectedSymbolException("Unexpected symbol "+lexeme);
		}
	}

	/**
	 * @return the lexeme
	 */
	public String getLexeme() {
		return lexeme;
	}
}
