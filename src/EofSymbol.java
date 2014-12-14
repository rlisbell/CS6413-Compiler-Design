

public class EofSymbol extends Terminal {

	public EofSymbol() {
		
	}

	
	/**
	 * EOF terminals have no lexeme, empty string is the best way to represent this
	 */
	@Override
	public String getLexeme()
	{
		return "";
	}
	
	/**
	 * Print
	 */
	public String print(Token found){
		return String.format("%-30s", "End Of File");
	}


}
