
public class EofSymbol implements Terminal {

	public EofSymbol() {
		
	}

	/**
	 * EOF terminals have no lexeme, empty string is the best way to represent this
	 */
	@Override
	public String getLexeme() {
		return "";
	}

}
