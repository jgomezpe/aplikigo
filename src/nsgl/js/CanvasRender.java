package nsgl.js;

import nsgl.app.net.Channel;
import nsgl.gui.Drawable;

public class CanvasRender extends Component implements nsgl.gui.canvas.Render{
	protected Drawable object = null;
	protected Canvas canvas;

	public CanvasRender(String id, Channel client){
		super(id, client);
		canvas = new Canvas(this); 
	}
	
	@Override
	public nsgl.gui.Canvas getCanvas(){ return canvas; }

	@Override
	public void init() { object = null; }

	@Override
	public void setCanvas(nsgl.gui.Canvas canvas){ this.canvas = (Canvas)canvas; }	
}
