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

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import aplikigo.Component;
import aplikigo.gui.Color;
import aplikigo.gui.canvas.Canvas;
import aplikigo.gui.canvas.CanvasConstants;
import aplikigo.gui.canvas.DrawProcessor;
import speco.stream.Resource;
import speco.jxon.JXON;

/**
 * <p>AWT Canvas</p>
 *
 */
public class AWTCanvas implements Canvas, DrawProcessor{
	protected String id;
	
	protected Graphics2D g;
	protected AWTCanvasRender render;
	protected GeneralPath path = new GeneralPath();
	
	protected HashMap<String, Integer> primitives = new HashMap<String,Integer>();
	protected HashMap<String, JXON> custom = new HashMap<String,JXON>();

	/**
	 * Creates a Canvas using the provided Render (panel)
	 * @param render Panel holding the canvas
	 */
	public AWTCanvas( AWTCanvasRender render ){
		this( render.id()+"Canvas", render );
	}
    
	/**
	 * Creates a Canvas using the provided Render (panel)
	 * @param id Canvas id for component search
	 * @param render Panel holding the canvas
	 */
	public AWTCanvas( String id, AWTCanvasRender render ){
		this.id = id;
		this.render = render;
		primitives.put(CanvasConstants.COMPOUND,0);
		primitives.put(CanvasConstants.MOVETO,1);
		primitives.put(CanvasConstants.LINETO,2);
		primitives.put(CanvasConstants.QUADTO,3);
		primitives.put(CanvasConstants.CURVETO,4);
		primitives.put(CanvasConstants.TEXT,5);
		primitives.put(CanvasConstants.IMAGE,6);
		primitives.put(CanvasConstants.BEGIN,7);
		primitives.put(CanvasConstants.CLOSE,8);
		primitives.put(CanvasConstants.STROKE,9);
		primitives.put(CanvasConstants.FILL,10);
		primitives.put(CanvasConstants.STROKESTYLE,11);
		primitives.put(CanvasConstants.FILLSTYLE,12);
		primitives.put(CanvasConstants.LINE,13);
		primitives.put(CanvasConstants.POLYLINE,14);
		primitives.put(CanvasConstants.POLYGON,15);
		primitives.put(CanvasConstants.TRANSLATE,16);
		primitives.put(CanvasConstants.ROTATE,17);
		primitives.put(CanvasConstants.SCALE,18);
		primitives.put(CanvasConstants.FIT,19);
	}
	
	/**
	 * Sets the Render of the canvas (panel holding it)
	 * @param render Render of the canvas (panel holding it)
	 */
	public void setRender( AWTCanvasRender render ) { this.render = render; }
	
	/**
	 * Sets the graphic component of the canvas
	 * @param g Graphic component of the canvas
	 */
	public void setGraphics( Graphics g ){ this.g = (Graphics2D)g; }
		
