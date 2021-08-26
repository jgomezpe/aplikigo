package aplikigo.gui.canvas;

import aplikigo.gui.Color;
import speco.jxon.JXON;

/**
 * Creates JXON information for a canvas command (drawing)
 */
public class DrawMaker {
	/**
	 * Creates a basic JXON configuration information for a canvas command (drawing)
	 * @param type Command tag
	 * @return Basic JXON configuration information for a canvas command (drawing)
	 */
	public static JXON create(String type) {
		JXON jxon = new JXON();
		jxon.set(CanvasConstants.COMMAND, type);
		return jxon;
	}
	
	/**
	 * Creates a JXON configuration information for point commands
	 * @param command Point command
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return JXON configuration information for point commands
	 */
	public static JXON point( String command, double x, double y ){
		JXON json = create(command);
		json.set(CanvasConstants.X, (Double)x);
		json.set(CanvasConstants.Y, (Double)y);
		return json;		
	}
	
	/**
	 * Creates a JXON configuration information for moveTo commands
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return JXON configuration information for moveTo commands
	 */
	public static JXON moveTo( double x, double y ){ return point(CanvasConstants.MOVETO,x,y); }
	
	/**
	 * Creates a JXON configuration information for lineTo commands
	 * @param x X coordinate
	 * @param y Y coordinate
	 * @return JXON configuration information for lineTo commands
	 */
	public static JXON lineTo( double x, double y ){ return point(CanvasConstants.LINETO,x,y); }

	/**
	 * Creates a JXON configuration information for multi-point commands
	 * @param command Multi-point command
	 * @param x X coordinates
	 * @param y Y coordinates
	 * @return JXON configuration information for multi-point commands
	 */
	public static JXON poly( String command, double[] x, double[] y ){
		JXON json = create(command);
		json.set(CanvasConstants.X, x);
		json.set(CanvasConstants.Y, y);
		return json;
	}
	
	/**
	 * Creates a JXON configuration information for quadratic commands
	 * @param cp1x X coordinated of the first control point 
	 * @param cp1y Y coordinated of the first control point 
	 * @param x X coordinated of the second control point 
	 * @param y Y coordinated of the second control point 
	 * @return JXON configuration information for quadratic commands
	 */
	public static JXON quadTo( double cp1x, double cp1y, double x, double y )
	{ return poly(CanvasConstants.QUADTO, new double[]{cp1x,x}, new double[]{cp1y,y});	}
	
	/**
	 * Creates a JXON configuration information for curve commands
	 * @param cp1x X coordinated of the first control point 
	 * @param cp1y Y coordinated of the first control point 
	 * @param cp2x X coordinated of the second control point 
	 * @param cp2y Y coordinated of the second control point 
	 * @param x X coordinated of the third control point 
	 * @param y Y coordinated of the third control point 
	 * @return JXON configuration information for curve commands
	 */
	public static JXON curveTo( double cp1x, double cp1y, double cp2x, double cp2y, double x, double y )
	{ return poly(CanvasConstants.CURVETO, new double[]{cp1x,cp2x,x}, new double[]{cp1y,cp2y,y});	}
	
	/**
	 * Creates a JXON configuration information for line commands
	 * @param start_x X coordinate of the line starting point 
	 * @param start_y Y coordinate of the line starting point 
	 * @param end_x X coordinate of the line ending point 
	 * @param end_y Y coordinate of the line ending point 
	 * @return JXON configuration information for line commands
	 */
	public static JXON line( double start_x, double start_y, double end_x, double end_y )
	{ return poly(CanvasConstants.LINE, new double[]{ start_x, end_x }, new double[]{ start_y, end_y }); }
	
	/**
	 * Creates a JXON configuration information for polygon commands
	 * @param x X coordinates of the polygon
	 * @param y Y coordinates of the polygon
	 * @return JXON configuration information for polygon commands
	 */
	public static JXON polygon( double[] x, double[] y ){ return poly(CanvasConstants.POLYGON, x, y); }

	/**
	 * Creates a JXON configuration information for polyline commands
	 * @param x X coordinates of the polyline
	 * @param y Y coordinates of the polyline
	 * @return JXON configuration information for polyline commands
	 */
	public static JXON polyline( double[] x, double[] y ){ return poly(CanvasConstants.POLYLINE, x, y); }

