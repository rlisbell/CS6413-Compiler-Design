import java.util.regex.Pattern;

/**
 * class that represents a chunk of source code
 * the smallest atomic chunk that might have any meaning
 * class is immutable
 * @author bobboau
 */
public class Token {
	/**
	 * pattern that matches word type tokens 
	 * \\w matches [a-zA-Z_0-9]
	 */
	static final Pattern TOKEN_WORD_PATTERN = Pattern.compile("^\\w*$");

	/**
	 * pattern that matches number type tokens 
	 * \\d+  (\\.  \\d+   (E [+-]?  \\d+)? )?
	 * 1+ digits, optional . and 1+ digits, optional E optional +/- and 1+ digits
	 */
	static final Pattern TOKEN_NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+(E[+-]?\\d+)?)?$");

	/**
	 * pattern that matches operator type tokens
	 * TODO: greater/lessthan, operator keywords (e.g. OR)
	 */
	static final Pattern TOKEN_OPERATOR_PATTERN = Pattern.compile("^[-+/*=:<>]+$");
	
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
			type = Token.Type.WORD;
		}
		else if(TOKEN_NUMBER_PATTERN.matcher(lexeme).matches()){
			type = Token.Type.NUMBER;
		}
		else if(TOKEN_WORD_PATTERN.matcher(lexeme).matches()){
			type = Token.Type.OPERATOR;
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
		return "Type:"+type.toString()+" Lexeme:"+lexeme;
	}
	

	/**
	 * factory that makes Tokens
	 * @param _lexeme string that made the Token
	 * @param _line_number where in the file the string was from
	 * @return a Token
	 */
	public static Token makeToken(String _lexeme, int _line_number){
		return new Token(_lexeme, _line_number);
	}
}
