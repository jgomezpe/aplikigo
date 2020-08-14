package nsgl.string.awt;

import java.awt.BorderLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

import nsgl.json.JSON;

public class Render extends JPanel implements nsgl.string.Render{
	protected String id;
	
	BorderLayout layout = new BorderLayout();
	JTextPane textArea = new JTextPane();
	JScrollPane scroll;
	/**
	 * 
	 */
	private static final long serialVersionUID = -8976916928457960190L;

	public Render(){
		scroll = new JScrollPane(textArea);
		this.setLayout(layout);
		this.add(scroll, BorderLayout.CENTER);		
	}
	
	public Render( String id ){
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
	public void config(JSON json) {}
}
