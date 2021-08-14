package aplikigo.awt;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import aplikigo.Component;
import aplikigo.gui.StringRender;
import jxon.JXON;

public class AWTStringRender extends JPanel implements StringRender{
	protected String id;
	
	BorderLayout layout = new BorderLayout();
	JTextPane textArea = new JTextPane();
	JScrollPane scroll;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8976916928457960190L;

	public AWTStringRender(){
		scroll = new JScrollPane(textArea);
		this.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);		
	}
	
	public AWTStringRender( String id ){
		this();
		this.id = id;
	}

	@Override
	public void render(String str){ textArea.setText(str);	}
	
	@Override
	public void add(String str){ textArea.setText(textArea.getText()+"\n"+str); }
	
	@Override
	public void init() { textArea.setText(""); }

	@Override
	public void setFont( Font font ) { if( textArea!=null) textArea.setFont(font); }

	@Override
	public void config(JXON json) {}

	@Override
	public Component get(String component) {
	    // TODO Auto-generated method stub
	    return null;
	}

	@Override
	public String id() { return id; }

	@Override
	public void id(String id) { this.id = id; }
}
