/**
 * class defines a symbol for something that has a type
 */
public class ValueSymbol<T> extends LexemeTerminal {
	
	/**
	 * @param _lexeme
	 * @param _type
	 */
	public ValueSymbol(String _lexeme) {
		super(_lexeme);
	}

}
