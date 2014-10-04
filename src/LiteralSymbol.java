/**
 * class defines a symbol for a typed literal
 */
public class LiteralSymbol<T> extends ValueSymbol<T> {
	
	/**
	 * Value of the literal, should be Integer or Double (for now)
	 */
	T value;

	/**
	 * @param _lexeme
	 * @param _type
	 */
	public LiteralSymbol(String _lexeme, Type _type, T _value) {
		super(_lexeme, _type);
		value = _value;
	}

}
