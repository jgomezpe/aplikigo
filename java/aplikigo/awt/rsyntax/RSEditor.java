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

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextScrollPane;

import aplikigo.awt.AWTEditor;
import lifya.Lexer;


/**
 * <p>An editor based on the RSyntaxArea</p>
 *
 */
public class RSEditor extends AWTEditor{
	protected AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
	protected RSTokenMaker tok;
	protected RTextScrollPane scroll;
	
	/**
	 * Creates a RSyntax editor
	 * @param id Editor's id
	 */
	public RSEditor(String id){
		super(id);
		RSyntaxTextArea textArea = (RSyntaxTextArea)editArea;
		atmf.putMapping("text/"+id, "aplikigo.awt.rsyntax.RSTokenMaker");
		textArea.setSyntaxEditingStyle("text/"+id);
		textArea.setCodeFoldingEnabled(true);
		tok = RSTokenMaker.lastInstance;
	}
	
	/**
	 * Sets the lexer for syntactic coloring of text (if required) 
	 * @param tokenizer Lexer required for obtaining lexema 
	 * @param converter Lexeme Coloring table
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setLexer( Lexer tokenizer, HashMap<String, ?> converter ){ this.tok.setLexer(id, tokenizer, (HashMap<String,Integer>)converter); }
	
	/**
	 * Gets the AWT Text component associated to the editor
	 * @return AWT Text component associated to the editor
	 */
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new RSyntaxTextArea();
		return editArea;	
	}
	
	/**
	 * Gets the scroll panel associated to the editor
	 * @return scroll panel associated to the editor
	 */
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new RTextScrollPane((RSyntaxTextArea)editArea);
		return scroll; 
	}
}