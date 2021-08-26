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

import java.io.OutputStream;
import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyledDocument;

/**
 * <p>A logs OutputStream. It can be used for sending error messages and output messages to a log panel instead of sending them to
 * the console</p>
 */
public class LogOutputStream extends OutputStream {
	/**
	 *  Maximum number of rows being stored in the LogPanel
	 */
	public static int MAX_ROWS = 1000;

	/**
	 * If the LogOutputStream will display the messages or not
	 */
	protected boolean display_messages = true;

	/**
	 * The JTextArea displaying the messages
	 */
	protected JTextPane area;

	/**
	 * Creates a Log OutputStream using the given TextArea
	 * @param area TextArea receiving the output messages
	 */
	public LogOutputStream(JTextPane area){ this.area = area; }

	/**
	 * Creates a Log OutputStream using the given TextArea
	 * @param area TextArea receiving the output messages
	 * @param display defines if the LogOutputStream will display the messages or not
	 */
	public LogOutputStream(JTextPane area, boolean display) {
		this.area = area;
		this.display_messages = display;
	}

	/**
	 * Writes the given symbol in the LogOutputStream
	 * @param b Symbol to be written in the LogOutputStream
	 */
	public void write(int b) {
		if (display_messages) {
			StyledDocument doc = area.getStyledDocument();
			try {
				doc.insertString(doc.getLength(), "" + (char) b, new SimpleAttributeSet());
			} catch (BadLocationException e) {}
			area.setCaretPosition(area.getText().length());
		}
	}
}