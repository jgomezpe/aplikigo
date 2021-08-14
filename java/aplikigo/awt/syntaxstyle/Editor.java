package aplikigo.awt.syntaxstyle;

import java.util.HashMap;

import javax.swing.JScrollPane;
import javax.swing.text.JTextComponent;

import lifya.Lexer;

public class Editor extends aplikigo.awt.Editor{
	public Editor(String id){ super(id); }
	
	public void setStyle(HashMap<String, SyntaxStyle> styles ){ ((Panel)editArea).setStyle(styles); }
	
	public JTextComponent editArea(){
		if( editArea==null ) editArea = new Panel(id);
		return editArea;	
	}
	
	public JScrollPane scroll(){
		if( scroll==null ) scroll = new JScrollPane();
		return scroll; 
	}
	
	@SuppressWarnings("unchecked")
	public void setLexer( Lexer tokenizer, HashMap<String, ?> converter ){ ((Panel)editArea).setLexer(tokenizer, (HashMap<String, String>)converter); }

	
}