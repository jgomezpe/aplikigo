package nsgl.gui;

public interface Console{
	void display( boolean output );	
	public void error( String message );
	public void out( String message );
}