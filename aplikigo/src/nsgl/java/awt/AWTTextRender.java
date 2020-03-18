package nsgl.java.awt;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import nsgl.gui.TextRender;

public class AWTTextRender extends PanelComponent implements TextRender{
	protected String id;
	
	BorderLayout layout = new BorderLayout();
	JTextPane textArea = new JTextPane();
	JScrollPane scroll;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8976916928457960190L;

	public AWTTextRender(){
		scroll = new JScrollPane(textArea);
		this.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);		
	}
	
	public AWTTextRender( String id ){
		this();
		this.id = id;
	}

	@Override
	public void render(String str){ textArea.setText(str);	}
	
	@Override
	public void add(String str){ textArea.setText(textArea.getText()+str); }
	
	@Override
	public void init() { textArea.setText(""); }

	@Override
	public void render(){}
}
