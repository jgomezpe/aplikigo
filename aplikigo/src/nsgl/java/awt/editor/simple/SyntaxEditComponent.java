package nsgl.java.awt.editor.simple;

import nsgl.generic.keymap.KeyMap;
import nsgl.language.Lexer;

public interface SyntaxEditComponent {
	public void setLexer(Lexer tokenizer, KeyMap<Character,String> token_style);
	public void setStyle( KeyMap<String, SyntaxStyle> styles );
	public String getText();
	public void setText( String txt );
	public void update();
}
