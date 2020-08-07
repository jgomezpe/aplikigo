package nsgl.gui;

import nsgl.app.Component;

public interface Editor extends Component{
	void setText(String text);
	
	String getText();

	void highlight(int row);

	void locate(int row, int col);
	
	int[] getLocation();
}