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
	 * common base for all Symbol exceptions
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
	 * Equivalence
	 */
	public abstract boolean equals(Symbol other);
	
	
	/**
	 * due to the way that equivalence works between AnySymbolOfClass and everything else any symbol can be equivalent to any other symbol
	 * so we do this to force HashMap into it's conflict resolution mode full time
	 */
	public int hashCode(){
		return 0;
	}
}
