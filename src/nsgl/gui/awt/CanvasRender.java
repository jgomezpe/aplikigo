package nsgl.gui.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

import nsgl.generic.Collection;
import nsgl.generic.array.Vector;
import nsgl.generic.hashmap.HashMap;
import nsgl.gui.Drawable;

public class CanvasRender extends JPanel implements nsgl.gui.CanvasRender{
	protected Vector<Drawable> objects = new Vector<Drawable>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691456845015325692L;

	protected Canvas canvas = new Canvas();
	
	protected HashMap<String, Image> images = new HashMap<String,Image>();
	
	public CanvasRender(){ this(new Color(255,255,255)); }
	
	public CanvasRender( Color background_color ){
		setBackground(background_color);
	}

	@Override
	public void render( Object obj ){
		add(obj);
		updateUI();
	}
	
	/**
	 * Paints the graphic component
	 * @param g Graphic component
	 */
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		canvas.setGraphics( g );
		render();
	}		

	@Override
	public nsgl.gui.Canvas getCanvas(){ return canvas; }

	@Override
	public Collection<Drawable> objects(){ return objects; }

	@Override
	public void setCanvas(nsgl.gui.Canvas canvas){}

}