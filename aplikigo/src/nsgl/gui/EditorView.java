package nsgl.gui;

import nsgl.app.Component;

public interface EditorView extends Component{
	public void setText( String text );
	public void highlight( int row );
	public void locate( int row, int col );
}