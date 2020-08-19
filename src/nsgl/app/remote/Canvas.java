package nsgl.app.remote;

import nsgl.json.JSON;

public class Canvas extends Component implements nsgl.gui.Canvas{
	
	public Canvas( String id ){ super(id); }
	
	@Override
	public void draw( JSON json ){ try{ run("draw", json); }catch(Exception e) {} }

	@Override
	public void config(JSON c) { try{ run("config", c); }catch(Exception e) {} }
}