package nsgl.js;

import nsgl.gui.EditorView;
import nsgl.web.servlet.Application;

public class JSEditorView extends JSView implements EditorView{
	public JSEditorView(String id){ super(id); }
	public JSEditorView(String id, Application servlet){ super(id, servlet); }


	@Override
	public void highlight(int row){ run("highlight",row); }

	@Override
	public void locate(int row, int column){ run("locateCursor",row,column); }

	@Override
	public void setText(String txt){ run("setText",txt); }
}