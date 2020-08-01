package nsgl.gui.editor;

public interface View{
	public void setText( String text );
	public void highlight( int row );
	public void locate( int row, int col );
}