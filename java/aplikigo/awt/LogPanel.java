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

import java.awt.BorderLayout;

import javax.swing.*;

import utila.I18N;

/**
 * <p>A panel for showing output and error messages</p>
 */
public class LogPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3299228531238906091L;

	/**
	 * Creates a panel for showing output and error messages
	 */
	public LogPanel() { try { jbInit(); } catch (Exception ex) { ex.printStackTrace(); } }

	private void jbInit() throws Exception {
		this.setLayout(logPanelLayout);
		jOutTextArea = new JTextPane();
		jErrorTextArea = new JTextPane();
		jOutPanel.setLayout(outBorderLayout);
		jOutPanel.add(new JScrollPane(jOutTextArea), java.awt.BorderLayout.CENTER);
		jErrorPanel.setLayout(errorBorderLayout);
		this.add(jLogPaneTab, java.awt.BorderLayout.CENTER);
		jLogPaneTab.add(jOutPanel, I18N.process("·Out·"));
		jLogPaneTab.add(jErrorPanel, I18N.process("·Error·"));
		jErrorPanel.add(new JScrollPane(jErrorTextArea), java.awt.BorderLayout.CENTER);
	}

	JTabbedPane jLogPaneTab = new JTabbedPane();
	BorderLayout logPanelLayout = new BorderLayout();
	JPanel jOutPanel = new JPanel();
	JPanel jErrorPanel = new JPanel();
	BorderLayout outBorderLayout = new BorderLayout();
	JTextPane jOutTextArea;
	BorderLayout errorBorderLayout = new BorderLayout();
	JTextPane jErrorTextArea;

	/**
	 * Gets the TextArea used for showing the output messages
	 * @return TextArea used for showing the output messages
	 */
	public JTextPane getOutArea(){ return this.jOutTextArea; }

	/**
	 * Gets the TextArea used for showing the error messages
	 * @return TextArea used for showing the error messages
	 */
	public JTextPane getErrorArea() { return this.jErrorTextArea; }
    
	/**
	 * Selects error/output panel to be shown 
	 * @param output <i>true</I> shows output panel, <i>false</i> shows error panel
	 */
    public void select( boolean output ){
    	jLogPaneTab.setSelectedIndex(output?0:1);
    }
    
    /**
     * Sets panels text according to language
     * @param out Caption for the output panel
     * @param error Caption for the error panel
     */
    public void setLanguage( String out, String error ){
    	jLogPaneTab.setTitleAt(0, out);
    	jLogPaneTab.setTitleAt(1, error);
    }
}