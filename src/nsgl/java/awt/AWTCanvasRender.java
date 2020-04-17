package nsgl.java.awt;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import nsgl.generic.Collection;
import nsgl.generic.array.Vector;
import nsgl.generic.hashmap.HashMap;
import nsgl.gui.Canvas;
import nsgl.gui.paint.CanvasRender;
import nsgl.gui.paint.Drawable;

public class AWTCanvasRender extends PanelComponent implements CanvasRender{
	protected Vector<Drawable> objects = new Vector<Drawable>();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1691456845015325692L;

	protected AWTCanvas canvas = new AWTCanvas();
	
	protected HashMap<String, Image> images = new HashMap<String,Image>();
	
	public AWTCanvasRender(){ this("",new Color(255,255,255)); }
	
	public AWTCanvasRender( String id ){ this(id,new Color(255,255,255)); }

	public AWTCanvasRender( String id, Color background_color ){
		super(id); 
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
	public Canvas getCanvas(){ return canvas; }

	@Override
	public Collection<Drawable> objects(){ return objects; }

	@Override
	public void setCanvas(Canvas canvas){}

}