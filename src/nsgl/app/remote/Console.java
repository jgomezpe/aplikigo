package nsgl.app.remote;

import nsgl.app.net.Channel;

public class Console extends Component implements nsgl.gui.Console{
	protected String out="";
	protected String err="";
	
	public Console(String id, Channel client){ super(id, client); }

	@Override
	public void display(boolean output){
	    try {
		if( output ) run("out", out);
		else run("error",err);
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
