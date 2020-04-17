package nsgl.gui;

import nsgl.app.Component;

public interface EditorController extends Component{
// Methods called by the View	
	public void text( String text );
	public void position( int row, int col );
}