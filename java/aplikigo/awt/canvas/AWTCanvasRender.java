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
package aplikigo.awt.canvas;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.JPanel;

import aplikigo.Component;
import aplikigo.gui.canvas.Canvas;
import aplikigo.gui.canvas.CanvasRender;
import speco.jxon.JXON;

/**
 * <p>AWT canvas render </p>
 *
 */
public class AWTCanvasRender extends JPanel implements CanvasRender{
	protected JXON object = null;
	protected String id;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691456845015325692L;

	protected AWTCanvas canvas;
	
	protected HashMap<String, Image> images = new HashMap<String,Image>();
	
	/**
	 * Creates a panel holding an AWT canvas with white background color
	 */
	public AWTCanvasRender(){ this(new Color(255,255,255)); }
	
	/**
	 * Creates a panel holding an AWT canvas with the given background color
	 * @param background_color Background color
	 */
	public AWTCanvasRender( Color background_color ){
		setBackground(background_color);
		canvas = new AWTCanvas(this);
		id = ""+serialVersionUID;
	}
	
	/**
	 * Initializes the render (draw object)
	 */
	@Override
	public void init() { object = null; }

	/**
	 * Draws the given object information (JXON) on the canvas
	 * @param obj Object to draw (JXON information)
	 */
	@Override
	public void render( JXON obj ){
		object = obj;
		updateUI();
	}
	
	/**
	 * Paints the graphic component
	 * @param g Graphic component
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		canvas.setGraphics( g );
		if( object!= null ) canvas.draw(object);
	}		

	/**
	 * Gets the Canvas
	 * @return Canvas
	 */
	@Override
	public Canvas canvas(){ return canvas; }

	/**
	 * Sets the Canvas
	 * @param canvas Canvas
	 */
	@Override
	public void canvas(Canvas canvas){
	    this.canvas = ((AWTCanvas)canvas);
	    this.canvas.setRender(this);
	}

	/**
	 * Obtains a subcomponent
	 * @param component Subcomponent id
	 * @return The subcomponent with the given id, <i>null</i> otherwise
	 */
	@Override
	public Component get(String component) {
	    if( component.equals(id()) ) return this;
	    return this.canvas().get(component);
	}

	/**
	 * Gets the canvas render id
	 * @return Canvas render id 
	 */
	@Override
	public String id() { return id; }

	/**
	 * Sets the canvas render id
	 * @param id Canvas render id 
	 */
	@Override
	public void id(String id) { this.id = id; }
}