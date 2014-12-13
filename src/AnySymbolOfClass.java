import java.util.LinkedList;
import java.util.List;

/**
 * This is a sort of metasymbol that counts as a place holder for another symbol
 * where this is used is when we push expected symbols on the parse stack we push one of these, 
 * then when we compare the found symbol (terminal in practice) it will throw an error if the 
 * found token is not of the class this class is looking for.
 * @author bobboau
 *
 */
public class AnySymbolOfClass implements Symbol {
	
	/**
	 * the base type of the class we expect the token to be of
	 */
	private Class<? extends Symbol> base_class;

	public AnySymbolOfClass(Class<? extends Symbol> _base_class) {
		base_class = _base_class;
	}

	@Override
	public List<Symbol> getProduction(Token next_token)
			throws UnexpectedTokenException
	{
		if(!base_class.isAssignableFrom(next_token.getTerminalClass())){
			throw new UnexpectedTokenException(next_token);
		}
		return new LinkedList<Symbol>();
	}

}