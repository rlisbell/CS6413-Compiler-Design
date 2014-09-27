import java.io.*;

public class Scanner {
	/**
	 * input giving us the source code
	 */
	private PushbackReader source;
	
	/**
	 * the line in the file we are currently on
	 */
	private int line_number;
	
	/**
	 * the column in the file we are currently in
	 */
	private int column_number;
	
	/**
	 * last char pulled from the file
	 */
	private char last_char;
	
	/**
	 * basic constructor, specifies the source code input stream
	 * @param _source
	 */
	public Scanner(PushbackReader _source) {
		source = _source;
		line_number = 0;
		column_number = 0;
		last_char = '\0';
	}
	
	/**
	 * Begins fetching next token
	 * @return
	 * @throws IOException
	 */
	public Token getNextToken() throws IOException{
		skipNonlexeme();
		int starting_line = line_number;
		int starting_column = column_number;
		String lexeme = getLexeme();
		return Token.makeToken(lexeme, starting_line, starting_column);
	}

	/**
	 * Builds the next lexeme
	 * @return
	 * @throws IOException
	 */
	private String getLexeme() throws IOException{
		StringBuilder lexeme = new StringBuilder();
		char new_char;
		
		while(isLexemeChar(new_char = getChar())){
			lexeme.append(new_char);
		}
		backOut(new_char);
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
		boolean skip = false;
		do{
			skip = 
						skipWhiteSpace()
					||
						skipNewlines()
					||
						skipComments();
		}
		while(skip);
	}
	
	/**
	 * moves past comments
	 * @return returns true if any comment was skipped
	 * @throws IOException 
	 */
	private boolean skipComments() throws IOException{
		
		boolean skip = false;
		char new_char;
		new_char = getChar();
		
		if(new_char == '{'){
			skip = true;
			do{
				//TODO: Throw error on {
			}while(getChar() != '}');
		}
		else{
			backOut(new_char);
		}
		return skip;
	}
	
	/**
	 * moves past whitespace
	 * @return returns true if any white space was skipped
	 * @throws IOException 
	 */
	private boolean skipWhiteSpace() throws IOException{
		boolean skip = false;
		char new_char;
		while(isWhiteSpace(new_char = getChar())){
			skip = true;
		}
		backOut(new_char);
		return skip;
	}
	
	/**
	 * moves past new lines
	 * @return returns true if any new lines were skipped
	 * @throws IOException 
	 */
	private boolean skipNewlines() throws IOException{
		boolean skip = false;
		char new_char;
		while(isNewline(new_char = getChar()));
		backOut(new_char);
		return skip;
	}

	/**
	 * determines newlines
	 * WARNING: Might not work properly with certain file formats?
	 * @param next_char
	 * @return returns true if the passed char is a newline character
	 */
	private static boolean isNewline(char next_char) {
		return next_char == '\n' || next_char == '\r';
	}

	/**
	 * determines whitespace
	 * TODO: Consider making this a whitelist instead of a blacklist
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
		next_char = (char) source.read();
		if(next_char == '\n' && last_char == '\r'){
			//windows newlines are screwy, \r\n should be treated as a single character
			next_char = (char) source.read();
		}
		last_char = next_char;
		if(isNewline(next_char)){
			line_number++;
			column_number = 0;
		}
		else{
			column_number++;
		}
		return next_char;
	}

	/**
	 * Moves reader pointer back by a character
	 * @throws IOException
	 */
	private void backOut(char c) throws IOException{
		source.unread((int) c);
		if(isNewline(c)){
			line_number--;
			//column_number = wtf who knows
		}else{
			column_number--;
		}
	}
}