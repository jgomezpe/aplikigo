package aplikigo.gui.canvas;

import aplikigo.gui.Color;
import jxon.JXON;

public class Util{
	public final static String COMMAND="command";
	public final static String SCALE="scale";
	public final static String ROTATE="rotate";
	public final static String TRANSLATE="translate";
	public final static String COMPOUND="compound";
	public final static String MOVETO="moveTo";
	public final static String LINETO="lineTo";
	public final static String QUADTO="quadTo";
	public final static String CURVETO="curveTo";
	public final static String TEXT="text";
	public final static String IMAGE="image";
	public final static String BEGIN="beginPath";
	public final static String CLOSE="closePath";
	public final static String STROKE="stroke";
	public final static String FILL="fill";
	public final static String STROKESTYLE="strokeStyle";
	public final static String FILLSTYLE="fillStyle";
	public final static String LINE="line";
	public final static String POLYLINE="polyline";
	public final static String POLYGON="polygon";
	public final static String FIT="fit";
	// Arguments of the command
	public final static String CUSTOM="custom";
	public final static String COMMANDS="commands";
	public final static String X="x";
	public final static String Y="y";
	public final static String MESSAGE="message";
	public final static String URL="url";
	public final static String IMAGE_REF="reflection";
	public final static String LINEWIDTH="lineWidth";
	public final static String RADIAL="radial";
	public final static String STARTCOLOR="startcolor";
	public final static String ENDCOLOR="endcolor";
	public final static String R="r";

	
	public static JXON create(String type) {
		JXON jxon = new JXON();
		jxon.set(COMMAND, type);
		return jxon;
	}
	
	protected static JXON point( String command, double x, double y ){
		JXON json = create(command);
		json.set(X, (Double)x);
		json.set(Y, (Double)y);
		return json;		
	}
	
	public static JXON moveTo( double x, double y ){ return point(MOVETO,x,y); }
	
	public static JXON lineTo( double x, double y ){ return point(LINETO,x,y); }

	public static JXON poly( String command, double[] x, double[] y ){
		JXON json = create(command);
		json.set(X, x);
		json.set(Y, y);
		return json;
	}
	
	public static JXON quadTo( double cp1x, double cp1y, double x, double y )
	{ return poly(QUADTO, new double[]{cp1x,x}, new double[]{cp1y,y});	}
	
	public static JXON curveTo( double cp1x, double cp1y, double cp2x, double cp2y, double x, double y )
	{ return poly(CURVETO, new double[]{cp1x,cp2x,x}, new double[]{cp1y,cp2y,y});	}
	
	public static JXON line( double start_x, double start_y, double end_x, double end_y )
	{ return poly( LINE, new double[]{ start_x, end_x }, new double[]{ start_y, end_y }); }
	
	public static JXON polygon( double[] x, double[] y ){ return poly(POLYGON, x, y); }

	public static JXON polyline( double[] x, double[] y ){ return poly(POLYLINE, x, y); }

	public static JXON rect( double x, double y, double width, double height )
	{ return polyline( new double[]{x, x+width, x+width, x, x}, new double[]{y,y,y+height,y+height, y} ); }

	public static JXON image( double x, double y, double width, double height, int rot, boolean reflex, String url ){
		JXON img = poly(IMAGE, new double[] {x, width}, new double[] {y,height} );
		img.set(R, rot);
		img.set(URL, url);
		return img;
	}

	public static JXON text( double x, double y, String str ){
		JXON json = point(TEXT, x, y);
		json.set(MESSAGE, str);
		return json;
	} 	
	
	public static JXON beginPath(){ return create(BEGIN); }
	public static JXON closePath(){ return create(CLOSE); }
	public static JXON stroke(){ return create(STROKE); }
	public static JXON fill(){ return create(FILL); }
	
	protected static JXON colorStyle( String STYLE, Color color ){
		JXON json = create(STYLE);
		json.set(Color.TAG, color.jxon());
		return json;
	} 
	
	protected static JXON linearGradientStyle( String STYLE, double x1, double y1, double x2, double y2, Color startcolor, Color endcolor ){
		JXON c = poly(STYLE, new double[]{x1,x2}, new double[]{y1,y2});
		c.set(STARTCOLOR, startcolor.jxon());
		c.set(ENDCOLOR, endcolor.jxon());
		return c;
	} 

	protected static JXON radialGradientStyle( String STYLE, double x, double y, double r, Color startcolor, Color endcolor ){ 
		JXON c = linearGradientStyle(STYLE, x, y, x, y, startcolor, endcolor);
		c.set(X, (Double)x);
		c.set(Y, (Double)y);
		c.set(R, (Double)r);
		return c;
	} 
	
	public static JXON strokeStyle( Color color ){ return colorStyle(STROKESTYLE, color); }
	
	public static JXON strokeStyle( Color color, int lineWidth ){
		JXON c = strokeStyle(color); 
		c.set(LINEWIDTH, lineWidth);
		return c;
	}
	
	public static JXON strokeStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor )
	{ return linearGradientStyle(STROKESTYLE, x1, y1, x2, y2, startcolor, endcolor); } 

	public static JXON strokeStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor, int lineWidth ){
		JXON c = strokeStyle(x1, y1, x2, y2, startcolor, endcolor);
		c.set(LINEWIDTH, lineWidth);
		return c;
	} 
	
	public static JXON strokeStyle( double x, double y, double r, Color startcolor, Color endcolor )
	{ return radialGradientStyle(STROKESTYLE, x, y, r, startcolor, endcolor); } 

	public static JXON strokeStyle( double x, double y, double r, Color startcolor, Color endcolor, int lineWidth ){
		JXON c = strokeStyle(x, y, r, startcolor, endcolor);
		c.set(LINEWIDTH, lineWidth);
		return c;
	} 
	
	public static JXON fillStyle( Color color ){ return colorStyle(FILLSTYLE, color); }
	
	public static JXON fillStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor )
	{ return linearGradientStyle(FILLSTYLE, x1, y1, x2, y2, startcolor, endcolor); } 

	public static JXON fillStyle( double x, double y, double r, Color startcolor, Color endcolor )
	{ return radialGradientStyle(FILLSTYLE, x, y, r, startcolor, endcolor); } 
	
/*	
	public JXON jsonRect( int x, int y, int width, int height ){ return json( RECT, x, y, width, height ); }
	
	public JXON jsonFillRect( int x, int y, int width, int height ){ return json( FILLRECT, x, y, width, height ); }
	
	public JXON jsonOval( int x, int y, int width, int height ){ return json( OVAL, x, y, width, height ); }
	
	public JXON jsonFillOval( int x, int y, int width, int height ){ return json( FILLOVAL, x, y, width, height); }
	
	public JXON jsonArc(int x, int y, int width, int height, int startAngle, int endAngle){
		JXON json = json(ARC, x, y, width, height );
		json.set(START_ANGLE, startAngle);
		json.set(END_ANGLE, endAngle);
		return json;
	} 
	
	public JXON jsonFillArc(int x, int y, int width, int height, int startAngle, int endAngle){
		JXON json = json(FILLARC, x, y, width, height );
		json.set(START_ANGLE, startAngle);
		json.set(END_ANGLE, endAngle);
		return json;
	}

*/	
}