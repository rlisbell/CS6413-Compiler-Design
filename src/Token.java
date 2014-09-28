import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * class that represents a chunk of source code
 * the smallest atomic chunk that might have any meaning
 * class is immutable
 * @author bobboau
 */
public class Token {
	
	/**
	 * Thrown when a Keyword is found in a bad context
	 */
	public static class KeywordException extends Exception {
		private static final long serialVersionUID = 8741809598928881876L;

		public KeywordException(String message){
			super(message);
		}
	}
	/**
	 * Language keywords
	 */
	static final List<String> KEYWORDS = Arrays.asList("PROGRAM","BEGIN","END","CONST");
	
	/**
	 * pattern that matches word type tokens 
	 * \\w matches [a-zA-Z_0-9]
	 */
	static final Pattern TOKEN_WORD_PATTERN = Pattern.compile("^\\w+");

	/**
	 * pattern that matches number type tokens 
	 * \\d+  (\\.  \\d+   (E [+-]?  \\d+)? )?
	 * 1+ digits, optional . and 1+ digits, optional E optional +/- and 1+ digits
	 */
	static final Pattern TOKEN_NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+(E[+-]?\\d+)?)?");

	/**
	 * pattern that matches operator type tokens
	 */
	static final Pattern TOKEN_OPERATOR_PATTERN = Pattern.compile("^([-+/*=<>])|(<>)|(<=)|(>=)|(:=)|(==)|(DIV)|(MOD)|(OR)|(AND)|(,)|(\\()|(\\))|(;)");
	
	/**
	 * enum that specifies the different options for what sort of token we might have 
	 * @author bobboau
	 */
	public enum Type {WORD, NUMBER, OPERATOR, EOF, ERROR}
	
	/**
	 * what type of token this is
	 */
	private final Type type;
	
	/**
	 * the string that generated this token
	 */
	private final String lexeme;
	
	/**
	 * which line in the source file did this token start on
	 */
	private final int line_number;
	
	/**
	 * basic constructor
	 * @param _lexeme
	 * @param _line_number
	 */
	public Token(String _lexeme, int _line_number){
		lexeme = _lexeme;
		line_number = _line_number;
		//determine Token type... if Token is subclassed, consider factory method
		//order is important!
		if(lexeme == "."){
			type = Token.Type.EOF;
		}
		else if(TOKEN_OPERATOR_PATTERN.matcher(lexeme).matches()){
			type = Token.Type.OPERATOR;
		}
		else if(TOKEN_NUMBER_PATTERN.matcher(lexeme).matches()){
			type = Token.Type.NUMBER;
		}
		else if(TOKEN_WORD_PATTERN.matcher(lexeme).matches()){
			type = Token.Type.WORD;
		}
		else{
			type = Token.Type.ERROR;
		}
	}

	/**
	 * @return the lexeme
	 */
	public String getLexeme() {
		return lexeme;
	}

	/**
	 * @return the line_number
	 */
	public int getLineNumber() {
		return line_number;
	}

	/**
	 * @return the type
	 */
	public Type getType() {
		return type;
	}

	/**
	 * generates formatted output for presenting to the user
	 * could have used toString, but I am not a fan of that method as there are a dozen different ways you might want to make something into a string
	 * @return String with a small mini-report about this token
	 */
	public String print() {
		return String.format("%-30s", lexeme)+"Type:"+type.toString();
	}
	

	/**
	 * factory that makes Tokens
	 * @param _lexeme string that made the Token
	 * @param _line_number where in the file the string was from
	 * @return a Token
	 * @throws KeywordException 
	 */
	public static Token makeToken(String _lexeme, int _line_number) throws KeywordException{
		Token token = new Token(_lexeme, _line_number);
		if(token.getType()==Type.WORD && KEYWORDS.contains(_lexeme) && _lexeme.charAt(_lexeme.length()-1)!='.'){
			throw new KeywordException("keyword "+_lexeme+" on line "+_line_number+" must be space delimited or end of program");
		}
		return token;
	}
	
	/**
	 * makes tokens and allows for keywords
	 * @param _lexeme
	 * @param _line_number
	 * @return Token
	 */
	public static Token makeKeywordToken(String _lexeme, int _line_number){
		Token token = new Token(_lexeme, _line_number);
		if(token.getType()==Type.ERROR){
			if(_lexeme.charAt(_lexeme.length()-1)=='.'){
				String new_lexeme = _lexeme.substring(0,_lexeme.length()-1);
				return makeKeywordToken(new_lexeme, _line_number);
			}
			return null;
		}
		else{
			return token;
		}
	}
}
