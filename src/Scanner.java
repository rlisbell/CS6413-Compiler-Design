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
	 * last char pulled from the file
	 */
	private char last_char;
	
	/**
	 * we have read past the end of the file
	 */
	private boolean eof;
	
	/**
	 * basic constructor, specifies the source code input stream
	 * @param _source
	 */
	public Scanner(PushbackReader _source) {
		source = _source;
		line_number = 0;
		last_char = '\0';
		eof = false;
	}
	
	/**
	 * Begins fetching next token
	 * @return Token the next one in the file, or null if there were none
	 * @throws IOException
	 */
	public Token getNextToken() throws IOException{
		if(eof){
			return null;
		}
		skipNonlexeme();
		int starting_line = line_number;
		String lexeme = getLexeme();
		return Token.makeToken(lexeme, starting_line);
	}

	/**
	 * Builds the next lexeme
	 * @return
	 * @throws IOException
	 */
	private String getLexeme() throws IOException{
		StringBuilder lexeme = new StringBuilder();
		char new_char;
		
		while(isLexemeChar(new_char = getChar()) && !eof){
			lexeme.append(new_char);
		}
		if(!eof){
			backOut(new_char);
		}
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
			while(getChar() != '}' && !eof);//TODO: Throw error on {
		}
		else if(!eof){
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
		while(isWhiteSpace(new_char = getChar()) && !eof){
			skip = true;
		}
		if(!eof){
			backOut(new_char);
		}
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
		while(isNewline(new_char = getChar()) && !eof);
		if(!eof){
			backOut(new_char);
		}
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
		int next_char;
		
		next_char = source.read();
		
		//we check for end of line
		if(next_char == -1){
			eof = true;
		}
		
		//now we check for windows' messed up newlines
		if(next_char == '\n' && last_char == '\r'){
			//windows newlines are screwy, \r\n should be treated as a single character
			next_char = (char) source.read();
		}
		
		//new we record the old char so next time we can find out if it's a windows newline
		last_char = (char)next_char;
		
		//if we got any form of newline increment the line count
		if(isNewline(last_char)){
			line_number++;
		}
		
		return last_char;
	}

	/**
	 * Moves reader pointer back by a character
	 * @throws IOException
	 */
	private void backOut(char c) throws IOException{
		source.unread((int) c);
		
		//if we are pushing back a newline then we need to move the line count back
		if(isNewline(c)){
			line_number--;
		}
	}
}