package aplikigo.gui;

import jxon.Castable;
import jxon.Configurable;
import jxon.JXON;

public class Color implements Comparable<Object>, Castable, Configurable{
	public static final String TAG="color";
	public static final String RED="red";
	public static final String GREEN="green";
	public static final String BLUE="blue";
	public static final String ALPHA="alpha";

	protected int r;
	protected int g;
	protected int b;
	protected int a;
	public Color(int R, int G, int B, int A){
		this.r = R;
		this.g = G;
		this.b = B;
		this.a = A;
	}
	
	public Color(JXON json) { this.config(json); }

	public int red(){ return r; }
	public int green(){ return g; }
	public int blue(){ return b; }
	public int alpha(){ return a; }
	
	public int[] values(){ return new int[]{r,g,b,a}; }

	public int compare(int[] one, int[] two) {
		if( one.length!=two.length ) return one.length-two.length;
		int k=0;
		while( k<one.length && one[k]==two[k] ) k++;
		if(k==one.length) return 0;
		else return one[k]-two[k];
	}
	
	@Override
	public int compareTo(Object other){
		if( !(other instanceof Color) ) return Integer.MAX_VALUE;
		return compare(values(), ((Color)other).values());
	}
	
	    @Override
	    public JXON jxon() {
		JXON json = new JXON();
		json.set(Color.RED, red());
		json.set(Color.GREEN, green());
		json.set(Color.BLUE, blue());
		json.set(Color.ALPHA, alpha());
		return json;
	    }
	    
	@Override
	public void config(JXON json){ 
	    //@TODO check type of values, must be integers..
	    r = json.integer(RED);
	    g = json.integer(GREEN);
	    b = json.integer(BLUE);
	    a = json.integer(ALPHA);
	}
}