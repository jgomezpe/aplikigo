package aplikigo.gui.awt;

import java.awt.BasicStroke;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.util.HashMap;

import aplikigo.gui.Color;
import aplikigo.gui.canvas.Util;
import aplikigo.stream.Resource;
import aplikigo.stream.loader.FromOS;
import jxon.JXON;

public class Canvas implements aplikigo.gui.Canvas{
	
	protected Graphics2D g;
	protected CanvasRender render;

	protected double scale=1;

	protected HashMap<String, Integer> primitives = new HashMap<String,Integer>();
	protected HashMap<String, JXON> custom = new HashMap<String,JXON>();

	public Canvas( CanvasRender render ){
		this.render = render;
		primitives.put(Util.COMPOUND,0);
		primitives.put(Util.MOVETO,1);
		primitives.put(Util.LINETO,2);
		primitives.put(Util.QUADTO,3);
		primitives.put(Util.CURVETO,4);
		primitives.put(Util.TEXT,5);
		primitives.put(Util.IMAGE,6);
		primitives.put(Util.BEGIN,7);
		primitives.put(Util.CLOSE,8);
		primitives.put(Util.STROKE,9);
		primitives.put(Util.FILL,10);
		primitives.put(Util.STROKESTYLE,11);
		primitives.put(Util.FILLSTYLE,12);
		primitives.put(Util.LINE,13);
		primitives.put(Util.POLYLINE,14);
		primitives.put(Util.POLYGON,15);
		primitives.put(Util.TRANSLATE,16);
		primitives.put(Util.ROTATE,17);
		primitives.put(Util.SCALE,18);
		primitives.put(Util.FIT,19);
	}
	
	public void setRender( CanvasRender render ) { this.render = render; }
	
	public void setGraphics( Graphics g ){ this.g = (Graphics2D)g; }
	
	public String type( JXON c ) { return c.string(Util.COMMAND); }
	public double real( JXON c, String TAG ) { return c.real(TAG); }
	public double[] array( JXON c, String TAG ) { return c.reals_array(TAG);  }
	public double x( JXON c ) { return real(c, Util.X); }
	public double y( JXON c ) { return real(c, Util.Y); }
	public double[] X( JXON c ) { return array(c, Util.X); }
	public double[] Y( JXON c ) { return array(c, Util.Y); }
	public Object[] commands(JXON c) { return c.array(Util.COMMANDS); }

	public void x( JXON c, double x ) { c.set(Util.X, x); }
	public void y( JXON c, double y ) { c.set(Util.Y, y);  }
	public void X( JXON c, double[] x ) { c.set(Util.X, x); }
	public void Y( JXON c, double[] y ) { c.set(Util.Y, y); }
	public void commands(JXON c, Object[] obj) { c.set(Util.COMMANDS, obj); }
	
	public JXON translate( JXON command, double dx, double dy ){
		Object[] commands = commands(command);
		if( commands!= null ){
			for(int i=0; i<commands.length; i++ ) commands[i] = translate((JXON)commands[i],dx, dy);
		}
		if( command.get(Util.X)==null ) return command;
		double[] x = X(command);
		if( x != null ){
			double[] y = Y(command);
			for( int i=0; i<x.length; i++ ){
				x[i] += dx;
				y[i] += dy;
			}
			X(command,x);
			Y(command,y);
		}else{
			x(command,dx+x(command));
			y(command,dy+y(command));
		}	
		return command;
	}
	
	public double angle( double x1, double y1, double x2, double y2 ){
		double a = (x2-x1); 
		double b = (y2-y1);
		double r = Math.sqrt(a*a+b*b);
		if( r>1e-6 ){
			double alpha = Math.acos(a/r);
			if( b<0 ) alpha = 2.0*Math.PI - alpha;
			return alpha;
		}else return 0.0;
	}
	
	public double[] rotate( double cx, double cy, double px, double py, double angle ){
		double alpha = angle( cx, cy, px, py ) + angle;
		if( alpha>1e-6 ){
			double a = (px-cx); 
			double b = (py-cy);
			double r = Math.sqrt(a*a+b*b);
			return new double[]{ cx + r*Math.cos(alpha), cy + r*Math.sin(alpha) };
		}else return new double[]{px,py};			
	}
	
	public JXON rotate( JXON command, double cx, double cy, double angle ){
		Object[] commands = commands(command);
		if( commands != null ){
			for(int i=0; i<commands.length; i++ ) commands[i] = rotate((JXON)commands[i],cx,cy,angle);
		}
		if( type(command).equals(Util.IMAGE) ) {
		    command = (JXON)command.copy();
		    command.set(Util.R, command.real(Util.R) + angle);
		    return command;
		}
		if( command.get(Util.X)==null ) return command;
		double[] x = X(command);
		if( x != null ){
			double[] y = Y(command);
			for( int i=0; i<x.length; i++ ){
				double[] p = rotate( cx, cy, x[i], y[i], angle );
				x[i] = p[0];
				y[i] = p[1];
			}	
			X(command,x);
			Y(command,y);
		}else{
			double[] p = rotate( cx, cy, x(command), y(command), angle);
			x(command,p[0]);
			y(command,p[1]);
		}
		return command;
	}
	
