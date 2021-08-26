/**
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <h3>License</h3>
 *
 * Copyright (c) 2019 by Jonatan Gomez-Perdomo. <br>
 * All rights reserved. <br>
 *
 * <p>Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <ul>
 * <li> Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the copyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * </ul>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
 * (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
 * @version 1.0
 */
package aplikigo.awt.syntaxstyle;

import java.util.HashMap;

import aplikigo.gui.Color;
import speco.object.JXONfyable;
import speco.jxon.JXON;
import jxon.JXONReader;
import speco.array.Array;
import speco.object.Configurable;

/**
 * <p>Syntax style definition for the editor</p>
 *
 */
public class SyntaxStyle implements JXONfyable, Configurable{
	public static final String STYLES = "styles";
	public static final String REGULAR = "regular";
	public static final String STYLE = "style";
	public static final String DEF = "define";
	public static final String ITALIC = "italic";
	public static final String BOLD = "bold";
	public static final String UNDER_LINE = "under_line";
	public static final String FONT = "font";
	public static final String SIZE = "size";
	
	protected String tag;
	protected boolean italic=false;
	protected boolean bold=false;
	protected boolean under_line=false;
	protected int size=0;
	protected String font_family=null;
	protected Color color=null;
	
	/**
	 * Creates an style for a lexema type
	 * @param tag Lexema type
	 * @param font_family Font family
	 * @param font_size Font size
	 * @param bold If bold or not
	 * @param italic If italic or not
	 * @param under_line If underlined or not
	 * @param color Font color
	 */
	public SyntaxStyle( String tag, String font_family, int font_size, boolean bold, boolean italic, boolean under_line, Color color ){
		this.tag = tag;
		this.font_family = font_family;
		this.size = font_size;
		this.bold = bold;
		this.italic = italic;
		this.under_line = under_line;
		this.color = color;
	}
	
	/**
	 * Creates a lexema type style
	 * @param json JXON configuration information
	 */
	public SyntaxStyle(JXON json){ this.config(json); }
	
	public boolean italic(){ return italic; }
	public boolean bold(){ return bold; }
	public boolean under_line(){ return under_line; }
	public String font_family(){ return font_family; }
	public String tag(){ return tag; }
	public int font_size(){ return size; }
	public Color color(){ return color;	}
	
	/**
	 * Gets the syntactic coloring of text (style) table 
	 * @param styles JXON styles configuration information text  
	 * @return Syntactic coloring of text (style) table 
	 */
	public static HashMap<String, SyntaxStyle> get( String styles ){
		try {
			JXON json = JXONReader.apply(styles);
			HashMap<String, SyntaxStyle> km = new HashMap<String,SyntaxStyle>();
			@SuppressWarnings("unchecked")
			Array<Object> objs = (Array<Object>)json.get(STYLES);
			for( Object o:objs ){
				SyntaxStyle style = new SyntaxStyle((JXON)o);
				km.put(style.tag,style);
			}
			return km;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Creates a JXON configuration information for the Style
	 * @return JXON configuration information for the Style
	 */
	@Override
	public JXON jxon() {
		JXON json = new JXON();
		json.set(DEF, tag);
		json.set(SIZE, font_size());
		if(bold()) json.set(BOLD, "true");
		if(italic()) json.set(ITALIC, "true");
		if(under_line()) json.set(UNDER_LINE, "true");
		if(font_family()!=null)json.set(FONT, font_family());
		if(color()!=null) json.set(Color.TAG, color().jxon());
		return json;
	}

	protected boolean value( JXON json, String tag ){ return json.bool(tag); }

	/**
	 * Configures a lexema type style
	 * @param json JXON configuration information
	 */
	@Override
	public void config(JXON json){
		size = json.integer(SIZE);
		bold = value(json,BOLD);
		italic = value(json,ITALIC);
		under_line = value(json,UNDER_LINE);
		color = new Color(json.object(Color.TAG));
		font_family = (String)json.get(FONT); 
		tag = (String)json.get(DEF); 
	}

/*	public static void main( String[] args ){
		SyntaxStyleInstance si = new SyntaxStyleInstance();
		String style =  "[[\"style\",\"regular\",[\"SansSerif\",12]],[\"style\",\"undef\",["+color(java.awt.Color.pink)+"]],[\"style\",\"comment\",[\"italic\","+color(java.awt.Color.gray)+"]],[\"style\",\"symbol\",["+color(java.awt.Color.blue)+"]],[\"style\",\"stitch\",["+color(java.awt.Color.red)+"]],[\"style\",\"reserved\",[\"bold\"]],[\"style\",\"remnant\",["+color(java.awt.Color.orange)+"]]]"; // "[[\"style\",\"normal\",[3,[\"color\",3,4,5,255],\"italic\",\"bold\",\"Sans Serif\"]]]";
		System.out.println(style);
		ImmutableKeyMap<String, SyntaxStyle> styles = get(style);
		for( SyntaxStyle s:styles ) System.out.println( si.store(s) );
	}  */
}