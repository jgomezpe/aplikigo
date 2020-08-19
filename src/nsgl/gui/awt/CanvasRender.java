package nsgl.gui.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import nsgl.generic.hashmap.HashMap;
import nsgl.json.JSON;

public class CanvasRender extends JPanel implements nsgl.gui.canvas.Render{
	protected JSON object = null;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691456845015325692L;

	protected Canvas canvas;
	
	protected HashMap<String, Image> images = new HashMap<String,Image>();
	
	public CanvasRender(){ this(new Color(255,255,255)); }
	
	public CanvasRender( Color background_color ){
		setBackground(background_color);
		canvas = new Canvas(this);
	}
	
	@Override
	public void init() { object = null; }

	@Override
	public void render( JSON obj ){
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

	@Override
	public nsgl.gui.Canvas getCanvas(){ return canvas; }

	@Override
	public void setCanvas(nsgl.gui.Canvas canvas){
	    this.canvas = ((Canvas)canvas);
	    this.canvas.setRender(this);
	}
}