	public double[] scale( double[] value, double s ){
		if( value == null ) return null;
		double[] svalue = new double[value.length];
		for( int i=0; i<svalue.length; i++ ) svalue[i] = value[i] * s;
		return svalue;
	}	
	
	public JXON scale( JXON command, double sx, double sy ) {
		Object[] commands = commands(command);
		if( commands != null ){
			for(int i=0; i<commands.length; i++ ) commands[i] = scale((JXON)commands[i],sx,sy);
		}
		if( command.get(Util.X)==null ) return command;
		double[] x = X(command);
		if( x != null ){
			x = scale(x, sx);
			double[] y = scale(Y(command), sy);
			X(command,x);
			Y(command,y);
		}else{
			x(command,x(command)*sx);
			y(command,y(command)*sy);
		}
		return command;
	}
	
	/**
	 * Converts a given Image into a BufferedImage
	 *
	 * @param img The Image to be converted
	 * @return The converted BufferedImage
	 */
	public static BufferedImage toBufferedImage(Image img)
	{
	    if (img instanceof BufferedImage) return (BufferedImage) img;

	    // Create a buffered image with transparency
	    BufferedImage bimage = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);

	    // Draw the image on to the buffered image
	    Graphics2D bGr = bimage.createGraphics();
	    bGr.drawImage(img, 0, 0, null);
	    bGr.dispose();

