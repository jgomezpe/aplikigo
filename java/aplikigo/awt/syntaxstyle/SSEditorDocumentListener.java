package aplikigo.awt.syntaxstyle;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.StyledDocument;

import lifya.Token;
import speco.array.Array;

/**
 *  <p>Document listener for the Simple Syntax Editor panel</p>
 */
public class SSEditorDocumentListener implements DocumentListener {
	protected SSEditorPanel panel;
	
	/**
	 * Creates a document listener for the SSEditor panel
	 * @param panel Simple Syntax editor panel 
	 */
	public SSEditorDocumentListener(SSEditorPanel panel){ this.panel = panel; }

	/**
	 * Updates syntax coloring for a region of the text
	 * @param pos Starting position
	 * @param length Length of the position to analyze
	 */
	public void syntax( int pos,int length ){
		StyledDocument doc = panel.getStyledDocument();
		Element root = doc.getDefaultRootElement(); 
		Element paragraph = root.getElement(root.getElementIndex(pos));
		int start = paragraph.getStartOffset();
		int end = length==0?paragraph.getEndOffset():pos+length;
		Array<Token> changes = new Array<Token>();
		panel.lexer.removeTokens(false);
		while(start<end){
			Element p = root.getElement(root.getElementIndex(start));
			length = p.getEndOffset()-start;
			String code = null;
			try{ code = doc.getText(start, length); } catch (BadLocationException e1) {}
			if( code != null && code.length()>0 ){
				panel.lexer.init(code);
				Token t;
				while( (t=panel.lexer.next())!=null ){
					t.shift(start);
					changes.add(t);
				}
			}
			start = p.getEndOffset();
		}
		panel.lexer.removeTokens(true);

		Runnable doAssist = new Runnable() {
			@Override
			public void run() {
				try{
					for( Token t:changes )
						doc.setCharacterAttributes(t.start(),t.size(),
								doc.getStyle(panel.token_style.get(t.type())),true);
				}catch(Exception e){}
			}
		};
		SwingUtilities.invokeLater(doAssist);
	}
    
	public void insertUpdate(DocumentEvent e) {
		syntax(e.getOffset(),e.getLength());
	}
    
	public void removeUpdate(DocumentEvent e) {
		int start = e.getOffset();
		syntax(start,0);
	}
    
	public void changedUpdate(DocumentEvent e) {
		//Plain text components do not fire these events
	}
}