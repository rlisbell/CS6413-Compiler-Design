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
	static final Pattern TOKEN_PAREN_PATTERN = Pattern.compile("^((\\()|(\\))|(\\[)|(\\]))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches :;.,
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_PUNCTUATION_PATTERN = Pattern.compile("^((\\,)|(\\.)|(\\;)|(\\:))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches operator type tokens
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_OPERATOR_PATTERN = Pattern.compile("^((<>)|(<=)|(>=)|(:=)|(DIV)|(MOD)|(OR)|(AND)|(,)|(\\()|(\\))|(;)|([-+/*=<>]))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches if/else
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_IF_ELSE_PATTERN = Pattern.compile("^((IF)|(THEN)|(ELSE))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches loop structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_LOOP_PATTERN = Pattern.compile("^((WHILE)|(DO))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches variable structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_VARIABLE_PATTERN = Pattern.compile("^((FUNCTION)|(PROCEDURE))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches method structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_METHOD_PATTERN = Pattern.compile("^VAR", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches array structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_ARRAY_PATTERN = Pattern.compile("^((ARRAY)|(OF))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches block structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_BLOCK_PATTERN = Pattern.compile("^((BEGIN)|(END))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches basic type structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_TYPE_PATTERN = Pattern.compile("^((INTEGER)|(REAL))", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches high level program structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_PROGRAM_PATTERN = Pattern.compile("^((PROGRAM)|(\\.))", Pattern.CASE_INSENSITIVE);
	
	
	/**
	 * pattern that matches loop structures
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_IDENTIFIER_PATTERN = Pattern.compile("^\\w[\\w\\d]*", Pattern.CASE_INSENSITIVE);
	
	/**
	 * pattern that matches number type tokens 
	 * \\d+  (\\.  \\d+)?   (E [+-]?  \\d+)?
	 * 1+ digits, optional . and 1+ digits, optional (E optional +/- and 1+ digits)
	 */
	static final Pattern TOKEN_NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?(E[+-]?\\d+)?", Pattern.CASE_INSENSITIVE);
	
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
		Matcher operator_matcher = TOKEN_OPERATOR_PATTERN.matcher(lexeme_block);
		Matcher number_matcher = TOKEN_NUMBER_PATTERN.matcher(lexeme_block);
		Matcher word_matcher = TOKEN_WORD_PATTERN.matcher(lexeme_block);
		
		String lexeme = "";
		Type type;
		
		//order is important!
		/*checking of keywords should happen here*/
		/*we are not dealing with keywords yet though*/
		if(lexeme_block.equals(".")){
			type = Token.Type.EOF;
			lexeme = lexeme_block;
		}
		else if(operator_matcher.find()){
			type = Token.Type.OPERATOR;
			lexeme = operator_matcher.group(0);
		}
		else if(number_matcher.find()){
			type = Token.Type.NUMBER;
			lexeme = number_matcher.group(0);
		}
		else if(word_matcher.find()){
			type = Token.Type.WORD;
			lexeme = word_matcher.group(0);
		}
		else{
			type = Token.Type.ERROR;
			lexeme = lexeme_block;
		}

		Token token = new Token(lexeme, type, line_number);
		
		return token;
	}
}
