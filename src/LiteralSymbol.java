/**
 * class defines a symbol for a typed literal
 */
public class LiteralSymbol<T> extends LexemeTerminal implements ValueSymbol<T> {
	
	/**
	 * Value of the literal, should be Integer or Double (for now)
	 */
	T value;

	/**
	 * @param _lexeme
	 * @param _type
	 */
	public LiteralSymbol(String _lexeme, T _value) {
		super(_lexeme);
		value = _value;
	}

	@Override
	public String getType() {
		return value.getClass().getName();
	}

}
