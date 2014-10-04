import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that represents a token of code
 * the smallest atomic chunk that might have any meaning
 * class is immutable
 * @author Mike, Ryan
 */
public class Token {
	
	/**
	 * pattern that matches parentheses and brackets
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_LEXEME_PATTERN = Pattern.compile("^("+
			"(\\()|(\\))|(\\[)|(\\])"
			+"|"+
			"(\\,)|(\\.)|(\\;)|(\\:)"
			+"|"+
			"(<>)|(<=)|(>=)|(:=)|(DIV)|(MOD)|(OR)|(AND)|(,)|(\\()|(\\))|(;)|([-+/*=<>])"
			+"|"+
			"([a-z]\\w*)"
			+"|"+
			"(\\d+(\\.\\d+)?(E[+-]?\\d+)?)"
		+")", Pattern.CASE_INSENSITIVE);
	
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
	public Token(String _lexeme, Type _type, int _line_number){
		lexeme = _lexeme;
		line_number = _line_number;
		type = _type;
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
		//30 column aligned for prettification
		return String.format("%-30s", lexeme)+"Type:"+type.toString();
	}
	
	/**
	 * factory that makes Tokens from a block
	 * @param lexeme_block string that should contain one or more lexemes
	 * @param line_number where in the file the string was from
	 * @return Token
	 */
	public static Token makeToken(String lexeme_block, int line_number){
		Matcher lexeme_matcher = TOKEN_LEXEME_PATTERN.matcher(lexeme_block);
		
		String lexeme = "";
		Type type;
		
		if(lexeme_matcher.find()){
			/*TODO:use symbol table*/
			type = Token.Type.ERROR;
			lexeme = lexeme_matcher.group(0);
		}
		else{
			type = Token.Type.ERROR;
			lexeme = lexeme_block;
		}

		Token token = new Token(lexeme, type, line_number);
		
		return token;
	}
}
