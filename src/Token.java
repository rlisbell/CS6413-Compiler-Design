/**
 * class that represents a chunk of source code
 * the smallest atomic chunk that might have any meaning
 * class is immutable
 * @author bobboau
 */
public class Token {
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
	 * which column on the source line did this token start on
	 */
	private final int column_number;
	
	
	/**
	 * basic constructor
	 * @param _type
	 * @param _lexeme
	 */
	public Token(Type _type, String _lexeme, int _line_number, int _column_number){
		type = _type;
		lexeme = _lexeme;
		line_number = _line_number;
		column_number = _column_number;
	}
	
	/**
	 * @return the column_number
	 */
	public int getColumnNumber() {
		return column_number;
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
}
