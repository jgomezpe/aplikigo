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

import java.awt.Color;
import java.util.HashMap;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import lifya.Lexer;

/**
 * <p>GUI component which holds the simple syntax style editor</p>
 *
 */
public class SSEditorPanel extends JTextPane{
	protected SyntaxStyle def;
	protected HashMap<String,SyntaxStyle> styles;
	protected HashMap<String,String> token_style=null;
	protected Lexer lexer=null;
	protected String src;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2456710620251070411L;

	/**
	 * Creates a panel for the SS editor
	 */
	public SSEditorPanel(){
		super();
		this.getDocument().addDocumentListener(new SSEditorDocumentListener(this));
	}
	
	/**
	 * Sets the lexer for syntactic coloring of text (if required) 
	 * @param lexer Lexer required for obtaining lexema 
	 * @param token_style Lexeme Coloring table
	 */
	public void setLexer(Lexer lexer, HashMap<String,String> token_style){
		this.lexer = lexer;
		this.token_style = token_style;
		update();
	}
	
	/**
	 * Updates the panel
	 */
	public void update(){
		String str = this.getText();
		try { this.getStyledDocument().remove(0, this.getText().length()); }catch(BadLocationException e) {}
		if( str != null && str.length()>0 ) this.setText(str);
	}
	
	protected void setStyle( SyntaxStyle style ){
		StyledDocument doc = this.getStyledDocument();
		Style s = doc.getStyle(style.tag());
		if( s==null ){
			Style parent;
			if( style.tag().equals(SyntaxStyle.REGULAR) ) parent = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			else parent = doc.getStyle(SyntaxStyle.REGULAR);
			s = doc.addStyle(style.tag(),parent);
		}	
		if( style.font_family()!=null)  StyleConstants.setFontFamily(s, style.font_family());
		if( style.font_size()>0) StyleConstants.setFontSize(s,style.font_size());
		StyleConstants.setItalic(s, style.italic()); 
		StyleConstants.setBold(s, style.bold()); 
		StyleConstants.setUnderline(s, style.under_line());
		int[] c = (style.color()!=null)?style.color().values():null;
		if( c!=null ) StyleConstants.setForeground(s, new Color(c[0],c[1],c[2],c[3]));
	}
	
	/**
	 * Sets the syntactic coloring of text  
	 * @param styles Lexeme Coloring table
	 */
	public void setStyle(HashMap<String, SyntaxStyle> styles ){
		String str = this.getText();
		try { this.getStyledDocument().remove(0, this.getText().length()); }catch(BadLocationException e) {}
		for( SyntaxStyle style:styles.values() ) setStyle(style); 
		if( str != null && str.length()>0 ) this.setText(str);
	}
}