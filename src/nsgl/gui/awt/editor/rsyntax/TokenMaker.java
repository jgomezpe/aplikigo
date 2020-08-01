package nsgl.gui.awt.editor.rsyntax;
import javax.swing.Action;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.OccurrenceMarker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenImpl;

import nsgl.character.CharacterSequence;
import nsgl.generic.array.Vector;
import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;


public class TokenMaker implements org.fife.ui.rsyntaxtextarea.TokenMaker{
	public static TokenMaker lastInstance=null;
	
	protected Lexer lexer;
	protected KeyMap<String, Integer> converter;
	protected TokenImpl firstToken=null;
	protected TokenImpl lastToken=null;
	protected String src;
	
	public TokenMaker(){
		lastInstance = this;
	}
	
	public void setLexer( String src, Lexer lexer, KeyMap<String, Integer> converter ){
		this.lexer = lexer;
		this.converter = converter;
		this.src = src;
	}
	
	protected void addToken( TokenImpl token ){
		if( firstToken == null ){
			firstToken = token;
			lastToken = firstToken;
		}else{
			lastToken.setNextToken(token);
			lastToken = token;
		}
	}
	
	protected void resetTokenList(){
		firstToken = lastToken = null;
	}
	
	@Override
	public void addNullToken(){ addToken( new TokenImpl() ); }

	@Override
	public void addToken(char[] line, int begin, int end, int type, int startOffset) {
		addToken( new TokenImpl(line, begin, end, startOffset, type, 0) ); 
	}

	@Override
	public int getClosestStandardTokenTypeForInternalType(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean getCurlyBracesDenoteCodeBlocks(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Action getInsertBreakAction() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getLastTokenTypeOnLine(Segment arg0, int arg1) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String[] getLineCommentStartAndEnd(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getMarkOccurrencesOfTokenType(int arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public OccurrenceMarker getOccurrenceMarker() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getShouldIndentNextLineAfter(Token arg0) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Token getTokenList(Segment text, int startTokenType, int startOffset) {
		char[] array = text.array;
		int offset = text.offset;
		int newStartOffset = startOffset - offset;
		int currentTokenStart = offset;

		resetTokenList();
		String input = text.toString();
		int count = input.length();
		if( input != null && input.length()>0 ){
		    	CharacterSequence seq = new CharacterSequence(input,this.src);
			Vector<nsgl.language.Token> token = null;
			token = lexer.shallow_analize(seq,0);
			for( nsgl.language.Token t:token ){
				int start = t.location()-1 + offset;
				if( start>currentTokenStart ) addToken(array, currentTokenStart,start-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
				currentTokenStart = start;
				try{ addToken(array, currentTokenStart, start+t.length()-1, converter.get(t.type()), newStartOffset+currentTokenStart); }catch(Exception e){}
				currentTokenStart += t.length();
			}
		}
		if( currentTokenStart-offset < count ) addToken(array, currentTokenStart,offset+count-1, Token.WHITESPACE, newStartOffset+currentTokenStart);
		addNullToken();
		return firstToken;
	}

	@Override
	public boolean isIdentifierChar(int arg0, char arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isMarkupLanguage() {
		// TODO Auto-generated method stub
		return false;
	}

}
