package nsgl.gui;

import nsgl.copy.Copyable;
import nsgl.json.JSON;

public class PaintCommand extends JSON{
	public final static String COMMAND="command";
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
	// Arguments of the command
	public final static String COMMANDS="commands";
	public final static String X="x";
	public final static String Y="y";
	public final static String MESSAGE="message";
	public final static String IMAGE_URL="url";
	public final static String IMAGE_ROT="rotation";
	public final static String IMAGE_REF="reflection";
	public final static String LINEWIDTH="lineWidth";
	public final static String RADIAL="radial";
	public final static String STARTCOLOR="startcolor";
	public final static String ENDCOLOR="endcolor";
	public final static String R="r";
	
	public PaintCommand( String type ){ set(COMMAND, type); }
	
	public PaintCommand( JSON source ){
		try{
			for( String key:source.keys() ){
				if( key.equals(COMMANDS) ){
					Object[] commands = source.getArray(COMMANDS);
					Object[] cs = new Object[commands.length];
					for( int i=0; i<commands.length; i++ ) cs[i] = new PaintCommand((JSON)commands[i]);
					set(key, cs);	
				}else{
					Object obj = source.get(key);
					Copyable c = Copyable.cast(obj);
					set(key, c.copy());
				}
			}
		}catch(Exception e){}	
	}
	
	@Override
	public Object clone(){ return new PaintCommand(this); }
	
	public double[] end(){
		if( x()==null ) return null;
		if( x() instanceof Object[] ){
			Object[] x = getArray(X); 
			Object[] y = getArray(Y); 
			return new double[]{(Double)x[x.length-1], (Double)y[y.length-1]}; 
		}else return new double[]{getReal(X), getReal(Y)};
	}
	
	public String type(){ return getString(COMMAND); }
	
	public Object x(){ try{ return get(X); }catch(Exception e){ return null; } }

	public Object y(){ try{ return get(Y); }catch(Exception e){ return null; } }
	
	public PaintCommand[] commands(){
		Object[] c = getArray(COMMANDS);
		PaintCommand[] commands = new PaintCommand[c.length];
		for( int i=0; i<c.length; i++ ) commands[i] = (PaintCommand)c[i];
		return commands; 
	}

	public void translate( double dx, double dy ){
		if( type().equals(COMPOUND) ){
			PaintCommand[] commands = (PaintCommand[])getArray(COMMANDS);
			for(PaintCommand c:commands ) c.translate(dx, dy);
			return;
		}
		if( x()==null ) return;
		if( x() instanceof Object[] ){
			double[] x = getRealArray(X);
			double[] y = getRealArray(Y);
			for( int i=0; i<x.length; i++ ){
				x[i] += dx;
				y[i] += dy;
			}
			set(X,x);
			set(Y,y);
		}else{
			set(X,dx+getReal(X));
			set(Y,dy+getReal(Y));
		}	
	}
	
	protected double angle( double x1, double y1, double x2, double y2 ){
		double a = (x2-x1); 
		double b = (y2-y1);
		double r = Math.sqrt(a*a+b*b);
		if( r>1e-6 ){
			double alpha = Math.acos(a/r);
			if( b<0 ) alpha = 2.0*Math.PI - alpha;
			return alpha;
		}else return 0.0;
	}
	
	protected double[] rotate( double cx, double cy, double px, double py, double angle ){
		double alpha = angle( cx, cy, px, py ) + angle;
		if( alpha>1e-6 ){
			double a = (px-cx); 
			double b = (py-cy);
			double r = Math.sqrt(a*a+b*b);
			return new double[]{ cx + r*Math.cos(alpha), cy + r*Math.sin(alpha) };
		}else return new double[]{px,py};			
	}
	
	public void rotate( double cx, double cy, double angle ){
		if( type().equals(COMPOUND) ){
			PaintCommand[] commands = (PaintCommand[])getArray(COMMANDS);
			for(PaintCommand c:commands ) c.rotate(cx, cy, angle);
			return;
		}
		if( x()==null ) return;
		if( x() instanceof Object[] ){
			double[] x = getRealArray(X);
			double[] y = getRealArray(Y);
			for( int i=0; i<x.length; i++ ){
				double[] p = rotate( cx, cy, x[i], y[i], angle );
				x[i] = p[0];
				y[i] = p[1];
			}	
			set(X,x);
			set(Y,y);			
		}else{
			double[] p = rotate( cx, cy, getReal(X),getReal(Y), angle);
			set(X,(Double)p[0]);
			set(Y,(Double)p[1]);
		}
	}
	
	protected static PaintCommand point( String command, double x, double y ){
		PaintCommand json = new PaintCommand(command);
		json.set(X, (Double)x);
		json.set(Y, (Double)y);
		return json;		
	}
	
	public static PaintCommand moveTo( double x, double y ){ return point(MOVETO,x,y); }
	
	public static PaintCommand lineTo( double x, double y ){ return point(LINETO,x,y); }

	public static PaintCommand poly( String command, double[] x, double[] y ){
		PaintCommand json = new PaintCommand(command);
		json.set(X, x);
		json.set(Y, y);
		return json;
	}
	
	public static PaintCommand quadTo( double cp1x, double cp1y, double x, double y )
	{ return poly(QUADTO, new double[]{cp1x,x}, new double[]{cp1y,y});	}
	
