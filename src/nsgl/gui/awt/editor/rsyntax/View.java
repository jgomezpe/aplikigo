package nsgl.gui.awt.editor.rsyntax;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import org.fife.ui.rsyntaxtextarea.AbstractTokenMakerFactory;
import org.fife.ui.rsyntaxtextarea.RSyntaxTextArea;
import org.fife.ui.rsyntaxtextarea.TokenMakerFactory;
import org.fife.ui.rtextarea.RTextScrollPane;

import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;

public class View extends nsgl.gui.awt.editor.View{
	protected AbstractTokenMakerFactory atmf = (AbstractTokenMakerFactory)TokenMakerFactory.getDefaultInstance();
	protected TokenMaker tok;
	protected RTextScrollPane scroll;
	
	public View(String id){
		super(id);
		RSyntaxTextArea textArea = (RSyntaxTextArea)editArea;
		atmf.putMapping("text/"+id, "nsgl.gui.editor.rsyntax.RSyntaxTokenMaker");
		textArea.setSyntaxEditingStyle("text/"+id);
		textArea.setCodeFoldingEnabled(true);
		tok = TokenMaker.lastInstance;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public void setLexer( Lexer tokenizer, KeyMap<String, ?> converter ){ this.tok.setLexer(id, tokenizer, (KeyMap<String,Integer>)converter); }
	
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new RSyntaxTextArea();
		return editArea;	
	}
	
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new RTextScrollPane((RSyntaxTextArea)editArea);
		return scroll; 
	}
}