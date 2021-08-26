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
package aplikigo.remotegui;

import aplikigo.server.JXONBaseServer;

/**
 * <p>Remote editor</p>
 *
 */
public class RemoteEditor extends RemoteComponent implements aplikigo.gui.Editor{
	protected String txt;
	protected int[] loc; 
	
	/**
	 * Creates a Remote Editor 
	 * @param id Editor id
	 * @param server Remote editor
	 */
	public RemoteEditor(String id, JXONBaseServer server){ super(id, server); }

	/**
	 * Highlights a row
	 * @param row Row to highlight
	 */
	@Override
	public void highlight(int row){ try{ run(id(), "highlight", row); }catch(Exception e) {} }

	/**
	 * Sets the editor cursor
	 * @param row Row for the editor's cursor
	 * @param column Column for the editor's cursor
	 */
	@Override
	public void locate(int row, int column){ try{ run(id(), "locateCursor", row, column); }catch(Exception e) {} }

	/**
	 * Sets the editor's text
	 * @param txt Text to set in the editor
	 */
	@Override
	public void setText(String txt){ try{ run(id(), "setText", txt); }catch(Exception e) {} }

	/**
	 * Gets the current text in the editor
	 * @return Current text in the editor
	 */
	@SuppressWarnings("static-access")
	@Override
	public String getText() {
		txt = null;
		try{ run(id(), "getText"); }catch(Exception e) {}
		while( txt == null ) {
			try{ Thread.currentThread().sleep(50); }catch (InterruptedException e) { e.printStackTrace(); }
		}
		return txt;
	}

	/**
	 * Gets the position of the editor's cursor
	 * @return [x,y] current position of the editor's cursor
	 */
	@SuppressWarnings("static-access")
	@Override
	public int[] getLocation() {
		loc = null;
		try{ run(id(), "getLocation"); }catch(Exception e) {}
		while( loc == null ) {
			try{ Thread.currentThread().sleep(50); }catch (InterruptedException e) { e.printStackTrace(); }
		}
		return loc;
	}
	
	protected Object updateText( String txt ) {
		this.txt = txt;
		return null;
	}
	
	protected Object updateLocation( int row, int column ) {
		loc = new int[] {row, column};
		return null;
	}
}