package nsgl.gui.awt.editor.syntaxstyle;

import java.awt.Color;

import javax.swing.JTextPane;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import javax.swing.text.Style;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;
import javax.swing.text.StyledDocument;

import nsgl.character.CharacterSequence;
import nsgl.generic.array.Vector;
import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;
import nsgl.language.Token;

public class Panel extends JTextPane implements Component{
	protected SyntaxStyle def;
	protected KeyMap<String,SyntaxStyle> styles;
	protected KeyMap<String,String> token_style=null;
	protected Lexer lexer=null;
	protected String src;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2456710620251070411L;

	public Panel(String src){
		super();
		this.src = src;
		this.getDocument().addDocumentListener(new SyntaxDocumentListener());
	}
	
	public void setLexer(Lexer lexer, KeyMap<String,String> token_style){
		this.lexer = lexer;
		this.token_style = token_style;
		update();
	}
	
	public void update(){
		String str = this.getText();
		try { this.getStyledDocument().remove(0, this.getText().length()); }catch(BadLocationException e) {}
		if( str != null && str.length()>0 )	this.setText(str);
	}
	
	protected void setStyle( SyntaxStyle style ){
		StyledDocument doc = this.getStyledDocument();
		Style s = doc.getStyle(style.tag());
		if( s==null ){
			Style parent;
			if( style.tag().equals(SyntaxStyle.REGULAR) ) parent = StyleContext.getDefaultStyleContext().getStyle(StyleContext.DEFAULT_STYLE);
			else parent = doc.getStyle(SyntaxStyle.REGULAR);
			s = doc.addStyle(style.tag(),parent);
		}	
		if( style.font_family()!=null)  StyleConstants.setFontFamily(s, style.font_family());
		if( style.font_size()>0) StyleConstants.setFontSize(s,style.font_size());
		StyleConstants.setItalic(s, style.italic()); 
		StyleConstants.setBold(s, style.bold()); 
		StyleConstants.setUnderline(s, style.under_line());
		int[] c = (style.color()!=null)?style.color().values():null;
		if( c!=null ) StyleConstants.setForeground(s, new Color(c[0],c[1],c[2],c[3]));
	}
	
	public void setStyle(KeyMap<String, SyntaxStyle> styles ){
		String str = this.getText();
		try { this.getStyledDocument().remove(0, this.getText().length()); }catch(BadLocationException e) {}
		for( SyntaxStyle style:styles ) setStyle(style); 
		if( str != null && str.length()>0 )	this.setText(str);
	}
	

	protected class SyntaxDocumentListener implements DocumentListener {
		public SyntaxDocumentListener(){}
	 
	    public void syntax( int pos,int length ){
	    	StyledDocument doc = getStyledDocument();
	        Element root = doc.getDefaultRootElement(); 
	        Element paragraph = root.getElement(root.getElementIndex(pos));
			int start = paragraph.getStartOffset();
			int end = length==0?paragraph.getEndOffset():pos+length;
			Vector<Token> changes = new Vector<Token>();
			while(start<end){
				Element p = root.getElement(root.getElementIndex(start));
				length = p.getEndOffset()-start;
				String code = null;
		        try{ code = doc.getText(start, length); } catch (BadLocationException e1) {}
		        if( code != null && code.length()>0 ){
					Vector<Token> token = lexer.shallow_analize(new CharacterSequence(code,src), start);
					for( Token t:token ){
						t.shift(start);
						changes.add((Token)t);
					}
		        }
				start = p.getEndOffset();
			}

	        Runnable doAssist = new Runnable() {
			@Override
				public void run() {
					try{
						for( Token t:changes )
							doc.setCharacterAttributes(t.location(),t.length(),doc.getStyle(token_style.get(t.type())),true);
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
	
}