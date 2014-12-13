import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that represents a token of code
 * the smallest atomic chunk that might have any meaning
 * It is worth noting that while we could check that keywords are
 * surrounded by whitespace during token generation, this step has
 * been deemed better left for the parser at this point...
 * 
 * class is immutable
 * @author Mike, Ryan
 */
public class Token {
	
	/*********************\
	|* custom exceptions *| 
	\*********************/
	
	/**
	 * @author bobboau
	 * common base for all Token exceptions
	 */
	public static class TokenException extends Exception {
		private static final long serialVersionUID = -4575729551102837842L;

		public TokenException(String message){
			super(message);
		}
	}
	
	/**
	 * @author bobboau
	 * thrown when a lexeme cannot be found
	 */
	public static class LexemeNotFoundException extends TokenException {
		private static final long serialVersionUID = -4680793979317618076L;

		public LexemeNotFoundException(String message){
			super(message);
		}
	}
	
	/**************\
	|* properties *|
	\**************/

	/**
	 * pattern that matches parentheses and brackets
	 * longer patterns must be listed first
	 */
	public static final Pattern TOKEN_LEXEME_PATTERN = Pattern.compile("^("+
			"(<>)|(<=)|(>=)|(:=)|(DIV)|(MOD)|(OR)|(AND)|([-+/*=<>])"
			+"|"+
			"(\\()|(\\))|(\\[)|(\\])"
			+"|"+
			"(\\,)|(\\.)|(\\;)|(\\:)"
			+"|"+
			"([a-z]\\w*)"
			+"|"+
			"(\\d+(\\.\\d+)?(E[+-]?\\d+)?)"
		+")", Pattern.CASE_INSENSITIVE);
	
	
	/**
	 * what type of token this is
	 */
	private final Terminal terminal;
	
	/**
	 * which line in the source file did this token start on
	 */
	private final int line_number;
	
	/**
	 * basic constructor
	 * @param _lexeme
	 * @param _line_number
	 */
	public Token(Terminal _terminal, int _line_number){
		terminal = _terminal;
		line_number = _line_number;
	}

	/**
	 * @return the line_number
	 */
	public int getLineNumber() {
		return line_number;
	}

	/**
	 * generates formatted output for presenting to the user
	 * could have used toString, but I am not a fan of that method as there are a dozen different ways you might want to make something into a string
	 * @return String with a small mini-report about this token
	 */
	public String print() {
		//30 column aligned for prettification
		return String.format("%-30s", getLexeme());
	}
	
	/**
	 * get the lexeme of the terminal we are wrapping
	 * @return
	 */
	public String getLexeme() {
		return terminal.getLexeme();
	}

	/**
	 * factory that makes Tokens from a block
	 * @param lexeme_block string that should contain one or more lexemes
	 * @param line_number where in the file the string was from
	 * @return Token
	 * @throws TokenException 
	 * @throws LexemeTerminal.LexemeTerminalException 
	 */
	public static Token makeToken(String lexeme_block, int line_number, SymbolTable symbol_table) throws TokenException, LexemeTerminal.LexemeTerminalException{
		Matcher lexeme_matcher = TOKEN_LEXEME_PATTERN.matcher(lexeme_block);
		
		if(lexeme_matcher.find()){
			try {
				return new Token(symbol_table.getSymbol(lexeme_matcher.group(0)), line_number);
			} catch (LexemeTerminal.UnexpectedSymbolException e) {
				throw new LexemeTerminal.UnexpectedSymbolException(e.getMessage()+" on line "+line_number);
			}
		}
		else{
			throw new Token.LexemeNotFoundException("could not find the next lexeme in the block on line #"+line_number+" : "+lexeme_block);
		}
	}
	
	/**
	 * returns true if this is an eof token
	 * this function is only going to be around for a short while until we refactor our Symbol hierarchy
	 * @return boolean
	 */
	public boolean isEofToken() {
		return terminal instanceof EofSymbol;
	}
}
