package nsgl.gui;

import nsgl.app.Component;

public interface Console extends Component{
	void display( boolean output );	
	public void error( String message );
	public void out( String message );
}