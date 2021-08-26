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
package aplikigo.awt;

import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import aplikigo.Component;
import aplikigo.gui.Editor;
import lifya.Lexer;
import speco.object.Named;

/**
 * <p>AWT Editor</p>
 *
 */
public abstract class AWTEditor extends Named implements Editor{
	protected JTextComponent editArea=null;
	protected JScrollPane scroll=null;
	protected String id;
	
	/**
	 * Gets the AWT Text component associated to the editor
	 * @return AWT Text component associated to the editor
	 */
	public abstract JTextComponent editArea();
	
	/**
	 * Gets the scroll panel associated to the editor
	 * @return scroll panel associated to the editor
	 */
	public abstract JScrollPane scroll();
	
	/**
	 * Creates an AWT editor
	 * @param id Editor's id
	 */
	public AWTEditor(String id){
		super(id);
		editArea = this.editArea();
		editArea.setVerifyInputWhenFocusTarget(true);
		scroll = this.scroll();
		scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		scroll.setAutoscrolls(true);
		scroll.getViewport().add(editArea, null);
	}
	
	/**
	 * Highlights a row (does nothing)
	 * @param row Row to highlight
	 */	
	@Override
	public void highlight(int row) {}

	/**
	 * Sets the editor cursor
	 * @param row Row for the editor's cursor
	 * @param column Column for the editor's cursor
	 */
	@Override
	public void locate(int row, int column){
		String str = editArea.getText();
		int caret = 0;
		int i=0;
		while( i<row ){
			while(str.charAt(caret)!='\n') caret++;
			caret++;
			i++;
		}
		editArea.setCaretPosition(caret+column);
		editArea.requestFocusInWindow();
	}

	/**
	 * Sets the editor's text
	 * @param txt Text to set in the editor
	 */
	@Override
	public void setText(String txt){ editArea.setText(txt); }

	/**
	 * Gets the current text in the editor
	 * @return Current text in the editor
	 */
	@Override 
	public String getText() { return editArea.getText(); }
	
	/**
	 * Gets the position of the editor's cursor
	 * @return [x,y] current position of the editor's cursor
	 */
	@Override
	public int[] getLocation(){
		int pos = editArea.getCaretPosition();
		String str = editArea.getText();
		int caret = 0;
		int row=0;
		int column=0;
		while( caret<pos ){
			if(str.charAt(caret)!='\n') column++;
			else{
				row++;
				column=0;
			}
			caret++;
		}
		return new int[]{row,column}; 
	}
	
	/**
	 * Sets the lexer for syntactic coloring of text (if required) 
	 * @param lexer Lexer required for obtaining lexema 
	 * @param converter Lexeme Coloring table
	 */
	public abstract void setLexer( Lexer lexer, HashMap<String, ?> converter );
	
	/**
	 * Obtains a subcomponent
	 * @param component Subcomponent id
	 * @return The subcomponent with the given id, <i>null</i> otherwise
	 */
	@Override
	public Component get(String component) {
	    if(component.equals(id())) return this;
	    return null;
	}	
}