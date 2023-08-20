// Generated from java-escape by ANTLR 4.11.1
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
		T__0=1, T__1=2, T__2=3, T__3=4, ARGUMENT=5, NON_TERMINAL_NAME=6, TERMINAL_NAME=7, 
		CODE=8, REGEX=9, WS=10;
	public static final int
		RULE_s = 0, RULE_nonTerminal = 1, RULE_arguments = 2, RULE_synt = 3, RULE_epsilonOrRule = 4, 
		RULE_nonTerminalRule = 5, RULE_terminal = 6;
	private static String[] makeRuleNames() {
		return new String[] {
			"s", "nonTerminal", "arguments", "synt", "epsilonOrRule", "nonTerminalRule", 
			"terminal"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "':'", "'|'", "'returns'", "'EPS'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, null, null, null, null, "ARGUMENT", "NON_TERMINAL_NAME", "TERMINAL_NAME", 
			"CODE", "REGEX", "WS"
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
	public static class SContext extends ParserRuleContext {
		public List<NonTerminalContext> nonTerminal() {
			return getRuleContexts(NonTerminalContext.class);
		}
		public NonTerminalContext nonTerminal(int i) {
			return getRuleContext(NonTerminalContext.class,i);
		}
		public List<TerminalContext> terminal() {
			return getRuleContexts(TerminalContext.class);
		}
		public TerminalContext terminal(int i) {
			return getRuleContext(TerminalContext.class,i);
		}
		public SContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_s; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterS(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitS(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitS(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SContext s() throws RecognitionException {
		SContext _localctx = new SContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_s);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(15); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(14);
				nonTerminal();
				}
				}
				setState(17); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NON_TERMINAL_NAME );
			setState(20); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(19);
				terminal();
				}
				}
				setState(22); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==TERMINAL_NAME );
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
	public static class NonTerminalContext extends ParserRuleContext {
		public TerminalNode NON_TERMINAL_NAME() { return getToken(GrammarParser.NON_TERMINAL_NAME, 0); }
		public List<EpsilonOrRuleContext> epsilonOrRule() {
			return getRuleContexts(EpsilonOrRuleContext.class);
		}
		public EpsilonOrRuleContext epsilonOrRule(int i) {
			return getRuleContext(EpsilonOrRuleContext.class,i);
		}
		public ArgumentsContext arguments() {
			return getRuleContext(ArgumentsContext.class,0);
		}
		public SyntContext synt() {
			return getRuleContext(SyntContext.class,0);
		}
		public NonTerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonTerminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterNonTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitNonTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitNonTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonTerminalContext nonTerminal() throws RecognitionException {
		NonTerminalContext _localctx = new NonTerminalContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_nonTerminal);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(24);
			match(NON_TERMINAL_NAME);
			setState(26);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==ARGUMENT) {
				{
				setState(25);
				arguments();
				}
			}

			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(28);
				synt();
				}
			}

			setState(31);
			match(T__0);
			setState(32);
			epsilonOrRule();
			setState(37);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__1) {
				{
				{
				setState(33);
				match(T__1);
				setState(34);
				epsilonOrRule();
				}
				}
				setState(39);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
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
	public static class ArgumentsContext extends ParserRuleContext {
		public TerminalNode ARGUMENT() { return getToken(GrammarParser.ARGUMENT, 0); }
		public ArgumentsContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_arguments; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterArguments(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitArguments(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitArguments(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ArgumentsContext arguments() throws RecognitionException {
		ArgumentsContext _localctx = new ArgumentsContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_arguments);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			match(ARGUMENT);
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
	public static class SyntContext extends ParserRuleContext {
		public TerminalNode ARGUMENT() { return getToken(GrammarParser.ARGUMENT, 0); }
		public SyntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_synt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterSynt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitSynt(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitSynt(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SyntContext synt() throws RecognitionException {
		SyntContext _localctx = new SyntContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_synt);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(42);
			match(T__2);
			setState(43);
			match(ARGUMENT);
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
	public static class EpsilonOrRuleContext extends ParserRuleContext {
		public EpsilonOrRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_epsilonOrRule; }
	 
		public EpsilonOrRuleContext() { }
		public void copyFrom(EpsilonOrRuleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NotEpsContext extends EpsilonOrRuleContext {
		public List<NonTerminalRuleContext> nonTerminalRule() {
			return getRuleContexts(NonTerminalRuleContext.class);
		}
		public NonTerminalRuleContext nonTerminalRule(int i) {
			return getRuleContext(NonTerminalRuleContext.class,i);
		}
		public NotEpsContext(EpsilonOrRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterNotEps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitNotEps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitNotEps(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class EpsContext extends EpsilonOrRuleContext {
		public EpsContext(EpsilonOrRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterEps(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitEps(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitEps(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EpsilonOrRuleContext epsilonOrRule() throws RecognitionException {
		EpsilonOrRuleContext _localctx = new EpsilonOrRuleContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_epsilonOrRule);
		try {
			int _alt;
			setState(51);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__3:
				_localctx = new EpsContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(45);
				match(T__3);
				}
				break;
			case NON_TERMINAL_NAME:
			case TERMINAL_NAME:
			case CODE:
				_localctx = new NotEpsContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(47); 
				_errHandler.sync(this);
				_alt = 1;
				do {
					switch (_alt) {
					case 1:
						{
						{
						setState(46);
						nonTerminalRule();
						}
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					setState(49); 
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,5,_ctx);
				} while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER );
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
	public static class NonTerminalRuleContext extends ParserRuleContext {
		public NonTerminalRuleContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nonTerminalRule; }
	 
		public NonTerminalRuleContext() { }
		public void copyFrom(NonTerminalRuleContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RuleCodeContext extends NonTerminalRuleContext {
		public TerminalNode CODE() { return getToken(GrammarParser.CODE, 0); }
		public RuleCodeContext(NonTerminalRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterRuleCode(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitRuleCode(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitRuleCode(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RuleNonTerminalContext extends NonTerminalRuleContext {
		public TerminalNode NON_TERMINAL_NAME() { return getToken(GrammarParser.NON_TERMINAL_NAME, 0); }
		public TerminalNode ARGUMENT() { return getToken(GrammarParser.ARGUMENT, 0); }
		public RuleNonTerminalContext(NonTerminalRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterRuleNonTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitRuleNonTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitRuleNonTerminal(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class RuleTerminalContext extends NonTerminalRuleContext {
		public TerminalNode TERMINAL_NAME() { return getToken(GrammarParser.TERMINAL_NAME, 0); }
		public RuleTerminalContext(NonTerminalRuleContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterRuleTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitRuleTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitRuleTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final NonTerminalRuleContext nonTerminalRule() throws RecognitionException {
		NonTerminalRuleContext _localctx = new NonTerminalRuleContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_nonTerminalRule);
		int _la;
		try {
			setState(59);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case CODE:
				_localctx = new RuleCodeContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(53);
				match(CODE);
				}
				break;
			case NON_TERMINAL_NAME:
				_localctx = new RuleNonTerminalContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(54);
				match(NON_TERMINAL_NAME);
				setState(56);
				_errHandler.sync(this);
				_la = _input.LA(1);
				if (_la==ARGUMENT) {
					{
					setState(55);
					match(ARGUMENT);
					}
				}

				}
				break;
			case TERMINAL_NAME:
				_localctx = new RuleTerminalContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(58);
				match(TERMINAL_NAME);
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
	public static class TerminalContext extends ParserRuleContext {
		public TerminalNode TERMINAL_NAME() { return getToken(GrammarParser.TERMINAL_NAME, 0); }
		public TerminalNode REGEX() { return getToken(GrammarParser.REGEX, 0); }
		public TerminalContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_terminal; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).enterTerminal(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof GrammarListener ) ((GrammarListener)listener).exitTerminal(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof GrammarVisitor ) return ((GrammarVisitor<? extends T>)visitor).visitTerminal(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TerminalContext terminal() throws RecognitionException {
		TerminalContext _localctx = new TerminalContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_terminal);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(61);
			match(TERMINAL_NAME);
			setState(62);
			match(T__0);
			setState(63);
			match(REGEX);
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
		"\u0004\u0001\nB\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0001\u0000\u0004\u0000\u0010"+
		"\b\u0000\u000b\u0000\f\u0000\u0011\u0001\u0000\u0004\u0000\u0015\b\u0000"+
		"\u000b\u0000\f\u0000\u0016\u0001\u0001\u0001\u0001\u0003\u0001\u001b\b"+
		"\u0001\u0001\u0001\u0003\u0001\u001e\b\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0001\u0001\u0005\u0001$\b\u0001\n\u0001\f\u0001\'\t\u0001\u0001"+
		"\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001"+
		"\u0004\u0004\u00040\b\u0004\u000b\u0004\f\u00041\u0003\u00044\b\u0004"+
		"\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u00059\b\u0005\u0001\u0005"+
		"\u0003\u0005<\b\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0006"+
		"\u0001\u0006\u0000\u0000\u0007\u0000\u0002\u0004\u0006\b\n\f\u0000\u0000"+
		"D\u0000\u000f\u0001\u0000\u0000\u0000\u0002\u0018\u0001\u0000\u0000\u0000"+
		"\u0004(\u0001\u0000\u0000\u0000\u0006*\u0001\u0000\u0000\u0000\b3\u0001"+
		"\u0000\u0000\u0000\n;\u0001\u0000\u0000\u0000\f=\u0001\u0000\u0000\u0000"+
		"\u000e\u0010\u0003\u0002\u0001\u0000\u000f\u000e\u0001\u0000\u0000\u0000"+
		"\u0010\u0011\u0001\u0000\u0000\u0000\u0011\u000f\u0001\u0000\u0000\u0000"+
		"\u0011\u0012\u0001\u0000\u0000\u0000\u0012\u0014\u0001\u0000\u0000\u0000"+
		"\u0013\u0015\u0003\f\u0006\u0000\u0014\u0013\u0001\u0000\u0000\u0000\u0015"+
		"\u0016\u0001\u0000\u0000\u0000\u0016\u0014\u0001\u0000\u0000\u0000\u0016"+
		"\u0017\u0001\u0000\u0000\u0000\u0017\u0001\u0001\u0000\u0000\u0000\u0018"+
		"\u001a\u0005\u0006\u0000\u0000\u0019\u001b\u0003\u0004\u0002\u0000\u001a"+
		"\u0019\u0001\u0000\u0000\u0000\u001a\u001b\u0001\u0000\u0000\u0000\u001b"+
		"\u001d\u0001\u0000\u0000\u0000\u001c\u001e\u0003\u0006\u0003\u0000\u001d"+
		"\u001c\u0001\u0000\u0000\u0000\u001d\u001e\u0001\u0000\u0000\u0000\u001e"+
		"\u001f\u0001\u0000\u0000\u0000\u001f \u0005\u0001\u0000\u0000 %\u0003"+
		"\b\u0004\u0000!\"\u0005\u0002\u0000\u0000\"$\u0003\b\u0004\u0000#!\u0001"+
		"\u0000\u0000\u0000$\'\u0001\u0000\u0000\u0000%#\u0001\u0000\u0000\u0000"+
		"%&\u0001\u0000\u0000\u0000&\u0003\u0001\u0000\u0000\u0000\'%\u0001\u0000"+
		"\u0000\u0000()\u0005\u0005\u0000\u0000)\u0005\u0001\u0000\u0000\u0000"+
		"*+\u0005\u0003\u0000\u0000+,\u0005\u0005\u0000\u0000,\u0007\u0001\u0000"+
		"\u0000\u0000-4\u0005\u0004\u0000\u0000.0\u0003\n\u0005\u0000/.\u0001\u0000"+
		"\u0000\u000001\u0001\u0000\u0000\u00001/\u0001\u0000\u0000\u000012\u0001"+
		"\u0000\u0000\u000024\u0001\u0000\u0000\u00003-\u0001\u0000\u0000\u0000"+
		"3/\u0001\u0000\u0000\u00004\t\u0001\u0000\u0000\u00005<\u0005\b\u0000"+
		"\u000068\u0005\u0006\u0000\u000079\u0005\u0005\u0000\u000087\u0001\u0000"+
		"\u0000\u000089\u0001\u0000\u0000\u00009<\u0001\u0000\u0000\u0000:<\u0005"+
		"\u0007\u0000\u0000;5\u0001\u0000\u0000\u0000;6\u0001\u0000\u0000\u0000"+
		";:\u0001\u0000\u0000\u0000<\u000b\u0001\u0000\u0000\u0000=>\u0005\u0007"+
		"\u0000\u0000>?\u0005\u0001\u0000\u0000?@\u0005\t\u0000\u0000@\r\u0001"+
		"\u0000\u0000\u0000\t\u0011\u0016\u001a\u001d%138;";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}