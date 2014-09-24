import java.io.*;
import java.util.regex.*;

public class Scanner {
	
	//maybe this token stuff belongs in the token class?
	/**
	 * pattern that matches word type tokens 
	 */
	static final Pattern TOKEN_WORD_PATERN = Pattern.compile("^\\w[\\w\\d_]*$");

	/**
	 * pattern that matches word type tokens 
	 */
	static final Pattern TOKEN_NUMBER_PATERN = Pattern.compile("^\\d+$");

	/**
	 * pattern that matches word type tokens 
	 */
	static final Pattern TOKEN_OPERATOR_PATERN = Pattern.compile("^[-+/*=:]+$");
	
	/**
	 * input giving us the source code
	 */
	private BufferedReader source;
	
	
	/**
	 * the line in the file we are currently on
	 */
	private int line_number;
	
	
	/**
	 * the column in the file we are currently in
	 */
	private int column_number;
	
	/**
	 * basic constructor, specifies the source code input stream
	 * @param _source
	 */
	public Scanner(BufferedReader _source) {
		source = _source;
		line_number = 0;
		column_number = 0;
	}
	
	public Token getNextToken() throws IOException{
		source.mark(1);
		skipNonlexeme();
		int starting_line = line_number;
		int starting_column = column_number;
		String lexeme = getLexeme();
		return new Token(determineTokenType(lexeme), lexeme, starting_line, starting_column);
	}
	
	//this really feels like it might belong in the Token class
	/**
	 * determines what type of token is in the lexeme
	 * @param lexeme
	 * @return
	 */
	private Token.Type determineTokenType(String lexeme) {
		if(TOKEN_WORD_PATERN.matcher(lexeme).matches()){
			return Token.Type.WORD;
		}
		else if(TOKEN_NUMBER_PATERN.matcher(lexeme).matches()){
			return Token.Type.NUMBER;
		}
		else if(TOKEN_OPERATOR_PATERN.matcher(lexeme).matches()){
			return Token.Type.OPERATOR;
		}
		else{
			return Token.Type.ERROR;
		}
	}

	private String getLexeme() throws IOException{
		StringBuilder lexeme = new StringBuilder();
		char new_char;
		
		while(isLexemeChar(new_char = getChar())){
			lexeme.append(new_char);
		}
		backOut();
		return lexeme.toString();
	}
	
	/**
	 * determines possible lexeme chars
	 * @param next_char
	 * @return true if the character seems like it might be a lexeme character
	 */
	private static boolean isLexemeChar(char next_char) {
		return
				next_char != '{' 
			&& 
				!isWhiteSpace(next_char)
			&&
				!isNewline(next_char);
	}

	/**
	 * moves past everything that is not part of the next useful lexeme
	 * @throws IOException 
	 */
	private void skipNonlexeme() throws IOException {
		boolean did_anything = false;
		do{
			did_anything = 
						skipWhiteSpace()
					||
						skipNewlines()
					||
						skipComments();
		}
		while(did_anything);
	}

	
	/**
	 * moves past comments
	 * @return returns true if any comment was skipped
	 * @throws IOException 
	 */
	private boolean skipComments() throws IOException{
		
		boolean did_anything = false;
		char new_char;
		new_char = getChar();
		
		if(new_char == '{'){
			did_anything = true;
			do{
				skipNewlines();
			}while(getChar() != '}');
		}
		else{
			backOut();
		}
		return did_anything;
	}

	
	/**
	 * moves past whitespace
	 * @return returns true if any white space was skipped
	 * @throws IOException 
	 */
	private boolean skipWhiteSpace() throws IOException{
		boolean did_anything = false;
		while(isWhiteSpace(getChar())){
			did_anything = true;
		}
		backOut();
		return did_anything;
	}

	
	/**
	 * moves past new lines
	 * @return returns true if any new lines were skipped
	 * @throws IOException 
	 */
	private boolean skipNewlines() throws IOException{
		boolean did_anything = false;
		while(isNewline(getChar())){
			if(!did_anything){
				line_number++;
				column_number = 0;
			}
			did_anything = true;
		}
		backOut();
		return did_anything;
	}


	/**
	 * determines newlines
	 * @param next_char
	 * @return returns true if the passed char is a newline character
	 */
	private static boolean isNewline(char next_char) {
		return next_char == '\n' || next_char == '\r';
	}

	
	/**
	 * determines whitespace
	 * @param next_char
	 * @return returns true if the passed char is a white space character
	 */
	private static boolean isWhiteSpace(char next_char) {
		return next_char == ' ' || next_char == '\t';
	}
	
	
	/**
	 * gets the next character marks it in case we need to backtrack and moves past it
	 * @return
	 * @throws IOException
	 */
	private char getChar() throws IOException {
		char next_char;
		source.mark(1);
		next_char = (char) source.read();
		column_number++;
		return next_char;
	}
	
	private void backOut() throws IOException{
		source.reset();
		column_number--;
	}
	
}