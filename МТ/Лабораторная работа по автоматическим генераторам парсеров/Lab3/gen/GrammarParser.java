// Generated from java-escape by ANTLR 4.11.1

import java.util.*;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class GrammarParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		Number=10, Variable=11, Whitespace=12;
	public static final int
		RULE_parse = 0, RULE_str = 1, RULE_assign = 2, RULE_expr = 3, RULE_addAndSub = 4, 
		RULE_term = 5, RULE_mulAndDiv = 6, RULE_power = 7, RULE_unary = 8;
	private static String[] makeRuleNames() {
		return new String[] {
			"parse", "str", "assign", "expr", "addAndSub", "term", "mulAndDiv", "power", 
			"unary"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'='", "';'", "'+'", "'-'", "'*'", "'/'", "'**'", "'('", "')'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, "Number", 
			"Variable", "Whitespace"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "java-escape"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public GrammarParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ParseContext extends ParserRuleContext {
		public StringBuilder res;
		public StrContext str() {
			return getRuleContext(StrContext.class,0);
		}
		public TerminalNode EOF() { return getToken(GrammarParser.EOF, 0); }
		public ParseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_parse; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterParse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitParse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitParse(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ParseContext parse() throws RecognitionException {
		ParseContext _localctx = new ParseContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_parse);

		    Map<String, Integer> vars = new HashMap<>();
		    StringBuilder res = new StringBuilder();
		    ((ParseContext)_localctx).res =  res;

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(18);
			str(vars, res);
			setState(19);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StrContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public StringBuilder res;
		public AssignContext assign() {
			return getRuleContext(AssignContext.class,0);
		}
		public StrContext str() {
			return getRuleContext(StrContext.class,0);
		}
		public StrContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public StrContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars, StringBuilder res) {
			super(parent, invokingState);
			this.vars = vars;
			this.res = res;
		}
		@Override public int getRuleIndex() { return RULE_str; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterStr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitStr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitStr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StrContext str(Map <String, Integer> vars,StringBuilder res) throws RecognitionException {
		StrContext _localctx = new StrContext(_ctx, getState(), vars, res);
		enterRule(_localctx, 2, RULE_str);
		try {
			setState(25);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case Variable:
				enterOuterAlt(_localctx, 1);
				{
				setState(21);
				assign(vars, res);
				setState(22);
				str(vars, res);
				}
				break;
			case EOF:
				enterOuterAlt(_localctx, 2);
				{
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AssignContext extends ParserRuleContext {
		public Map<String, Integer> vars;
		public StringBuilder res;
		public Token variable;
		public ExprContext expr;
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Variable() { return getToken(GrammarParser.Variable, 0); }
		public AssignContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AssignContext(ParserRuleContext parent, int invokingState, Map<String, Integer> vars, StringBuilder res) {
			super(parent, invokingState);
			this.vars = vars;
			this.res = res;
		}
		@Override public int getRuleIndex() { return RULE_assign; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterAssign(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitAssign(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitAssign(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AssignContext assign(Map<String, Integer> vars,StringBuilder res) throws RecognitionException {
		AssignContext _localctx = new AssignContext(_ctx, getState(), vars, res);
		enterRule(_localctx, 4, RULE_assign);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(27);
			((AssignContext)_localctx).variable = match(Variable);
			setState(28);
			match(T__0);
			setState(29);
			((AssignContext)_localctx).expr = expr(vars);
			setState(30);
			match(T__1);

			        _localctx.vars.put((((AssignContext)_localctx).variable!=null?((AssignContext)_localctx).variable.getText():null), ((AssignContext)_localctx).expr.val);
			        _localctx.res.append((((AssignContext)_localctx).variable!=null?((AssignContext)_localctx).variable.getText():null) + " = " + ((AssignContext)_localctx).expr.val + ";" + '\n');
			    
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExprContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public int val;
		public TermContext term;
		public AddAndSubContext addAndSub;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public AddAndSubContext addAndSub() {
			return getRuleContext(AddAndSubContext.class,0);
		}
		public ExprContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public ExprContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars) {
			super(parent, invokingState);
			this.vars = vars;
		}
		@Override public int getRuleIndex() { return RULE_expr; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExprContext expr(Map <String, Integer> vars) throws RecognitionException {
		ExprContext _localctx = new ExprContext(_ctx, getState(), vars);
		enterRule(_localctx, 6, RULE_expr);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(33);
			((ExprContext)_localctx).term = term(vars);
			setState(34);
			((ExprContext)_localctx).addAndSub = addAndSub(vars, ((ExprContext)_localctx).term.val);
			 ((ExprContext)_localctx).val =  ((ExprContext)_localctx).addAndSub.val; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AddAndSubContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public int acc;
		public int val;
		public TermContext term;
		public AddAndSubContext addAndSub;
		public TermContext term() {
			return getRuleContext(TermContext.class,0);
		}
		public AddAndSubContext addAndSub() {
			return getRuleContext(AddAndSubContext.class,0);
		}
		public AddAndSubContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public AddAndSubContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars, int acc) {
			super(parent, invokingState);
			this.vars = vars;
			this.acc = acc;
		}
		@Override public int getRuleIndex() { return RULE_addAndSub; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterAddAndSub(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitAddAndSub(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitAddAndSub(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AddAndSubContext addAndSub(Map <String, Integer> vars,int acc) throws RecognitionException {
		AddAndSubContext _localctx = new AddAndSubContext(_ctx, getState(), vars, acc);
		enterRule(_localctx, 8, RULE_addAndSub);
		try {
			setState(50);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__2:
				enterOuterAlt(_localctx, 1);
				{
				setState(37);
				match(T__2);
				setState(38);
				((AddAndSubContext)_localctx).term = term(vars);
				 _localctx.acc += ((AddAndSubContext)_localctx).term.val; 
				setState(40);
				((AddAndSubContext)_localctx).addAndSub = addAndSub(vars, _localctx.acc);
				 ((AddAndSubContext)_localctx).val =  ((AddAndSubContext)_localctx).addAndSub.val; 
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 2);
				{
				setState(43);
				match(T__3);
				setState(44);
				((AddAndSubContext)_localctx).term = term(vars);
				 _localctx.acc -= ((AddAndSubContext)_localctx).term.val; 
				setState(46);
				((AddAndSubContext)_localctx).addAndSub = addAndSub(vars, _localctx.acc);
				 ((AddAndSubContext)_localctx).val =  ((AddAndSubContext)_localctx).addAndSub.val; 
				}
				break;
			case T__1:
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				 ((AddAndSubContext)_localctx).val =  _localctx.acc; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public int val;
		public PowerContext power;
		public MulAndDivContext mulAndDiv;
		public PowerContext power() {
			return getRuleContext(PowerContext.class,0);
		}
		public MulAndDivContext mulAndDiv() {
			return getRuleContext(MulAndDivContext.class,0);
		}
		public TermContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TermContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars) {
			super(parent, invokingState);
			this.vars = vars;
		}
		@Override public int getRuleIndex() { return RULE_term; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterTerm(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitTerm(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTerm(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term(Map <String, Integer> vars) throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState(), vars);
		enterRule(_localctx, 10, RULE_term);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(52);
			((TermContext)_localctx).power = power(vars);
			setState(53);
			((TermContext)_localctx).mulAndDiv = mulAndDiv(vars, ((TermContext)_localctx).power.val);
			 ((TermContext)_localctx).val =  ((TermContext)_localctx).mulAndDiv.val; 
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class MulAndDivContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public int acc;
		public int val;
		public PowerContext power;
		public MulAndDivContext mulAndDiv;
		public PowerContext power() {
			return getRuleContext(PowerContext.class,0);
		}
		public MulAndDivContext mulAndDiv() {
			return getRuleContext(MulAndDivContext.class,0);
		}
		public MulAndDivContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public MulAndDivContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars, int acc) {
			super(parent, invokingState);
			this.vars = vars;
			this.acc = acc;
		}
		@Override public int getRuleIndex() { return RULE_mulAndDiv; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterMulAndDiv(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitMulAndDiv(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitMulAndDiv(this);
			else return visitor.visitChildren(this);
		}
	}

	public final MulAndDivContext mulAndDiv(Map <String, Integer> vars,int acc) throws RecognitionException {
		MulAndDivContext _localctx = new MulAndDivContext(_ctx, getState(), vars, acc);
		enterRule(_localctx, 12, RULE_mulAndDiv);
		try {
			setState(69);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__4:
				enterOuterAlt(_localctx, 1);
				{
				setState(56);
				match(T__4);
				setState(57);
				((MulAndDivContext)_localctx).power = power(vars);
				 _localctx.acc *= ((MulAndDivContext)_localctx).power.val; 
				setState(59);
				((MulAndDivContext)_localctx).mulAndDiv = mulAndDiv(vars, _localctx.acc);
				 ((MulAndDivContext)_localctx).val =  ((MulAndDivContext)_localctx).mulAndDiv.val; 
				}
				break;
			case T__5:
				enterOuterAlt(_localctx, 2);
				{
				setState(62);
				match(T__5);
				setState(63);
				((MulAndDivContext)_localctx).power = power(vars);
				 _localctx.acc /= ((MulAndDivContext)_localctx).power.val; 
				setState(65);
				((MulAndDivContext)_localctx).mulAndDiv = mulAndDiv(vars, _localctx.acc);
				 ((MulAndDivContext)_localctx).val =  ((MulAndDivContext)_localctx).mulAndDiv.val; 
				}
				break;
			case T__1:
			case T__2:
			case T__3:
			case T__8:
				enterOuterAlt(_localctx, 3);
				{
				 ((MulAndDivContext)_localctx).val =  _localctx.acc; 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PowerContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public int val;
		public UnaryContext unary;
		public PowerContext power;
		public UnaryContext unary() {
			return getRuleContext(UnaryContext.class,0);
		}
		public PowerContext power() {
			return getRuleContext(PowerContext.class,0);
		}
		public PowerContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public PowerContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars) {
			super(parent, invokingState);
			this.vars = vars;
		}
		@Override public int getRuleIndex() { return RULE_power; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterPower(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitPower(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitPower(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PowerContext power(Map <String, Integer> vars) throws RecognitionException {
		PowerContext _localctx = new PowerContext(_ctx, getState(), vars);
		enterRule(_localctx, 14, RULE_power);
		try {
			setState(79);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				setState(71);
				((PowerContext)_localctx).unary = unary(vars);
				setState(72);
				match(T__6);
				setState(73);
				((PowerContext)_localctx).power = power(vars);
				 ((PowerContext)_localctx).val =  (int) Math.pow(((PowerContext)_localctx).unary.val, ((PowerContext)_localctx).power.val); 
				}
				break;
			case 2:
				enterOuterAlt(_localctx, 2);
				{
				setState(76);
				((PowerContext)_localctx).unary = unary(vars);
				 ((PowerContext)_localctx).val =  ((PowerContext)_localctx).unary.val; 
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class UnaryContext extends ParserRuleContext {
		public Map <String, Integer> vars;
		public int val;
		public UnaryContext unary;
		public ExprContext expr;
		public Token number;
		public Token variable;
		public UnaryContext unary() {
			return getRuleContext(UnaryContext.class,0);
		}
		public ExprContext expr() {
			return getRuleContext(ExprContext.class,0);
		}
		public TerminalNode Number() { return getToken(GrammarParser.Number, 0); }
		public TerminalNode Variable() { return getToken(GrammarParser.Variable, 0); }
		public UnaryContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public UnaryContext(ParserRuleContext parent, int invokingState, Map <String, Integer> vars) {
			super(parent, invokingState);
			this.vars = vars;
		}
		@Override public int getRuleIndex() { return RULE_unary; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterUnary(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitUnary(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitUnary(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UnaryContext unary(Map <String, Integer> vars) throws RecognitionException {
		UnaryContext _localctx = new UnaryContext(_ctx, getState(), vars);
		enterRule(_localctx, 16, RULE_unary);
		try {
			setState(94);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(81);
				match(T__3);
				setState(82);
				((UnaryContext)_localctx).unary = unary(vars);
				 ((UnaryContext)_localctx).val =  -((UnaryContext)_localctx).unary.val; 
				}
				break;
			case T__7:
				enterOuterAlt(_localctx, 2);
				{
				setState(85);
				match(T__7);
				setState(86);
				((UnaryContext)_localctx).expr = expr(vars);
				setState(87);
				match(T__8);
				 ((UnaryContext)_localctx).val =  ((UnaryContext)_localctx).expr.val; 
				}
				break;
			case Number:
				enterOuterAlt(_localctx, 3);
				{
				setState(90);
				((UnaryContext)_localctx).number = match(Number);
				 ((UnaryContext)_localctx).val =  Integer.parseInt((((UnaryContext)_localctx).number!=null?((UnaryContext)_localctx).number.getText():null)); 
				}
				break;
			case Variable:
				enterOuterAlt(_localctx, 4);
				{
				setState(92);
				((UnaryContext)_localctx).variable = match(Variable);
				 ((UnaryContext)_localctx).val =  _localctx.vars.get((((UnaryContext)_localctx).variable!=null?((UnaryContext)_localctx).variable.getText():null)); 
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\u0004\u0001\fa\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0003\u0001\u001a\b\u0001\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0003\u00043\b\u0004\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006\u0003\u0006F\b\u0006"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0003\u0007P\b\u0007\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0003\b_\b\b\u0001\b\u0000\u0000\t\u0000\u0002\u0004\u0006"+
		"\b\n\f\u000e\u0010\u0000\u0000`\u0000\u0012\u0001\u0000\u0000\u0000\u0002"+
		"\u0019\u0001\u0000\u0000\u0000\u0004\u001b\u0001\u0000\u0000\u0000\u0006"+
		"!\u0001\u0000\u0000\u0000\b2\u0001\u0000\u0000\u0000\n4\u0001\u0000\u0000"+
		"\u0000\fE\u0001\u0000\u0000\u0000\u000eO\u0001\u0000\u0000\u0000\u0010"+
		"^\u0001\u0000\u0000\u0000\u0012\u0013\u0003\u0002\u0001\u0000\u0013\u0014"+
		"\u0005\u0000\u0000\u0001\u0014\u0001\u0001\u0000\u0000\u0000\u0015\u0016"+
		"\u0003\u0004\u0002\u0000\u0016\u0017\u0003\u0002\u0001\u0000\u0017\u001a"+
		"\u0001\u0000\u0000\u0000\u0018\u001a\u0001\u0000\u0000\u0000\u0019\u0015"+
		"\u0001\u0000\u0000\u0000\u0019\u0018\u0001\u0000\u0000\u0000\u001a\u0003"+
		"\u0001\u0000\u0000\u0000\u001b\u001c\u0005\u000b\u0000\u0000\u001c\u001d"+
		"\u0005\u0001\u0000\u0000\u001d\u001e\u0003\u0006\u0003\u0000\u001e\u001f"+
		"\u0005\u0002\u0000\u0000\u001f \u0006\u0002\uffff\uffff\u0000 \u0005\u0001"+
		"\u0000\u0000\u0000!\"\u0003\n\u0005\u0000\"#\u0003\b\u0004\u0000#$\u0006"+
		"\u0003\uffff\uffff\u0000$\u0007\u0001\u0000\u0000\u0000%&\u0005\u0003"+
		"\u0000\u0000&\'\u0003\n\u0005\u0000\'(\u0006\u0004\uffff\uffff\u0000("+
		")\u0003\b\u0004\u0000)*\u0006\u0004\uffff\uffff\u0000*3\u0001\u0000\u0000"+
		"\u0000+,\u0005\u0004\u0000\u0000,-\u0003\n\u0005\u0000-.\u0006\u0004\uffff"+
		"\uffff\u0000./\u0003\b\u0004\u0000/0\u0006\u0004\uffff\uffff\u000003\u0001"+
		"\u0000\u0000\u000013\u0006\u0004\uffff\uffff\u00002%\u0001\u0000\u0000"+
		"\u00002+\u0001\u0000\u0000\u000021\u0001\u0000\u0000\u00003\t\u0001\u0000"+
		"\u0000\u000045\u0003\u000e\u0007\u000056\u0003\f\u0006\u000067\u0006\u0005"+
		"\uffff\uffff\u00007\u000b\u0001\u0000\u0000\u000089\u0005\u0005\u0000"+
		"\u00009:\u0003\u000e\u0007\u0000:;\u0006\u0006\uffff\uffff\u0000;<\u0003"+
		"\f\u0006\u0000<=\u0006\u0006\uffff\uffff\u0000=F\u0001\u0000\u0000\u0000"+
		">?\u0005\u0006\u0000\u0000?@\u0003\u000e\u0007\u0000@A\u0006\u0006\uffff"+
		"\uffff\u0000AB\u0003\f\u0006\u0000BC\u0006\u0006\uffff\uffff\u0000CF\u0001"+
		"\u0000\u0000\u0000DF\u0006\u0006\uffff\uffff\u0000E8\u0001\u0000\u0000"+
		"\u0000E>\u0001\u0000\u0000\u0000ED\u0001\u0000\u0000\u0000F\r\u0001\u0000"+
		"\u0000\u0000GH\u0003\u0010\b\u0000HI\u0005\u0007\u0000\u0000IJ\u0003\u000e"+
		"\u0007\u0000JK\u0006\u0007\uffff\uffff\u0000KP\u0001\u0000\u0000\u0000"+
		"LM\u0003\u0010\b\u0000MN\u0006\u0007\uffff\uffff\u0000NP\u0001\u0000\u0000"+
		"\u0000OG\u0001\u0000\u0000\u0000OL\u0001\u0000\u0000\u0000P\u000f\u0001"+
		"\u0000\u0000\u0000QR\u0005\u0004\u0000\u0000RS\u0003\u0010\b\u0000ST\u0006"+
		"\b\uffff\uffff\u0000T_\u0001\u0000\u0000\u0000UV\u0005\b\u0000\u0000V"+
		"W\u0003\u0006\u0003\u0000WX\u0005\t\u0000\u0000XY\u0006\b\uffff\uffff"+
		"\u0000Y_\u0001\u0000\u0000\u0000Z[\u0005\n\u0000\u0000[_\u0006\b\uffff"+
		"\uffff\u0000\\]\u0005\u000b\u0000\u0000]_\u0006\b\uffff\uffff\u0000^Q"+
		"\u0001\u0000\u0000\u0000^U\u0001\u0000\u0000\u0000^Z\u0001\u0000\u0000"+
		"\u0000^\\\u0001\u0000\u0000\u0000_\u0011\u0001\u0000\u0000\u0000\u0005"+
		"\u00192EO^";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}