	    // Return the buffered image
	    return bimage;
	}	
	
	public static Color awt2color( java.awt.Color color ){ return new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha()); } 
	public static java.awt.Color color2awt( Color color ){ return new java.awt.Color(color.red(), color.green(), color.blue(), color.alpha()); }

	GeneralPath path = new GeneralPath();
		
	public void moveTo(JXON c) {
		double x = x(c);
		double y = y(c);
		path.moveTo(x, y);
	}

	public void lineTo(JXON c) {
		double x = x(c);
		double y = y(c);
		path.lineTo(x, y);
	}

	public void line(JXON c){
		beginPath();
		double[] x = X(c);
		double[] y = Y(c);
		path.moveTo(x[0],y[0]);
		path.lineTo(x[0],y[0]);
		stroke();		
	}
	
	protected void poly(double[] x, double[] y) {
		beginPath();
		path.moveTo(x[0],y[0]);
		for( int i=1; i<x.length; i++) path.lineTo(x[i],y[i]);	    
	}

	protected void poly(JXON c){ poly(X(c),Y(c)); }
	
	public void polyline(JXON c){
		poly(c);
		stroke();
	}

	public void polygon(JXON c){
		poly(c);
		fill();
	}

	public void quadTo(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		path.quadTo(x[0], y[0], x[1], y[1]);
	}

	public void curveTo(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		path.curveTo(x[0], y[0], x[1], y[1], x[2], y[2]);
	}

	public void text(JXON c) {
		double x = x(c);
		double y = y(c);
		String str = c.string(Util.MESSAGE);
		g.drawString(str, (int)x, (int)y); 
	}

	public void image(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		double rot = c.real(Util.R);
		// boolean reflex = c.getBool(Command.IMAGE_REF);
		String image_path = c.string(Util.URL);
		Resource resource = new Resource();
		resource.add("local", new FromOS(""));
		try{
		    Image obj = resource.image(image_path);
		    Image img = obj.getScaledInstance((int)(x[1]-x[0]), (int)(y[1]-y[0]), Image.SCALE_SMOOTH);
		    int cx = (img.getWidth(null) / 2);
		    int cy = (img.getHeight(null) / 2);
		    AffineTransform tx = AffineTransform.getRotateInstance(rot, cx, cy);
		    AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_BILINEAR);

		    // Drawing the rotated image at the required drawing locations
		    g.drawImage(op.filter(toBufferedImage(img), null),(int)x[0], (int)y[0], null);		
		}catch(Exception e) {}
	}

	public void beginPath(){ path = new GeneralPath(); }

	public void closePath(){ path.closePath(); }

	public void fill(){
		path.closePath();
		g.fill(path); 
	}

	public void stroke(){ g.draw(path); }

	public Color color(JXON c) { return color(c, Color.TAG); }
	
	public Color color(JXON c, String TAG) { 
	    try{
		return new Color(c.object(TAG)); 
	    }catch(Exception e) {
		return new Color(255,255,255,255);
	    }
	}
	
	public void strokeStyle(JXON c) {
		if( c.valid(Color.TAG) ) g.setColor(color2awt(color(c))); 
		if( c.valid(Util.LINEWIDTH) ) g.setStroke(new BasicStroke(c.integer(Util.LINEWIDTH)));
		fillStyle(c);
	}

	public void fillStyle(JXON c) {
		if( c.valid(Color.TAG) ){
			g.setPaint(color2awt(color(c)));
		}else{
			java.awt.Color sc = color2awt(color(c, Util.STARTCOLOR));
			java.awt.Color ec = color2awt(color(c, Util.ENDCOLOR));
			if( c.valid(Util.R) ){
				double x = x(c);
				double y = y(c);
				double r = real(c, Util.R);
				g.setPaint(new RadialGradientPaint((float)x, (float)y, (float)r, new float[]{0.0f,1.0f}, new java.awt.Color[]{sc,ec}) );
			}else{
				double[] x = X(c);
				double[] y = Y(c);
				g.setPaint(new LinearGradientPaint((float)x[0], (float)y[0], (float)x[1], (float)y[1], new float[]{0.0f,1.0f}, new java.awt.Color[]{sc,ec}));
			}
		}
	}
	
	public void compound( JXON c ){
		Object[] commands = c.array(Util.COMMANDS);
		for( Object v : commands ){ command((JXON)v); }
	}
	
	public JXON translate( JXON c ) {
	    c = (JXON)c.copy();
	    c.set(Util.COMMAND, Util.COMPOUND);
	    return translate(c, x(c), y(c)); 	    
	}
	
	public JXON rotate( JXON c ) {
	    c = (JXON)c.copy();
	    c.set(Util.COMMAND, Util.COMPOUND);
	    return rotate(c, x(c), y(c), real(c,Util.R)); 	    
	}
	
	public JXON scale( JXON c ) {
	    c = (JXON)c.copy();
	    c.set(Util.COMMAND, Util.COMPOUND);
	    return scale(c, x(c), y(c)); 
	}
	
	public JXON fit( JXON c ) {
	    c = (JXON)c.copy();
	    c.set(Util.COMMAND, Util.COMPOUND);
	    double x = x(c);
	    double y = y(c);
	    boolean keepAspectRatio = c.bool(Util.R);
	    Dimension d = render.getSize();
	    double w = d.getWidth();
	    double h = d.getHeight();
	    if(keepAspectRatio) {
		double scale;
		if( w*x < h*y ) scale = w*x;
		else scale = h*y;
		x = scale;
		y = scale;
	    }else {
		x *= w;
		y *= h;
	    }
	    return scale(c,x,y);
	}
	
	

	public void addCustomCommand(String id, JXON command) {
	    custom.put(id, command);
	}
	
	@Override
	public void draw( JXON c ) { 
	    JXON json = init((JXON)c.copy());
	    command(json); 
	}
	
	protected JXON init( JXON c ) {
	    JXON cc = custom.get(type(c));  
	    if( cc != null ) {
		c = (JXON)cc.copy();
		c.set(Util.COMMAND, Util.COMPOUND);
	    }

	    if(c.get(Util.COMMANDS)!=null) {
		c = (JXON)c.copy();
		Object[] obj = c.array(Util.COMMANDS);
		for(int i=0; i<obj.length; i++)
		    obj[i] = init((JXON)obj[i]);
		c.set(Util.COMMANDS, obj);
	    }
	    int k = primitives.get(type(c));
	    switch(k) {
	    	case 16: return translate(c);
	    	case 17: return rotate(c);
	    	case 18: return scale(c);
	    	case 19: return fit(c);
	    }
	    return c;
	}
	
	protected void command( JXON c ){
		if( c==null ) return;
		String type = c.string(Util.COMMAND);
		if( type == null ) return;
		try{
		    Integer cId = primitives.get(type);
		    if( cId != null)
			switch(cId){
				case 0: compound(c); break; 
				case 1: moveTo(c); break; 
				case 2: lineTo(c); break; 
				case 3: quadTo(c); break; 
				case 4: curveTo(c); break; 
				case 5: text(c); break; 
				case 6: image(c); break; 
				case 7: beginPath(); break; 
				case 8: closePath(); break; 
				case 9: stroke(); break; 
				case 10: fill(); break; 
				case 11: strokeStyle(c); break; 
				case 12: fillStyle(c); break; 
				case 13: line(c); break; 
				case 14: polyline(c); break; 
				case 15: polygon(c); break; 
			}
		}catch(Exception e){}	
	}

	@Override
	public void config(JXON c) {
	    custom.clear();
	    Object[] commands = c.array(Util.COMMANDS);
	    for( int i=0; i<commands.length; i++) {
		JXON x = (JXON)commands[i];
		custom.put(type(x), x);
	    }
	}
}