package aplikigo.awt.syntaxstyle;

import java.io.IOException;
import java.util.HashMap;

import aplikigo.gui.Color;
import jxon.Castable;
import jxon.Configurable;
import jxon.JXON;
import speco.array.Array;

public class SyntaxStyle implements Castable, Configurable{
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
	
	public SyntaxStyle( String tag, String font_family, int font_size, boolean bold, boolean italic, boolean under_line, Color color ){
		this.tag = tag;
		this.font_family = font_family;
		this.size = font_size;
		this.bold = bold;
		this.italic = italic;
		this.under_line = under_line;
		this.color = color;
	}
	
	public SyntaxStyle(JXON json) throws IOException{ this.config(json); }
	
	public boolean italic(){ return italic; }
	public boolean bold(){ return bold; }
	public boolean under_line(){ return under_line; }
	public String font_family(){ return font_family; }
	public String tag(){ return tag; }
	public int font_size(){ return size; }
	public Color color(){ return color;	}
	
	public static HashMap<String, SyntaxStyle> get( String styles ){
		try {
			JXON json = JXON.parse(styles);
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