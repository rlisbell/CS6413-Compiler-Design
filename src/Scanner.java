import java.io.*;

/**
 * Class for interpreting file as lexemes
 * @author Ryan
 */
public class Scanner {

	/*********************\
	|* custom exceptions *| 
	\*********************/
	
	/**
	 * @author bobboau
	 *
	 * common base for all scanner exceptions
	 */
	public class ScannerException extends Exception {
		private static final long serialVersionUID = 3357151207683981122L;

		public ScannerException(String message){
			super(message);
		}
	}
	
	/**
	 * @author bobboau
	 *
	 * thrown when the file ends while in a comment
	 */
	public class CommentNotClosedException extends ScannerException {
		private static final long serialVersionUID = -6249589749885372084L;

		public CommentNotClosedException(String message){
			super(message);
		}
	}
	
	/**
	 * @author bobboau
	 *
	 * thrown when there is another comment inside a comment
	 */
	public class RecursiveCommentException extends ScannerException {
		private static final long serialVersionUID = 4083771233656858205L;

		public RecursiveCommentException(String message){
			super(message);
		}
	}
	
	/**
	 * thrown when there is an apparent lexeme of a certain type that we cannot parse for some reason
	 */
	public class LexemeLiteralInvalidException extends ScannerException {
		private static final long serialVersionUID = -6527222350803000048L;

		public LexemeLiteralInvalidException(String message){
			super(message);
		}
	}
	 
	
	/**********************\
	|* private properties *|
	\**********************/
	
	
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
	 * current chunk of lexemes
	 */
	private StringBuilder lexeme_chunk;
	
	/**
	 * we have read past the end of the file
	 */
	private boolean eof;
	
	
	/****************\
	|* constructors *|
	\****************/
	
	/**
	 * basic constructor, specifies the source code input stream
	 * @param _source
	 */
	public Scanner(PushbackReader _source) {
		source = _source;
		line_number = 1;
		last_char = '\0';
		lexeme_chunk = new StringBuilder();
		eof = false;
	}
	
	
	/******************\
	|* public methods *|
	\******************/
	
	/**
	 * Begins fetching next token
	 * @return Token the next one in the file, or null if there were none
	 * @throws IOException
	 * @throws ScannerException 
	 * @throws KeywordException 
	 */
	public Token getNextToken() throws IOException, ScannerException, Token.KeywordException{
		boolean try_keywords = false;
		//if our current chunk is exhausted get a new one
		if(lexeme_chunk.length()==0){
			if(eof){
				return null;
			}
			
			//skip past comments, whitespace, and newlines
			skipNonlexeme();
			
			//get the next chunk of interesting text from the file
			getNextLexemeChunk();
			
			//this is a fresh chunk, so we should try to check keywords
			try_keywords = true;
		}
		
		//call the Token class' factory method on this chunk
		Token found_token = Token.makeToken(lexeme_chunk.toString(), line_number, try_keywords);
		
		//consume the characters found by the token factory
		lexeme_chunk.delete(0, found_token.getLexeme().length());
		
		//return the found token
		return found_token;
	}
	
	
	/*******************\
	|* private methods *|
	\*******************/

	/**
	 * Builds the next lexeme chunk
	 * "chunk" here means there may possibly be multiple lexemes in the result
	 * @return
	 * @throws IOException
	 */
	private void getNextLexemeChunk() throws IOException{
		char new_char;
		while(isLexemeChar(new_char = getChar()) && !eof){
			lexeme_chunk.append(new_char);
		}
		if(!eof){
			backOut(new_char);
		}
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
	 * @throws ScannerException 
	 */
	private void skipNonlexeme() throws IOException, ScannerException {
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
	 * @throws ScannerException 
	 */
	private boolean skipComments() throws IOException, ScannerException{
		boolean skip = false;
		char new_char;
		new_char = getChar();
		
		int starting_line = line_number;
		if(new_char == '{'){
			//we did something
			skip = true;
			//skip everything until we find a close something
			while((new_char = getChar()) != '}' && !eof){
				if(new_char == '{'){
					//you cannot start another comment inside a comment
					throw new RecursiveCommentException("comment started on line "+starting_line+" has another comment starting on line "+line_number);
				}
			}
		}
		else{
			if(eof){
				//we never found the end of the comment
				throw new CommentNotClosedException("comment started on line "+starting_line+" not closed before end of file");
			}
			else{
				backOut(new_char);
			}
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
		while(isNewline(new_char = getChar()) && !eof){
			skip = true;
		}
		if(!eof){
			backOut(new_char);
		}
		return skip;
	}

	
	/**
	 * determines newlines
	 * NOTE: windows newlines (\r\n) are a special case handled in getChar
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
		
		//get the next char
		next_char = source.read();
		
		//we check for end of line
		if(next_char == -1){
			eof = true;
			//we shouldn't be using anything if we're at EOF
			return '\0';
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
	 * Pushes character back onto the reader
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