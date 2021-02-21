package aplikigo.gui.awt.syntaxstyle;

import java.util.HashMap;

import lifya.Lexer;

public interface Component {
	public void setLexer(Lexer tokenizer, HashMap<String,String> token_style);
	public void setStyle( HashMap<String, SyntaxStyle> styles );
	public String getText();
	public void setText( String txt );
	public void update();
}
