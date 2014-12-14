/**
 * class defines a symbol for a typed literal
 */
public abstract class NumberSymbol extends LexemeTerminal {
	
	/**
	 * Value of the literal, should be Integer or Double (for now)
	 */
	Number value;

	/**
	 * @param _lexeme
	 * @param _type
	 */
	public NumberSymbol(String _lexeme, Number _value) {
		super(_lexeme);
		value = _value;
	}

}
