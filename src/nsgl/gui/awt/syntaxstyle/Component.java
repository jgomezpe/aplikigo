package nsgl.gui.awt.syntaxstyle;

import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;

public interface Component {
	public void setLexer(Lexer tokenizer, KeyMap<String,String> token_style);
	public void setStyle( KeyMap<String, SyntaxStyle> styles );
	public String getText();
	public void setText( String txt );
	public void update();
}
