package nsgl.gui;

import nsgl.app.Component;
import nsgl.app.VCComponent;

public class Editor extends VCComponent implements EditorController, EditorView{

	public Editor(String id, Component controller, Component view) { super(id, controller, view); }

	@Override
	public void setText(String text){ ((EditorView)view).setText(text); }

	@Override
	public void highlight(int row) { ((EditorView)view).highlight(row); }

	@Override
	public void locate(int row, int col) {  ((EditorView)view).locate(row, col); }

	@Override
	public void text(String text) { ((EditorController)controller).text(text);	}

	@Override
	public void position(int row, int col) { ((EditorController)controller).position(row,col); }
}