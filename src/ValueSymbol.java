/**
 * 
 */

/**
 * @author bobboau
 * class defines a symbol for something that has a type
 *
 */
public class ValueSymbol<T> extends Symbol {
	
	/**
	 * @param _lexeme
	 * @param _type
	 */
	public ValueSymbol(String _lexeme, Type _type) {
		super(_lexeme, _type);
	}

}
