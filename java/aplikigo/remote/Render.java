package aplikigo.remote;

import aplikigo.net.Channel;
import jxon.JXON;

public class Render extends Component implements aplikigo.gui.Render{

	public Render(String id, Channel client){ super(id, client); }

	@Override
	public void config(JXON json) { try{ run("config", json); }catch(Exception e) {} 	}

	@Override
	public void render(Object obj) { try{ run("render", obj); }catch(Exception e) {} }

	@Override
	public void init() { try{ run("init"); }catch(Exception e) {}  }
}