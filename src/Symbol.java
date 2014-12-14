import java.util.List;

/**
 * @author bobboau
 *
 */
public abstract class Symbol {
	
	/**
	 * common base for all Symbol exceptions
	 */
	public static class SymbolException extends Exception {
		private static final long serialVersionUID = -4457162023556548921L;

		public SymbolException(String message){
			super(message);
		}
	}
	
	/**
	 * error thrown when we find a token we were not expecting
	 */
	public static class UnexpectedTokenException extends SymbolException {
		private static final long serialVersionUID = -515908850221632681L;

		public UnexpectedTokenException(Token unexpected_token){
			super("Found Unexpected Token '"+unexpected_token.getLexeme()+"' on line: #"+unexpected_token.getLineNumber());
		}
	}
	
	/**
	 * returns a list of Symbols that this Symbol can be replaced with if the passed token is found
	 * throws an exception when the passed token is unexpected (not in non-terminal parse table or does not match for terminals)
	 * @param next_token -- the token we just pulled out of the source code
	 */
	public abstract List<Symbol> getProduction(Token next_token) throws UnexpectedTokenException;
	

	/**
	 * Print appropriate name/value for the symbol
	 * @return
	 */
	public String print(){
		return print(null);
	}

	/**
	 * Print appropriate name/value for the symbol
	 * given an optional found token
	 * @return
	 */
	public abstract String print(Token found);
	
	
	/**
	 * Equivalence
	 */
	public abstract boolean equals(Symbol other);
	
	/**
	 * Equivalence
	 */
	@Override
	public boolean equals(Object other){
		if(other instanceof Symbol){
			return equals((Symbol)other);
		}
		return false;
	}
	
	
	/**
	 * due to the way that equivalence works between AnySymbolOfClass and everything else any symbol can be equivalent to any other symbol
	 * so we do this to force HashMap into it's conflict resolution mode full time
	 */
	@Override
	public int hashCode(){
		return 0;
	}
	
	/**
	 * tells if this symbol when pulled from the stack should result in pulling a token from the source file
	 */
	abstract public boolean shouldGetToken();
}
