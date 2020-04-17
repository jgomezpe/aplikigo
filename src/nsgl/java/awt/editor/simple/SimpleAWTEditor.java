package nsgl.java.awt.editor.simple;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import nsgl.java.awt.AWTEditorView;
import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;

public class SimpleAWTEditor extends AWTEditorView{
	public SimpleAWTEditor(String id){ super(id); }
	
	public void setStyle(KeyMap<String, SyntaxStyle> styles ){ ((SyntaxEditPanel)editArea).setStyle(styles); }
	
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new SyntaxEditPanel();
		return editArea;	
	}
	
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new JScrollPane();
		return scroll; 
	}
	
	@SuppressWarnings("unchecked")
	public void setLexer( Lexer tokenizer, KeyMap<Character, ?> converter ){ ((SyntaxEditPanel)editArea).setLexer(tokenizer, (KeyMap<Character, String>)converter); }
	
}