	/**
	 * Creates a JXON configuration information for rectangle commands
	 * @param x X coordinate of the rectangle
	 * @param y Y coordinates of the rectangle
	 * @param width Width of the rectangle
	 * @param height Height of the rectangle
	 * @return JXON configuration information for rectangle commands
	 */
	public static JXON rect( double x, double y, double width, double height )
	{ return polyline( new double[]{x, x+width, x+width, x, x}, new double[]{y,y,y+height,y+height, y} ); }

	/**
	 * Creates a JXON configuration information for image commands
	 * @param x X coordinate of the image
	 * @param y Y coordinates of the image
	 * @param width Width of the image
	 * @param height Height of the image
	 * @param rot Rotation of the image
	 * @param reflex If the image must be reflexed or not
	 * @param url Url of the image
	 * @return JXON configuration information for image commands
	 */
	public static JXON image( double x, double y, double width, double height, int rot, boolean reflex, String url ){
		JXON img = poly(CanvasConstants.IMAGE, new double[] {x, width}, new double[] {y,height} );
		img.set(CanvasConstants.R, rot);
		img.set(CanvasConstants.URL, url);
		return img;
	}

	/**
	 * Creates a JXON configuration information for text commands
	 * @param x X coordinate of the text
	 * @param y Y coordinates of the text
	 * @param str Text to draw
	 * @return JXON configuration information for text commands
	 */
	public static JXON text( double x, double y, String str ){
		JXON json = point(CanvasConstants.TEXT, x, y);
		json.set(CanvasConstants.MESSAGE, str);
		return json;
	} 	
	
	/**
	 * Creates a JXON configuration information for starting a path commands
	 * @return JXON configuration information for starting a path commands
	 */
	public static JXON beginPath(){ return create(CanvasConstants.BEGIN); }

	/**
	 * Creates a JXON configuration information for closing a path commands
	 * @return JXON configuration information for closing a path commands
	 */
	public static JXON closePath(){ return create(CanvasConstants.CLOSE); }

	/**
	 * Creates a JXON configuration information for stroke commands
	 * @return JXON configuration information for stroke commands
	 */
	public static JXON stroke(){ return create(CanvasConstants.STROKE); }
		
	/**
	 * Creates a JXON configuration information for fill commands
	 * @return JXON configuration information for fill commands
	 */
	public static JXON fill(){ return create(CanvasConstants.FILL); }
	
	/**
	 * Creates a JXON configuration information for color style commands
	 * @param STYLE Style command
	 * @param color Base color
	 * @return JXON configuration information for color style commands
	 */
	public static JXON colorStyle( String STYLE, Color color ){
		JXON json = create(STYLE);
		json.set(Color.TAG, color.jxon());
		return json;
	} 
	
	/**
	 * Creates a JXON configuration information for linear gradient style commands
	 * @param STYLE Style command
	 * @param x1 Starting x coordinate of the gradient style
	 * @param y1 Starting y coordinate of the gradient style
	 * @param x2 Ending x coordinate of the gradient style
	 * @param y2 Ending y coordinate of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @return JXON configuration information for linear gradient style commands
	 */
	public static JXON linearGradientStyle( String STYLE, double x1, double y1, double x2, double y2, Color startcolor, Color endcolor ){
		JXON c = poly(STYLE, new double[]{x1,x2}, new double[]{y1,y2});
		c.set(CanvasConstants.STARTCOLOR, startcolor.jxon());
		c.set(CanvasConstants.ENDCOLOR, endcolor.jxon());
		return c;
	} 

	/**
	 * Creates a JXON configuration information for radial gradient style commands
	 * @param STYLE Style command
	 * @param x Center's x coordinate of the gradient style
	 * @param y Center's y coordinate of the gradient style
	 * @param r Radius of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @return JXON configuration information for radial gradient style commands
	 */
	public static JXON radialGradientStyle( String STYLE, double x, double y, double r, Color startcolor, Color endcolor ){ 
		JXON c = linearGradientStyle(STYLE, x, y, x, y, startcolor, endcolor);
		c.set(CanvasConstants.X, (Double)x);
		c.set(CanvasConstants.Y, (Double)y);
		c.set(CanvasConstants.R, (Double)r);
		return c;
	} 
	
