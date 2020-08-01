package nsgl.js;

import nsgl.communication.Channel;
import nsgl.gui.editor.View;

public class EditorView extends Component implements View{
	public EditorView(){}
	public EditorView(String id, Channel client){ super(id, client); }

	@Override
	public void highlight(int row){ try{ run("highlight",row); }catch(Exception e) {} }

	@Override
	public void locate(int row, int column){ try{ run("locateCursor",row,column); }catch(Exception e) {} }

	@Override
	public void setText(String txt){ try{ run("setText",txt); }catch(Exception e) {} }
}