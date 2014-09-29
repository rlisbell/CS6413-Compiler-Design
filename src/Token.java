//import java.util.Arrays; //needed for keywords down the line
//import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * class that represents a token of code
 * the smallest atomic chunk that might have any meaning
 * class is immutable
 * @author bobboau
 */
public class Token {
	
	/**
	 * Thrown when a Keyword is found in a bad context
	 * TODO: Implement along with symbol table
	 */
//	public static class KeywordException extends Exception {
//		private static final long serialVersionUID = 8741809598928881876L;
//
//		public KeywordException(String message){
//			super(message);
//		}
//	}
	/**
	 * Language keywords
	 */
//	static final List<String> KEYWORDS = Arrays.asList("PROGRAM","BEGIN","END","CONST");
	
	/**
	 * pattern that matches word type tokens 
	 * \\w matches [a-zA-Z_0-9]
	 */
	static final Pattern TOKEN_WORD_PATTERN = Pattern.compile("^\\w+");

	/**
	 * pattern that matches number type tokens 
	 * \\d+  (\\.  \\d+)?   (E [+-]?  \\d+)?
	 * 1+ digits, optional . and 1+ digits, optional (E optional +/- and 1+ digits)
	 */
	static final Pattern TOKEN_NUMBER_PATTERN = Pattern.compile("^\\d+(\\.\\d+)?(E[+-]?\\d+)?");

	/**
	 * pattern that matches operator type tokens
	 * longer patterns must be listed first
	 */
	static final Pattern TOKEN_OPERATOR_PATTERN = Pattern.compile("^((<>)|(<=)|(>=)|(:=)|(==)|(DIV)|(MOD)|(OR)|(AND)|(,)|(\\()|(\\))|(;)|([-+/*=<>]))");
	
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
		
		//TODO: implement keyword logic along with symbol table
		/*
		if(token.getType()==Type.WORD && KEYWORDS.contains(lexeme_block) && lexeme_block.charAt(lexeme_block.length()-1)!='.'){
			throw new KeywordException("keyword "+lexeme_block+" on line "+line_number+" must be space delimited or end of program");
		}
		*/
		return token;
	}
}
