package nsgl.js;

import nsgl.gui.Color;
import nsgl.json.JSON;

public class Canvas implements nsgl.gui.Canvas{
	protected Color color=null;
	protected CanvasRender render;
	
	public Canvas( CanvasRender render ){ this.render = render; }
	
	@Override
	public void draw( JSON json ){ try{ render.run("draw", json); }catch(Exception e) {} }

	@Override
	public void config(JSON c) {try{ render.run("config", c); }catch(Exception e) {} }
}