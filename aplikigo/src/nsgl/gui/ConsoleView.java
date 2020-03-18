package nsgl.gui;

import nsgl.app.Component;

public interface ConsoleView extends Component{
    public void display( boolean output );	
	public void error( String message );
	public void out( String message );
}