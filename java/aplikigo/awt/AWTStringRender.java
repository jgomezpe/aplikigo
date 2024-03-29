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
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import aplikigo.Component;
import aplikigo.gui.StringRender;
import speco.jxon.JXON;

/**
 * <p>String render for AWT</p>
 *
 */
public class AWTStringRender extends JPanel implements StringRender{
	protected String id;
	
	BorderLayout layout = new BorderLayout();
	JTextPane textArea = new JTextPane();
	JScrollPane scroll;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8976916928457960190L;

	/**
	 * Creates a text render
	 */
	public AWTStringRender(){
		scroll = new JScrollPane(textArea);
		this.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);		
	}
	
	/**
	 * Creates a text render
	 * @param id Text render id
	 */
	public AWTStringRender( String id ){
		this();
		this.id = id;
	}

	/**
	 * Draws the given text 
	 * @param str Text to draw
	 */
	@Override
	public void render(String str){ textArea.setText(str);	}
	
	/**
	 * Adds the given text 
	 * @param str Text to add
	 */
	@Override
	public void add(String str){ textArea.setText(textArea.getText()+"\n"+str); }
	
	/**
	 * Initializes the render (draw text)
	 */
	@Override
	public void init() { textArea.setText(""); }

	/** 
	 * Sets the render's font
	 * @param font Font
	 */
	@Override
	public void setFont( Font font ) { if( textArea!=null) textArea.setFont(font); }

	@Override
	public void config(JXON json) {}

	/**
	 * Obtains a subcomponent
	 * @param component Subcomponent id
	 * @return <i>null</i>
	 */
	@Override
	public Component get(String component) { return null; }

	/**
	 * Gets the string render id
	 * @return String render id 
	 */
	@Override
	public String id() { return id; }

	/**
	 * Sets the string render id
	 * @param id String render id 
	 */
	@Override
	public void id(String id) { this.id = id; }
}
