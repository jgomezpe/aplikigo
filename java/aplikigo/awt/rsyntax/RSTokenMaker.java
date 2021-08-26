/**
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <h3>License</h3>
 *
 * Copyright (c) 2019 by Jonatan Gomez-Perdomo. <br>
 * All rights reserved. <br>
 *
 * <p>Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <ul>
 * <li> Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the copyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * </ul>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
 * (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
 * @version 1.0
 */
package aplikigo.awt.rsyntax;
import java.util.HashMap;

import javax.swing.Action;
import javax.swing.text.Segment;

import org.fife.ui.rsyntaxtextarea.OccurrenceMarker;
import org.fife.ui.rsyntaxtextarea.Token;
import org.fife.ui.rsyntaxtextarea.TokenImpl;
import org.fife.ui.rsyntaxtextarea.TokenMaker;

import lifya.Lexer;

/**
 * <p>A TokenMaker for the RSEditor based on Lifya's Lexer</p>
 *
 */
public class RSTokenMaker implements TokenMaker{
	protected static RSTokenMaker lastInstance=null;
	
	protected Lexer lexer;
	protected HashMap<String, Integer> converter;
	protected TokenImpl firstToken=null;
	protected TokenImpl lastToken=null;
	protected String src;
	
	public RSTokenMaker(){ lastInstance = this; }
	
	/**
	 * Sets the lexer for syntactic coloring of text 
	 * @param src Text to be analyzed
	 * @param lexer Lexer required for obtaining lexema 
	 * @param converter Lexeme Coloring table
	 */
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
	
	protected void resetTokenList(){ firstToken = lastToken = null; }
	
	@Override
	public void addNullToken(){ addToken( new TokenImpl() ); }

	@Override
	public void addToken(char[] line, int begin, int end, int type, int startOffset) {
		addToken( new TokenImpl(line, begin, end, startOffset, type, 0) ); 		
	}

	@Override
	public int getClosestStandardTokenTypeForInternalType(int arg0) { return 0; }

	@Override
	public boolean getCurlyBracesDenoteCodeBlocks(int arg0) { return false; }

	@Override
	public Action getInsertBreakAction() { return null; }

	@Override
	public int getLastTokenTypeOnLine(Segment arg0, int arg1) { return 0; }

	@Override
	public String[] getLineCommentStartAndEnd(int arg0) { return null; }

	@Override
	public boolean getMarkOccurrencesOfTokenType(int arg0) { return false; }

	@Override
	public OccurrenceMarker getOccurrenceMarker() { return null; }

    @Override
    public boolean getShouldIndentNextLineAfter(Token arg0) { return false; }

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
    public boolean isIdentifierChar(int arg0, char arg1) { return false; }

    @Override
    public boolean isMarkupLanguage() {	return false; }
}