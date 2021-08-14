package aplikigo.awt.rsyntax;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.OccurrenceMarker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenImpl;

import lifya.Lexer;

public class TokenMaker implements org.fife.ui.rsyntaxtextarea.TokenMaker{
    public static TokenMaker lastInstance=null;
	
    protected Lexer lexer;
    protected HashMap<String, Integer> converter;
    protected TokenImpl firstToken=null;
    protected TokenImpl lastToken=null;
    protected String src;
	
    public TokenMaker(){
	lastInstance = this;
    }
	
    public void setLexer( String src, Lexer lexer, HashMap<String, Integer> converter ){
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
	
	resetTokenList();
	String input = text.toString();
	if( input != null && input.length()>0 ){
	    lexer.init(input);
	    lexer.removeTokens(false);
	    lifya.Token t;
	    while( (t=lexer.next()) != null ) {
		try{ 
		    addToken(array, t.start()+offset, t.end()+offset-1, converter.get(t.type()), 
			    startOffset+t.start()); 
		}catch(Exception e){}
	    }
	    lexer.removeTokens(true);
	}
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