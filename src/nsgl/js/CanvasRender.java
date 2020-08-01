package nsgl.js;

import nsgl.communication.Channel;
import nsgl.generic.Collection;
import nsgl.generic.array.Vector;
import nsgl.gui.Drawable;

public class CanvasRender extends Component implements nsgl.gui.CanvasRender{
	protected Vector<Drawable> objects = new Vector<Drawable>();
	protected Canvas canvas;

	public CanvasRender(){
		canvas = new Canvas(this); 
	}
	
	public CanvasRender(String id, Channel client){
		super(id, client);
		canvas = new Canvas(this); 
	}
	
	@Override
	public nsgl.gui.Canvas getCanvas(){ return canvas; }

	@Override
	public Collection<Drawable> objects(){ return objects; }

	@Override
	public void setCanvas(nsgl.gui.Canvas canvas){}
	
	@Override
	public void render( Object obj ){
		add(obj);
		render();
	}	
}
