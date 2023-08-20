// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#s}.
	 * @param ctx the parse tree
	 */
	void enterS(GrammarParser.SContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#s}.
	 * @param ctx the parse tree
	 */
	void exitS(GrammarParser.SContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#nonTerminal}.
	 * @param ctx the parse tree
	 */
	void enterNonTerminal(GrammarParser.NonTerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#nonTerminal}.
	 * @param ctx the parse tree
	 */
	void exitNonTerminal(GrammarParser.NonTerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(GrammarParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(GrammarParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#synt}.
	 * @param ctx the parse tree
	 */
	void enterSynt(GrammarParser.SyntContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#synt}.
	 * @param ctx the parse tree
	 */
	void exitSynt(GrammarParser.SyntContext ctx);
	/**
	 * Enter a parse tree produced by the {@code eps}
	 * labeled alternative in {@link GrammarParser#epsilonOrRule}.
	 * @param ctx the parse tree
	 */
	void enterEps(GrammarParser.EpsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code eps}
	 * labeled alternative in {@link GrammarParser#epsilonOrRule}.
	 * @param ctx the parse tree
	 */
	void exitEps(GrammarParser.EpsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code notEps}
	 * labeled alternative in {@link GrammarParser#epsilonOrRule}.
	 * @param ctx the parse tree
	 */
	void enterNotEps(GrammarParser.NotEpsContext ctx);
	/**
	 * Exit a parse tree produced by the {@code notEps}
	 * labeled alternative in {@link GrammarParser#epsilonOrRule}.
	 * @param ctx the parse tree
	 */
	void exitNotEps(GrammarParser.NotEpsContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ruleCode}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void enterRuleCode(GrammarParser.RuleCodeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ruleCode}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void exitRuleCode(GrammarParser.RuleCodeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ruleNonTerminal}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void enterRuleNonTerminal(GrammarParser.RuleNonTerminalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ruleNonTerminal}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void exitRuleNonTerminal(GrammarParser.RuleNonTerminalContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ruleTerminal}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void enterRuleTerminal(GrammarParser.RuleTerminalContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ruleTerminal}
	 * labeled alternative in {@link GrammarParser#nonTerminalRule}.
	 * @param ctx the parse tree
	 */
	void exitRuleTerminal(GrammarParser.RuleTerminalContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void enterTerminal(GrammarParser.TerminalContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#terminal}.
	 * @param ctx the parse tree
	 */
	void exitTerminal(GrammarParser.TerminalContext ctx);
}