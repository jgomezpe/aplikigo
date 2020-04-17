package nsgl.java.awt.editor.rsyntax;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextScrollPane;

import nsgl.java.awt.AWTEditorView;
import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;

public class RSyntaxEditor extends AWTEditorView{
	protected AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
	protected RSyntaxTokenMaker tok;
	protected RTextScrollPane scroll;
	
	protected int src;

	public RSyntaxEditor( String id ){ this( id, 0 ); }
    
	public RSyntaxEditor( String id, int src ){
		super(id);
		this.src = src;
		RSyntaxTextArea textArea = (RSyntaxTextArea)editArea;
		atmf.putMapping("text/"+id, "unalcol.gui.editor.rsyntax.RSyntaxTokenMaker");
		textArea.setSyntaxEditingStyle("text/"+id);
		textArea.setCodeFoldingEnabled(true);
		tok = RSyntaxTokenMaker.lastInstance;
	}
	
	public void setSrc( int src ){ this.src = src; }
	public int src(){ return src; }
    
	@SuppressWarnings("unchecked")
	@Override
	public void setLexer( Lexer tokenizer, KeyMap<Character, ?> converter ){ this.tok.setLexer(src, tokenizer, (KeyMap<Character,Integer>)converter); }
	
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new RSyntaxTextArea();
		return editArea;	
	}
	
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new RTextScrollPane((RSyntaxTextArea)editArea);
		return scroll; 
	}
}