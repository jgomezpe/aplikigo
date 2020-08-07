package nsgl.js;

import nsgl.gui.paint.Color;
import nsgl.json.JXON;

public class Canvas implements nsgl.gui.Canvas{
	protected Color color=null;
	protected CanvasRender render;
	
	public Canvas( CanvasRender render ){ this.render = render; }
	
	@Override
	public void draw( JXON json ){ try{ render.run("draw", json); }catch(Exception e) {} }

	@Override
	public void config(JXON c) {try{ render.run("config", c); }catch(Exception e) {} }
}