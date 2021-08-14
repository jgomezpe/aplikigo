package aplikigo.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.HashMap;

import javax.swing.JPanel;

import aplikigo.Component;
import jxon.JXON;

public class CanvasRender extends JPanel implements aplikigo.gui.canvas.Render{
	protected JXON object = null;
	protected String id;
	
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
		id = ""+serialVersionUID;
	}
	
	@Override
	public void init() { object = null; }

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

	@Override
	public aplikigo.gui.Canvas canvas(){ return canvas; }

	@Override
	public void canvas(aplikigo.gui.Canvas canvas){
	    this.canvas = ((Canvas)canvas);
	    this.canvas.setRender(this);
	}

	@Override
	public Component get(String component) {
	    if( component.equals(id()) ) return this;
	    return this.canvas().get(component);
	}

	@Override
	public String id() { return id; }

	@Override
	public void id(String id) { this.id = id; }
}