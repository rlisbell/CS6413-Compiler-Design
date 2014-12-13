import java.util.Arrays;
import java.util.List;

/**
 * Represents a NonTerminal
 * Handles productions logic for them
 */
public class NonTerminal implements Symbol {
	
	private enum TYPE {
		Program,
		IdList,
		IdListPrime,
		Declarations,
		Type,
		StandardType,
		SubprogramDeclarations,
		SubprogramDeclaration,
		SubprogramHead,
		Arguments,
		ParameterList,
		ParameterListPrime,
		CompoundStatement,
		OptionalStatements,
		StatementList,
		StatementListPrime,
		Statement,
		IdStatement,
		IdStatementPrime,
		VariableStatement,
		ProcedureStatement,
		ExpressionList,
		ExpressionListPrime,
		Expression,
		ExpressionPrime,
		SimpleExpression,
		SimpleExpressionPrime,
		Term,
		TermPrime,
		Factor,
		FactorPrime,
		Sign
	}
	
	private TYPE type;
	
	public NonTerminal(TYPE type) {
		this.type = type;
	}

	public List<Symbol> getProduction(Token next_token) throws UnexpectedTokenException {
		switch(this.type) {
			case Arguments:
				if(next_token.equals(new LexemeTerminal("("))) {
					return Arrays.asList(
						(Symbol)new LexemeTerminal("("), 
						(Symbol)new NonTerminal(TYPE.ParameterList), 
						(Symbol)new LexemeTerminal(")")
					);
				}
				else if(next_token.equals(new LexemeTerminal(";")) || next_token.equals(new LexemeTerminal(":"))) {
					return Arrays.asList();
				}

			case CompoundStatement:
				switch(next_token.getLexeme()) {
					case "begin":
						return Arrays.asList(
							(Symbol)new LexemeTerminal("begin"),
							(Symbol)new NonTerminal(TYPE.OptionalStatements),
							(Symbol)new LexemeTerminal("end")
						);
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Declarations:
				switch(next_token.getLexeme()) {
					case "var":
						return Arrays.asList(
							(Symbol)new LexemeTerminal("var"),
							(Symbol)new NonTerminal(TYPE.IdList),
							(Symbol)new LexemeTerminal(":"),
							(Symbol)new NonTerminal(TYPE.Type),
							(Symbol)new LexemeTerminal(";"),
							(Symbol)new NonTerminal(TYPE.Declarations)
						);
					case "function":
					case "procedure":
					case "begin":
						return Arrays.asList();
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Expression:
				switch(next_token.getLexeme()) {
					case id:
					case num:
					case "(":
					case "not":
					case "+":
					case "-":
						return Arrays.asList(
							(Symbol)new NonTerminal(TYPE.SimpleExpression),
							(Symbol)new NonTerminal(TYPE.ExpressionPrime)
						);
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ExpressionList:
				switch(next_token.getLexeme()) {
					case "id":
					case "id":
					case "id":
					case "id":
					case "id":
					case "id":
						return Arrays.asList(
							new LexemeTerminal("var"),
							new NonTerminal(TYPE.IdList),
							new LexemeTerminal(":"),
							new NonTerminal(TYPE.Type),
							new LexemeTerminal(";"),
							new NonTerminal(TYPE.Declarations)
						);
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ExpressionListPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case ExpressionPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case Factor:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case FactorPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case IdList:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case IdListPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case IdStatement:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case IdStatementPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case OptionalStatements:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case ParameterList:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case ParameterListPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case ProcedureStatement:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case Program:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case Sign:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case SimpleExpression:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case SimpleExpressionPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case StandardType:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case Statement:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case StatementList:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case StatementListPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case SubprogramDeclaration:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case SubprogramDeclarations:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case SubprogramHead:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case Term:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case TermPrime:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case Type:
				if(next_token.equals() {
					return Arrays.asList();
				}

			case VariableStatement:
				if(next_token.equals() {
					return Arrays.asList();
				}

			default:
				throw new UnexpectedTokenException(next_token);
		}
	}

	/**
	 * equivilence
	 */
	@Override
	public boolean equals(Symbol other) {
		if(other.getClass().equals(getClass())){
			 return ((NonTerminal)other).type.equals(type);
		}
		else{
			return false;
		}
	}

}
