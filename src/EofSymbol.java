
/**
 * special case class for dealing with the one terminal that is not born from a lexeme
 * @author Mike Abegg
 *
 */
public class EofSymbol extends Terminal {

	/**
	 * simple constructor
	 */
	public EofSymbol() {
		
	}

	
	/**
	 * EOF terminals have no lexeme, empty string is the best way to represent this
	 * could do null, but that has far to great a risk for problems
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
		return "End Of File";
	}


}