	public static PaintCommand curveTo( double cp1x, double cp1y, double cp2x, double cp2y, double x, double y )
	{ return poly(CURVETO, new double[]{cp1x,cp2x,x}, new double[]{cp1y,cp2y,y});	}
	
	public static PaintCommand line( double start_x, double start_y, double end_x, double end_y )
	{ return poly( LINE, new double[]{ start_x, end_x }, new double[]{ start_y, end_y }); }
	
	public static PaintCommand polygon( double[] x, double[] y ){ return poly(POLYGON, x, y); }

	public static PaintCommand polyline( double[] x, double[] y ){ return poly(POLYLINE, x, y); }

	public static PaintCommand rect( double x, double y, double width, double height )
	{ return polyline( new double[]{x, x+width, x+width, x, x}, new double[]{y,y,y+height,y+height, y} ); }

	public static PaintCommand image( double x, double y, double width, double height, int rot, boolean reflex, String url ){
		PaintCommand img = rect( x, y, width, height );
		img.set(COMMAND, IMAGE);
		img.set(IMAGE_ROT, rot);
		img.set(IMAGE_REF, reflex);
		img.set(IMAGE_URL, url);
		return img;
	}

	public static PaintCommand text( double x, double y, String str ){
		PaintCommand json = point(TEXT, x, y);
		json.set(MESSAGE, str);
		return json;
	} 	
	
	public static PaintCommand beginPath(){ return new PaintCommand(BEGIN); }
	public static PaintCommand closePath(){ return new PaintCommand(CLOSE); }
	public static PaintCommand stroke(){ return new PaintCommand(STROKE); }
	public static PaintCommand fill(){ return new PaintCommand(FILL); }
	
	protected static PaintCommand colorStyle( String STYLE, Color color ){
		PaintCommand c = new PaintCommand(STYLE);
		c.set(Color.TAG, color.json());
		return c;
	} 
	
	protected static PaintCommand linearGradientStyle( String STYLE, double x1, double y1, double x2, double y2, Color startcolor, Color endcolor ){
		PaintCommand c = poly(STYLE, new double[]{x1,x2}, new double[]{y1,y2});
		c.set(STARTCOLOR, startcolor.json());
		c.set(ENDCOLOR, endcolor.json());
		return c;
	} 

	protected static PaintCommand radialGradientStyle( String STYLE, double x, double y, double r, Color startcolor, Color endcolor ){ 
		PaintCommand c = linearGradientStyle(STYLE, x, y, x, y, startcolor, endcolor);
		c.set(X, (Double)x);
		c.set(Y, (Double)y);
		c.set(R, (Double)r);
		return c;
	} 
	
	public static PaintCommand strokeStyle( Color color ){ return colorStyle(STROKESTYLE, color); }
	
	public static PaintCommand strokeStyle( Color color, int lineWidth ){
		PaintCommand c = strokeStyle(color); 
		c.set(LINEWIDTH, lineWidth);
		return c;
	}
	
	public static PaintCommand strokeStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor )
	{ return linearGradientStyle(STROKESTYLE, x1, y1, x2, y2, startcolor, endcolor); } 

	public static PaintCommand strokeStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor, int lineWidth ){
		PaintCommand c = strokeStyle(x1, y1, x2, y2, startcolor, endcolor);
		c.set(LINEWIDTH, lineWidth);
		return c;
	} 
	
	public static PaintCommand strokeStyle( double x, double y, double r, Color startcolor, Color endcolor )
	{ return radialGradientStyle(STROKESTYLE, x, y, r, startcolor, endcolor); } 

	public static PaintCommand strokeStyle( double x, double y, double r, Color startcolor, Color endcolor, int lineWidth ){
		PaintCommand c = strokeStyle(x, y, r, startcolor, endcolor);
		c.set(LINEWIDTH, lineWidth);
		return c;
	} 
	
	public static PaintCommand fillStyle( Color color ){ return colorStyle(FILLSTYLE, color); }
	
	public static PaintCommand fillStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor )
	{ return linearGradientStyle(FILLSTYLE, x1, y1, x2, y2, startcolor, endcolor); } 

	public static PaintCommand fillStyle( double x, double y, double r, Color startcolor, Color endcolor )
	{ return radialGradientStyle(FILLSTYLE, x, y, r, startcolor, endcolor); } 
	
/*	
	public JSON jsonRect( int x, int y, int width, int height ){ return json( RECT, x, y, width, height ); }
	
	public JSON jsonFillRect( int x, int y, int width, int height ){ return json( FILLRECT, x, y, width, height ); }
	
	public JSON jsonOval( int x, int y, int width, int height ){ return json( OVAL, x, y, width, height ); }
	
	public JSON jsonFillOval( int x, int y, int width, int height ){ return json( FILLOVAL, x, y, width, height); }
	
	public JSON jsonArc(int x, int y, int width, int height, int startAngle, int endAngle){
		JSON json = json(ARC, x, y, width, height );
		json.set(START_ANGLE, startAngle);
		json.set(END_ANGLE, endAngle);
		return json;
	} 
	
	public JSON jsonFillArc(int x, int y, int width, int height, int startAngle, int endAngle){
		JSON json = json(FILLARC, x, y, width, height );
		json.set(START_ANGLE, startAngle);
		json.set(END_ANGLE, endAngle);
		return json;
	}

*/	
}