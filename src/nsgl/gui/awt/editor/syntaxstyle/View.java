package nsgl.gui.awt.editor.syntaxstyle;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;

public class View extends nsgl.gui.awt.editor.View{
	public View(String id){ super(id); }
	
	public void setStyle(KeyMap<String, SyntaxStyle> styles ){ ((Panel)editArea).setStyle(styles); }
	
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new Panel(id);
		return editArea;	
	}
	
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new JScrollPane();
		return scroll; 
	}
	
	@SuppressWarnings("unchecked")
	public void setLexer( Lexer tokenizer, KeyMap<String, ?> converter ){ ((Panel)editArea).setLexer(tokenizer, (KeyMap<String, String>)converter); }
	
}