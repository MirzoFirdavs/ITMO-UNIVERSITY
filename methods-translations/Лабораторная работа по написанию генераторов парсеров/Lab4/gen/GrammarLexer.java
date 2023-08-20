// Generated from java-escape by ANTLR 4.11.1
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue"})
public class GrammarLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.11.1", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, ARGUMENT=5, NON_TERMINAL_NAME=6, TERMINAL_NAME=7, 
		CODE=8, REGEX=9, WS=10;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"T__0", "T__1", "T__2", "T__3", "ARGUMENT", "NON_TERMINAL_NAME", "TERMINAL_NAME", 
			"CODE", "REGEX", "WS"
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


	public GrammarLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "Grammar.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u0000\nU\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0001\u0000\u0001\u0000\u0001"+
		"\u0001\u0001\u0001\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001"+
		"\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003\u0001\u0003\u0001"+
		"\u0003\u0001\u0003\u0001\u0004\u0001\u0004\u0005\u0004(\b\u0004\n\u0004"+
		"\f\u0004+\t\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005\u0005"+
		"\u00051\b\u0005\n\u0005\f\u00054\t\u0005\u0001\u0006\u0001\u0006\u0005"+
		"\u00068\b\u0006\n\u0006\f\u0006;\t\u0006\u0001\u0007\u0001\u0007\u0005"+
		"\u0007?\b\u0007\n\u0007\f\u0007B\t\u0007\u0001\u0007\u0001\u0007\u0001"+
		"\b\u0001\b\u0005\bH\b\b\n\b\f\bK\t\b\u0001\b\u0001\b\u0001\t\u0004\tP"+
		"\b\t\u000b\t\f\tQ\u0001\t\u0001\t\u0003)@I\u0000\n\u0001\u0001\u0003\u0002"+
		"\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006\r\u0007\u000f\b\u0011\t\u0013"+
		"\n\u0001\u0000\u0004\u0001\u0000az\u0004\u000009AZ__az\u0001\u0000AZ\u0003"+
		"\u0000\t\n\r\r  Z\u0000\u0001\u0001\u0000\u0000\u0000\u0000\u0003\u0001"+
		"\u0000\u0000\u0000\u0000\u0005\u0001\u0000\u0000\u0000\u0000\u0007\u0001"+
		"\u0000\u0000\u0000\u0000\t\u0001\u0000\u0000\u0000\u0000\u000b\u0001\u0000"+
		"\u0000\u0000\u0000\r\u0001\u0000\u0000\u0000\u0000\u000f\u0001\u0000\u0000"+
		"\u0000\u0000\u0011\u0001\u0000\u0000\u0000\u0000\u0013\u0001\u0000\u0000"+
		"\u0000\u0001\u0015\u0001\u0000\u0000\u0000\u0003\u0017\u0001\u0000\u0000"+
		"\u0000\u0005\u0019\u0001\u0000\u0000\u0000\u0007!\u0001\u0000\u0000\u0000"+
		"\t%\u0001\u0000\u0000\u0000\u000b.\u0001\u0000\u0000\u0000\r5\u0001\u0000"+
		"\u0000\u0000\u000f<\u0001\u0000\u0000\u0000\u0011E\u0001\u0000\u0000\u0000"+
		"\u0013O\u0001\u0000\u0000\u0000\u0015\u0016\u0005:\u0000\u0000\u0016\u0002"+
		"\u0001\u0000\u0000\u0000\u0017\u0018\u0005|\u0000\u0000\u0018\u0004\u0001"+
		"\u0000\u0000\u0000\u0019\u001a\u0005r\u0000\u0000\u001a\u001b\u0005e\u0000"+
		"\u0000\u001b\u001c\u0005t\u0000\u0000\u001c\u001d\u0005u\u0000\u0000\u001d"+
		"\u001e\u0005r\u0000\u0000\u001e\u001f\u0005n\u0000\u0000\u001f \u0005"+
		"s\u0000\u0000 \u0006\u0001\u0000\u0000\u0000!\"\u0005E\u0000\u0000\"#"+
		"\u0005P\u0000\u0000#$\u0005S\u0000\u0000$\b\u0001\u0000\u0000\u0000%)"+
		"\u0005[\u0000\u0000&(\t\u0000\u0000\u0000\'&\u0001\u0000\u0000\u0000("+
		"+\u0001\u0000\u0000\u0000)*\u0001\u0000\u0000\u0000)\'\u0001\u0000\u0000"+
		"\u0000*,\u0001\u0000\u0000\u0000+)\u0001\u0000\u0000\u0000,-\u0005]\u0000"+
		"\u0000-\n\u0001\u0000\u0000\u0000.2\u0007\u0000\u0000\u0000/1\u0007\u0001"+
		"\u0000\u00000/\u0001\u0000\u0000\u000014\u0001\u0000\u0000\u000020\u0001"+
		"\u0000\u0000\u000023\u0001\u0000\u0000\u00003\f\u0001\u0000\u0000\u0000"+
		"42\u0001\u0000\u0000\u000059\u0007\u0002\u0000\u000068\u0007\u0001\u0000"+
		"\u000076\u0001\u0000\u0000\u00008;\u0001\u0000\u0000\u000097\u0001\u0000"+
		"\u0000\u00009:\u0001\u0000\u0000\u0000:\u000e\u0001\u0000\u0000\u0000"+
		";9\u0001\u0000\u0000\u0000<@\u0005{\u0000\u0000=?\t\u0000\u0000\u0000"+
		">=\u0001\u0000\u0000\u0000?B\u0001\u0000\u0000\u0000@A\u0001\u0000\u0000"+
		"\u0000@>\u0001\u0000\u0000\u0000AC\u0001\u0000\u0000\u0000B@\u0001\u0000"+
		"\u0000\u0000CD\u0005}\u0000\u0000D\u0010\u0001\u0000\u0000\u0000EI\u0005"+
		"(\u0000\u0000FH\t\u0000\u0000\u0000GF\u0001\u0000\u0000\u0000HK\u0001"+
		"\u0000\u0000\u0000IJ\u0001\u0000\u0000\u0000IG\u0001\u0000\u0000\u0000"+
		"JL\u0001\u0000\u0000\u0000KI\u0001\u0000\u0000\u0000LM\u0005)\u0000\u0000"+
		"M\u0012\u0001\u0000\u0000\u0000NP\u0007\u0003\u0000\u0000ON\u0001\u0000"+
		"\u0000\u0000PQ\u0001\u0000\u0000\u0000QO\u0001\u0000\u0000\u0000QR\u0001"+
		"\u0000\u0000\u0000RS\u0001\u0000\u0000\u0000ST\u0006\t\u0000\u0000T\u0014"+
		"\u0001\u0000\u0000\u0000\u0007\u0000)29@IQ\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}