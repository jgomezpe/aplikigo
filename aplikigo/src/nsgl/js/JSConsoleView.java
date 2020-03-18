package nsgl.js;

import nsgl.gui.ConsoleView;
import nsgl.web.Servlet;

public class JSConsoleView extends JSView implements ConsoleView{
	protected String out="";
	protected String err="";
	
	public JSConsoleView(String id){ super(id); }

	public JSConsoleView(String id, Servlet servlet){ super(id, servlet); }

	@Override
	public void display(boolean output){
		if( output ) run("out", out);
		else run("error",err);
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
