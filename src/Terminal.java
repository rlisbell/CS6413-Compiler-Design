import java.util.LinkedList;
import java.util.List;


public abstract class Terminal extends Symbol {

	/**
	 * gets the file contents that generated this terminal
	 * @return
	 */
	public abstract String getLexeme();

	/**
	 * Equivalence
	 */
	@Override
	public boolean equals(Symbol other){
		if(other.getClass().equals(getClass())){
			 return ((Terminal)other).getLexeme().equals(getLexeme());
		}
		else if(other instanceof AnySymbolOfClass){
			return other.equals(this);
		}
		else{
			return false;
		}
	}


	/**
	 * returns an empty list, throws an exception if the passed token does not match
	 * @param next_token the token most recently pulled from the source file
	 */
	@Override
	public List<Symbol> getProduction(Token next_token)
			throws UnexpectedTokenException
	{
		if(!next_token.getTerminalClass().getClass().equals(getClass()) || !next_token.getLexeme().equals(getLexeme())){
			throw new UnexpectedTokenException(next_token);
		}
		return new LinkedList<Symbol>();
	}
}
