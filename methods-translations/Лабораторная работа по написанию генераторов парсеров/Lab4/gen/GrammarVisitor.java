// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link GrammarParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface GrammarVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link GrammarParser#s}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitS(GrammarParser.SContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#nonTerminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNonTerminal(GrammarParser.NonTerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#arguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArguments(GrammarParser.ArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#synt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSynt(GrammarParser.SyntContext ctx);
	/**
	 * Visit a parse tree produced by the {@code eps}
	 * labeled alternative in {@link GrammarParser#epsilonOrRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitEps(GrammarParser.EpsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code notEps}
	 * labeled alternative in {@link GrammarParser#epsilonOrRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNotEps(GrammarParser.NotEpsContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ruleCode}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleCode(GrammarParser.RuleCodeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ruleNonTerminal}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleNonTerminal(GrammarParser.RuleNonTerminalContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ruleTerminal}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitRuleTerminal(GrammarParser.RuleTerminalContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerminal(GrammarParser.TerminalContext ctx);
}