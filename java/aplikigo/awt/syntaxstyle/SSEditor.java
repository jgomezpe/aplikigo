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
package aplikigo.awt.syntaxstyle;

import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import aplikigo.awt.AWTEditor;
import lifya.Lexer;

/**
 * <p>Simple syntax style editor</p>
 *
 */
public class SSEditor extends AWTEditor{
	/**
	 * Creates a simple syntax style editor
	 * @param id Editor's id
	 */
	public SSEditor(String id){ super(id); }
	
	/**
	 * Sets the syntax coloring table
	 * @param styles Style associated to a lexema
	 */
	public void setStyle(HashMap<String, SyntaxStyle> styles ){ ((SSEditorPanel)editArea).setStyle(styles); }
	
	/**
	 * Gets the AWT Text component associated to the editor
	 * @return AWT Text component associated to the editor
	 */
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new SSEditorPanel();
		return editArea;	
	}
	
	/**
	 * Gets the scroll panel associated to the editor
	 * @return scroll panel associated to the editor
	 */
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new JScrollPane();
		return scroll; 
	}
	
	/**
	 * Sets the lexer for syntactic coloring of text (if required) 
	 * @param tokenizer Lexer required for obtaining lexema 
	 * @param converter Lexeme Coloring table
	 */
	@SuppressWarnings("unchecked")
	public void setLexer( Lexer tokenizer, HashMap<String, ?> converter ){ ((SSEditorPanel)editArea).setLexer(tokenizer, (HashMap<String, String>)converter); }
}