package nsgl.js;

import nsgl.generic.Collection;
import nsgl.generic.array.Vector;
import nsgl.gui.Canvas;
import nsgl.gui.paint.CanvasRender;
import nsgl.gui.paint.Drawable;
import nsgl.web.Servlet;

public class JSCanvasRender extends JSView implements CanvasRender{
	protected Vector<Drawable> objects = new Vector<Drawable>();
	protected JSCanvas canvas;

	public JSCanvasRender(String id){
		super(id);
		canvas = new JSCanvas(this); 
	}
	
	public JSCanvasRender(String id, Servlet servlet){
		super(id, servlet);
		canvas = new JSCanvas(this); 
	}
	
	@Override
	public Canvas getCanvas(){ return canvas; }

	@Override
	public Collection<Drawable> objects(){ return objects; }

	@Override
	public void setCanvas(Canvas canvas){}
	
	@Override
	public void render( Object obj ){
		add(obj);
		render();
	}	
}