	/**
	 * Converts a given Image into a BufferedImage
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img) {
		if (img instanceof BufferedImage) return (BufferedImage) img;

		// Create a buffered image with transparency
		BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

		// Draw the image on to the buffered image
		Graphics2D bGr = bimage.createGraphics();
		bGr.drawImage(img, 0, 0, null);
		bGr.dispose();

		// Return the buffered image
		return bimage;
	}	
	
	/**
	 * Casts AWT color to aplikigo color
	 * @param color AWT color
	 * @return Aplikigo color
	 */
	public static Color awt2color( java.awt.Color color ){ return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()); } 

	/**
	 * Casts aplikigo color to AWT color 
	 * @param color Aplikigo color
	 * @return AWT color
	 */
	public static java.awt.Color color2awt( Color color ){ return new java.awt.Color(color.red(), color.green(), color.blue(), color.alpha()); }

	/**
	 * Moves the canvas cursor to the given position
	 * @param c moveTo command information
	 */
	public void moveTo(JXON c) {
		double x = x(c);
		double y = y(c);
		path.moveTo(x, y);
	}

	/**
	 * Draws a line from the canvas position up to the given position
	 * @param c lineTo command information
	 */
	public void lineTo(JXON c) {
		double x = x(c);
		double y = y(c);
		path.lineTo(x, y);
	}

	/**
	 * Draw a line
	 * @param c line command information
	 */
	public void line(JXON c){
		beginPath();
		double[] x = X(c);
		double[] y = Y(c);
		path.moveTo(x[0],y[0]);
		path.lineTo(x[0],y[0]);
		stroke();		
	}
	
	protected void poly(double[] x, double[] y) {
		beginPath();
		path.moveTo(x[0],y[0]);
		for( int i=1; i<x.length; i++) path.lineTo(x[i],y[i]);	    
	}

	protected void poly(JXON c){ poly(X(c),Y(c)); }
	
	/**
	 * Draws a polyline 
	 * @param c polyline command information
	 */
	public void polyline(JXON c){
		poly(c);
		stroke();
	}

	/**
	 * Draws a polygon
	 * @param c polygon command information
	 */
	public void polygon(JXON c){
		poly(c);
		fill();
	}

	/**
	 * Draws a quadratic line from the current canvas position
	 * @param c quadTo command information
	 */
	public void quadTo(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		path.quadTo(x[0], y[0], x[1], y[1]);
	}

	/**
	 * Draws a curve line from the current canvas position
	 * @param c curveTo command information
	 */
	public void curveTo(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		path.curveTo(x[0], y[0], x[1], y[1], x[2], y[2]);
	}

	/**
	 * Draws a text
	 * @param c text command information
	 */
	public void text(JXON c) {
		double x = x(c);
		double y = y(c);
		String str = c.string(CanvasConstants.MESSAGE);
		g.drawString(str, (int)x, (int)y); 
	}

	/**
	 * Draws an image
	 * @param c Image command information
	 */
	public void image(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		double rot = c.real(CanvasConstants.R);
		// boolean reflex = c.getBool(Command.IMAGE_REF);
		String image_path = c.string(CanvasConstants.URL);
		try{
			Image obj = Resource.image(image_path);
			Image img = obj.getScaledInstance((int)(x[1]-x[0]), (int)(y[1]-y[0]), Image.SCALE_SMOOTH);
			int cx = (img.getWidth(null) / 2);
			int cy = (img.getHeight(null) / 2);
			AffineTransform tx = AffineTransform.getRotateInstance(rot, cx, cy);
			AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

			// Drawing the rotated image at the required drawing locations
			g.drawImage(op.filter(toBufferedImage(img), null),(int)x[0], (int)y[0], null);		
		}catch(Exception e) {}
	}

	/**
	 * Starts a path
	 */
	public void beginPath(){ path = new GeneralPath(); }

	/**
	 * Closes a path
	 */
	public void closePath(){ path.closePath(); }

	/**
	 * Fills and closes the maintained path
	 */
	public void fill(){
		path.closePath();
		g.fill(path); 
	}

	/**
	 * Draws the path
	 */
	public void stroke(){ g.draw(path); }

	/**
	 * Gets a color from a command JXON configuration information (uses Color.TAG)
	 * @param c Command info
	 * @return Aplikigo color
	 */
	public Color color(JXON c) { return color(c, Color.TAG); }
	
	/**
	 * Gets a color from its JXON configuration information
	 * @param c Command info
	 * @param TAG Colors tag in the command info 
	 * @return Aplikigo color
	 */	
	public Color color(JXON c, String TAG) { 
		try{
			return new Color(c.object(TAG)); 
		}catch(Exception e) {
			return new Color(255,255,255,255);
		}
	}
	
	/**
	 * Sets the stroke style  
	 * @param c Stroke style information
	 */
	public void strokeStyle(JXON c) {
		if( c.valid(Color.TAG) ) g.setColor(color2awt(color(c))); 
		if( c.valid(CanvasConstants.LINEWIDTH) ) g.setStroke(new BasicStroke(c.integer(CanvasConstants.LINEWIDTH)));
		fillStyle(c);
	}

	/**
	 * Sets the fill style  
	 * @param c Fill style information
	 */
	public void fillStyle(JXON c) {
		if( c.valid(Color.TAG) ) g.setPaint(color2awt(color(c)));
		else{
			java.awt.Color sc = color2awt(color(c, CanvasConstants.STARTCOLOR));
			java.awt.Color ec = color2awt(color(c, CanvasConstants.ENDCOLOR));
			if( c.valid(CanvasConstants.R) ){
				double x = x(c);
				double y = y(c);
				double r = real(c, CanvasConstants.R);
				g.setPaint(new RadialGradientPaint((float)x, (float)y, (float)r, 
						new float[]{0.0f,1.0f}, new java.awt.Color[]{sc,ec}) );
			}else{
				double[] x = X(c);
				double[] y = Y(c);
				g.setPaint(new LinearGradientPaint((float)x[0], (float)y[0], 
						(float)x[1], (float)y[1], new float[]{0.0f,1.0f}, new java.awt.Color[]{sc,ec}));
			}
		}
	}
	
	/**
	 * Runs multiple commands represented in a multi-command JXON configuration information
	 * @param c Multi-command
	 */
	public void compound( JXON c ){
		Object[] commands = c.array(CanvasConstants.COMMANDS);
		for( Object v : commands ){ command((JXON)v); }
	}
	
	/**
	 * Translates a command
	 * @param c Command translation information
	 * @return Translated command
	 */
	public JXON translate( JXON c ) {
		c = (JXON)c.copy();
		c.set(CanvasConstants.COMMAND, CanvasConstants.COMPOUND);
		return translate(c, x(c), y(c)); 	    
	}
	
	/**
	 * Rotates a command
	 * @param c Command rotation information
	 * @return Rotated command
	 */
	public JXON rotate( JXON c ) {
		c = (JXON)c.copy();
		c.set(CanvasConstants.COMMAND, CanvasConstants.COMPOUND);
		return rotate(c, x(c), y(c), real(c,CanvasConstants.R)); 	    
	}
	
	/**
	 * Scales a command
	 * @param c Command scaling information
	 * @return Scaled command
	 */
	public JXON scale( JXON c ) {
		c = (JXON)c.copy();
		c.set(CanvasConstants.COMMAND, CanvasConstants.COMPOUND);
		return scale(c, x(c), y(c)); 
	}
	
	/**
	 * Fits a command to the render's size
	 * @param c Command to fit
	 * @return Command adjusted to the render's size
	 */
	public JXON fit( JXON c ) {
		c = (JXON)c.copy();
		c.set(CanvasConstants.COMMAND, CanvasConstants.COMPOUND);
		double x = x(c);
		double y = y(c);
		boolean keepAspectRatio = c.bool(CanvasConstants.R);
		Dimension d = render.getSize();
		double w = d.getWidth();
		double h = d.getHeight();
		if(keepAspectRatio) {
			double scale;
			if( w*x < h*y ) scale = w*x;
			else scale = h*y;
			x = scale;
			y = scale;
		}else {
			x *= w;
			y *= h;
		}
		return scale(c,x,y);
	}
		
	/**
	 * Adds a custom command
	 * @param id Id of the custom command
	 * @param command Command configuration information
	 */
	public void addCustomCommand(String id, JXON command) { custom.put(id, command); }
	
	/**
     * Draws an object
	 * @param c Object to draw
	 */
	@Override
	public void draw( JXON c ) { 
		JXON json = init((JXON)c.copy());
		command(json); 
	}
	
	/**
	 * Initializes the canvas and render if required (for example if using custom commands)
	 * @param c Command initialization information
	 * @return Command initialized and ready for drawing in the canvas 
	 */
	protected JXON init( JXON c ) {
		JXON cc = custom.get(type(c));  
		if( cc != null ) {
			c = (JXON)cc.copy();
			c.set(CanvasConstants.COMMAND, CanvasConstants.COMPOUND);
		}

		if(c.get(CanvasConstants.COMMANDS)!=null) {
			c = (JXON)c.copy();
			Object[] obj = c.array(CanvasConstants.COMMANDS);
			for(int i=0; i<obj.length; i++)
				obj[i] = init((JXON)obj[i]);
			c.set(CanvasConstants.COMMANDS, obj);
		}
		int k = primitives.get(type(c));
		switch(k) {
			case 16: return translate(c);
			case 17: return rotate(c);
			case 18: return scale(c);
			case 19: return fit(c);
		}
		return c;
	}
	
	/**
	 * Runs the command
	 * @param c Command to run
	 */
	protected void command( JXON c ){
		if( c==null ) return;
		String type = c.string(CanvasConstants.COMMAND);
		if( type == null ) return;
		try{
			Integer cId = primitives.get(type);
			if( cId != null)
				switch(cId){
					case 0: compound(c); break; 
					case 1: moveTo(c); break; 
					case 2: lineTo(c); break; 
					case 3: quadTo(c); break; 
					case 4: curveTo(c); break; 
					case 5: text(c); break; 
					case 6: image(c); break; 
					case 7: beginPath(); break; 
					case 8: closePath(); break; 
					case 9: stroke(); break; 
					case 10: fill(); break; 
					case 11: strokeStyle(c); break; 
					case 12: fillStyle(c); break; 
					case 13: line(c); break; 
					case 14: polyline(c); break; 
					case 15: polygon(c); break; 
				}
		}catch(Exception e){}	
	}

	/**
	 * Sets custom command for the canvas
	 * @param c Canvas configuration information (custom commands)
	 */
	@Override
	public void config(JXON c) {
		custom.clear();
		Object[] commands = c.array(CanvasConstants.CUSTOM);
		for( int i=0; i<commands.length; i++) {
			JXON x = (JXON)commands[i];
			custom.put(type(x), x);
		}
	}

	/**
	 * Gets subcomponents of the canvas (just the canvas itself)
	 * @param component Subcomponent id
	 * @return Itself if the sent id corresponds to the canvas id, <i>null</i> otherwise
	 */
	@Override
	public Component get(String component) {
		if( component.equals(id()) ) return this;
		return null;
	}

	/**
	 * Gets the canvas id
	 * @return Canvas id 
	 */
	@Override
	public String id() { return id; }

	/**
	 * Sets the canvas id
	 * @param id Canvas id 
	 */
	@Override
	public void id(String id) { this.id = id; }
}