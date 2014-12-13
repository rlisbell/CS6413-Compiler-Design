import java.util.List;


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

}
