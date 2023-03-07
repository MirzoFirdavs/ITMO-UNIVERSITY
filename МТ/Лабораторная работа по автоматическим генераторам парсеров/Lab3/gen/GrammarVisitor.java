// Generated from java-escape by ANTLR 4.11.1

import java.util.*;

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
	 * Visit a parse tree produced by {@link GrammarParser#parse}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParse(GrammarParser.ParseContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#str}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStr(GrammarParser.StrContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#assign}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssign(GrammarParser.AssignContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpr(GrammarParser.ExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#addAndSub}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddAndSub(GrammarParser.AddAndSubContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(GrammarParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#mulAndDiv}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulAndDiv(GrammarParser.MulAndDivContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#power}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPower(GrammarParser.PowerContext ctx);
	/**
	 * Visit a parse tree produced by {@link GrammarParser#unary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitUnary(GrammarParser.UnaryContext ctx);
}