	/**
	 * Creates a JXON configuration information for stroke style commands
	 * @param color Stroke color
	 * @return JXON configuration information for stroke style commands
	 */
	public static JXON strokeStyle( Color color ){ return colorStyle(CanvasConstants.STROKESTYLE, color); }
	
	/**
	 * Creates a JXON configuration information for stroke style commands
	 * @param color Stroke color
	 * @param lineWidth Line width 
	 * @return JXON configuration information for stroke style commands
	 */
	public static JXON strokeStyle( Color color, int lineWidth ){
		JXON c = strokeStyle(color); 
		c.set(CanvasConstants.LINEWIDTH, lineWidth);
		return c;
	}
	
	/**
	 * Creates a JXON configuration information for stroke style commands
	 * @param x1 Starting x coordinate of the gradient style
	 * @param y1 Starting y coordinate of the gradient style
	 * @param x2 Ending x coordinate of the gradient style
	 * @param y2 Ending y coordinate of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @return JXON configuration information for stroke style commands
	 */
	public static JXON strokeStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor )
	{ return linearGradientStyle(CanvasConstants.STROKESTYLE, x1, y1, x2, y2, startcolor, endcolor); } 

	/**
	 * Creates a JXON configuration information for stroke style commands
	 * @param x1 Starting x coordinate of the gradient style
	 * @param y1 Starting y coordinate of the gradient style
	 * @param x2 Ending x coordinate of the gradient style
	 * @param y2 Ending y coordinate of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @param lineWidth Line width 
	 * @return JXON configuration information for stroke style commands
	 */
	public static JXON strokeStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor, int lineWidth ){
		JXON c = strokeStyle(x1, y1, x2, y2, startcolor, endcolor);
		c.set(CanvasConstants.LINEWIDTH, lineWidth);
		return c;
	} 
	
	/**
	 * Creates a JXON configuration information for stroke gradient style commands
	 * @param x Center's x coordinate of the gradient style
	 * @param y Center's y coordinate of the gradient style
	 * @param r Radius of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @return JXON configuration information for stroke gradient style commands
	 */
	public static JXON strokeStyle( double x, double y, double r, Color startcolor, Color endcolor )
	{ return radialGradientStyle(CanvasConstants.STROKESTYLE, x, y, r, startcolor, endcolor); } 

	/**
	 * Creates a JXON configuration information for stroke style commands
	 * @param x Center's x coordinate of the gradient style
	 * @param y Center's y coordinate of the gradient style
	 * @param r Radius of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @param lineWidth Line width 
	 * @return JXON configuration information for stroke style commands
	 */
	public static JXON strokeStyle( double x, double y, double r, Color startcolor, Color endcolor, int lineWidth ){
		JXON c = strokeStyle(x, y, r, startcolor, endcolor);
		c.set(CanvasConstants.LINEWIDTH, lineWidth);
		return c;
	} 
	
	/**
	 * Creates a JXON configuration information for fill style commands
	 * @param color Fill color
	 * @return JXON configuration information for fill style commands
	 */
	public static JXON fillStyle( Color color ){ return colorStyle(CanvasConstants.FILLSTYLE, color); }
	
	/**
	 * Creates a JXON configuration information for fill style commands
	 * @param x1 Starting x coordinate of the gradient style
	 * @param y1 Starting y coordinate of the gradient style
	 * @param x2 Ending x coordinate of the gradient style
	 * @param y2 Ending y coordinate of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @return JXON configuration information for fill style commands
	 */
	public static JXON fillStyle( double x1, double y1, double x2, double y2, Color startcolor, Color endcolor )
	{ return linearGradientStyle(CanvasConstants.FILLSTYLE, x1, y1, x2, y2, startcolor, endcolor); } 

	/**
	 * Creates a JXON configuration information for fill style commands
	 * @param x Center's x coordinate of the gradient style
	 * @param y Center's y coordinate of the gradient style
	 * @param r Radius of the gradient style
	 * @param startcolor Start color of the gradient style
	 * @param endcolor End color of the gradient style
	 * @return JXON configuration information for fill style commands
	 */
	public static JXON fillStyle( double x, double y, double r, Color startcolor, Color endcolor )
	{ return radialGradientStyle(CanvasConstants.FILLSTYLE, x, y, r, startcolor, endcolor); } 
	
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
