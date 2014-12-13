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
				switch(next_token.getLexeme()) {
					case "(":
						return Arrays.asList(
							new LexemeTerminal("("), 
							new NonTerminal(TYPE.ParameterList), 
							new LexemeTerminal(")")
						);
					case ";":
					case ":":
						return Arrays.asList();
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case CompoundStatement:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Declarations:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Expression:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ExpressionList:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ExpressionListPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ExpressionPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Factor:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case FactorPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case IdList:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case IdListPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case IdStatement:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case IdStatementPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case OptionalStatements:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ParameterList:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ParameterListPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case ProcedureStatement:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Program:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Sign:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case SimpleExpression:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case SimpleExpressionPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case StandardType:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Statement:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case StatementList:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case StatementListPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case SubprogramDeclaration:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case SubprogramDeclarations:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case SubprogramHead:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Term:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case TermPrime:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case Type:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			case VariableStatement:
				switch(next_token.getLexeme()) {
					default:
						throw new UnexpectedTokenException(next_token);
				}

			default:
				throw new UnexpectedTokenException(next_token);
		}
		return null;
	}

}
