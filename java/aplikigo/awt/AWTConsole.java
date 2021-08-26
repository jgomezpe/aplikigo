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

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import aplikigo.Component;
import aplikigo.gui.Console;
import speco.object.Named;

/**
 * <p>AWT console for showing error and messages</p>
 *
 */
public class AWTConsole extends Named implements Console{
	protected LogPanel panel;
	protected Color out_color = Color.gray;
	protected Color error_color = Color.red;
	
	/**
	 * Creates a console inside the provided log panel
	 * @param panel LogPanel holding the console
	 */
	public AWTConsole(LogPanel panel){
		this(panel.getUIClassID(), panel);
		this.panel = panel;
	}
	
	/**
	 * Creates a console inside the provided log panel
	 * @param id Console id
	 * @param panel LogPanel holding the console
	 */
	public AWTConsole(String id, LogPanel panel){
		super(id);
		this.panel = panel;
	}
	
	/**
	 * Sets the font color for output messages
	 * @param color Color for output messages
	 */
	public void setOutColor( Color color ){ out_color=color; }
	
	/**
	 * Sets the font color for error messages
	 * @param color Color for error messages
	 */
	public void setErrorColor( Color color ){ error_color=color; }

	/**
	 * Shows the output or error console
	 * @param output <i>true</i> shows the output console, <i>false</i> shows the error console
	 */
	@Override
	public void display(boolean output){ panel.select(output); }

	private void appendToPane(JTextPane tp, String msg, Color c){
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, "Lucida Console");
		aset = sc.addAttribute(aset, StyleConstants.Alignment, StyleConstants.ALIGN_JUSTIFIED);

		int len = tp.getDocument().getLength();
		tp.setCaretPosition(len);
		tp.setCharacterAttributes(aset, false);
		tp.replaceSelection(msg);
	}
	 
	/**
	 * Shows an error message in the console
	 * @param message Error message
	 */
	@Override
	public void error(String message){
		panel.getErrorArea().setText("");
		appendToPane(panel.getErrorArea(), message, error_color); 
		display(false);
	}

	/**
	 * Shows an output message in the console
	 * @param message Output message
	 */
	@Override
	public void out(String message) {
		panel.getOutArea().setText("");
		appendToPane(panel.getOutArea(), message, out_color); 
		display(true);
	}

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