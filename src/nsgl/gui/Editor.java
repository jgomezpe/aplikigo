package nsgl.gui;

import nsgl.app.ViewController;
import nsgl.gui.editor.Controller;
import nsgl.gui.editor.View;

public class Editor extends ViewController implements Controller, View{
	public Editor() {}
	
	public Editor(String id, Object controller, Object view) { super(id, controller, view); }

	@Override
	public void setText(String text){ ((View)view).setText(text); }

	@Override
	public void highlight(int row) { ((View)view).highlight(row); }

	@Override
	public void locate(int row, int col) {  ((View)view).locate(row, col); }

	@Override
	public void text(String text) { ((Controller)controller).text(text);	}

	@Override
	public void position(int row, int col) { ((Controller)controller).position(row,col); }
}