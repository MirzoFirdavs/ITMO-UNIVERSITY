// Generated from java-escape by ANTLR 4.11.1

import java.util.*;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link GrammarParser}.
 */
public interface GrammarListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link GrammarParser#parse}.
	 * @param ctx the parse tree
	 */
	void enterParse(GrammarParser.ParseContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#parse}.
	 * @param ctx the parse tree
	 */
	void exitParse(GrammarParser.ParseContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#str}.
	 * @param ctx the parse tree
	 */
	void enterStr(GrammarParser.StrContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#str}.
	 * @param ctx the parse tree
	 */
	void exitStr(GrammarParser.StrContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#assign}.
	 * @param ctx the parse tree
	 */
	void enterAssign(GrammarParser.AssignContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#assign}.
	 * @param ctx the parse tree
	 */
	void exitAssign(GrammarParser.AssignContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void enterExpr(GrammarParser.ExprContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 */
	void exitExpr(GrammarParser.ExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#addAndSub}.
	 * @param ctx the parse tree
	 */
	void enterAddAndSub(GrammarParser.AddAndSubContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#addAndSub}.
	 * @param ctx the parse tree
	 */
	void exitAddAndSub(GrammarParser.AddAndSubContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(GrammarParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(GrammarParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#mulAndDiv}.
	 * @param ctx the parse tree
	 */
	void enterMulAndDiv(GrammarParser.MulAndDivContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#mulAndDiv}.
	 * @param ctx the parse tree
	 */
	void exitMulAndDiv(GrammarParser.MulAndDivContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#power}.
	 * @param ctx the parse tree
	 */
	void enterPower(GrammarParser.PowerContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#power}.
	 * @param ctx the parse tree
	 */
	void exitPower(GrammarParser.PowerContext ctx);
	/**
	 * Enter a parse tree produced by {@link GrammarParser#unary}.
	 * @param ctx the parse tree
	 */
	void enterUnary(GrammarParser.UnaryContext ctx);
	/**
	 * Exit a parse tree produced by {@link GrammarParser#unary}.
	 * @param ctx the parse tree
	 */
	void exitUnary(GrammarParser.UnaryContext ctx);
}