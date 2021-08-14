package aplikigo.remote.gui;

import aplikigo.remote.Component;
import jxon.JXON;

public class Canvas extends Component implements aplikigo.gui.Canvas{
	
	public Canvas( String id ){ super(id); }
	
	@Override
	public void draw( JXON json ){ try{ run("draw", json); }catch(Exception e) {} }

	@Override
	public void config(JXON c) { try{ run("config", c); }catch(Exception e) {} }
}