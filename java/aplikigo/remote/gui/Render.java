package aplikigo.remote.gui;

import aplikigo.remote.Component;
import aplikigo.server.JXONBaseServer;
import jxon.JXON;

public class Render extends Component implements aplikigo.gui.Render{

	public Render(String id, JXONBaseServer server){ super(id, server); }

	@Override
	public void config(JXON json) { try{ run(id(), "config", json); }catch(Exception e) {} 	}

	@Override
	public void render(Object obj) { try{ run(id(), "render", obj); }catch(Exception e) {} }

	@Override
	public void init() { try{ run(id(), "init"); }catch(Exception e) {}  }
}