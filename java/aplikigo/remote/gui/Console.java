package aplikigo.remote.gui;

import aplikigo.remote.Component;
import aplikigo.server.JXONBaseServer;

public class Console extends Component implements aplikigo.gui.Console{
    protected String out="";
    protected String err="";
	
    public Console(String id, JXONBaseServer server){ super(id, server); }

    @Override
    public void display(boolean output){
	try {
	    if( output ) run(id(), "out", out);
	    else run(id(), "error", err);
	}catch(Exception e) {}
    }

    @Override
    public void error(String txt) {
	err = txt;
	display(false);
    }

    @Override
    public void out(String txt) {
	out = txt;
	display(true);
    }
}
