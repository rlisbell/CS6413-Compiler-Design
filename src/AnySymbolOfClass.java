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
public class AnySymbolOfClass extends Symbol {
	
	/**
	 * the base type of the class we expect the token to be of
	 */
	private Class<? extends Symbol> base_class;
	
	public String print(Token found) {
		//30 column aligned for prettification
		return base_class.getName()+": '"+found.print()+"'";
	}

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

	/**
	 * equivilence
	 */
	@Override
	public boolean equals(Symbol other) {
		if(other.getClass().equals(getClass())){
			 return ((AnySymbolOfClass)other).base_class.equals(base_class);
		}
		else{
			return base_class.isAssignableFrom(other.getClass());
		}
	}

	@Override
	public boolean shouldGetToken() {
		if(Terminal.class.isAssignableFrom(base_class)){
			return true;
		}
		if(NonTerminal.class.isAssignableFrom(base_class)){
			return false;
		}
		return false;
	}

}
