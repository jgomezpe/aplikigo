package nsgl.app.remote;

import nsgl.app.net.Channel;
import nsgl.json.JSON;

public class Render extends Component implements nsgl.gui.Render{

	public Render(String id, Channel client){ super(id, client); }

	@Override
	public void config(JSON json) { try{ run("config", json); }catch(Exception e) {} 	}

	@Override
	public void render(Object obj) { try{ run("render", obj); }catch(Exception e) {} }

	@Override
	public void init() { try{ run("init"); }catch(Exception e) {}  }
}