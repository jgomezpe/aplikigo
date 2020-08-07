package nsgl.gui.awt;

import java.awt.BasicStroke;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.LinearGradientPaint;
import java.awt.RadialGradientPaint;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;

import nsgl.generic.hashmap.HashMap;
import nsgl.gui.paint.Color;
import nsgl.gui.paint.Command;
import nsgl.json.JXON;
import nsgl.stream.Resource;
import nsgl.stream.loader.FromOS;

public class Canvas implements nsgl.gui.Canvas{
	
	protected Graphics2D g;

	protected double scale=1;

	protected HashMap<String, Integer> primitives = new HashMap<String,Integer>();
	protected HashMap<String, JXON> custom = new HashMap<String,JXON>();

	public Canvas(){
		primitives.set(Command.COMPOUND,0);
		primitives.set(Command.MOVETO,1);
		primitives.set(Command.LINETO,2);
		primitives.set(Command.QUADTO,3);
		primitives.set(Command.CURVETO,4);
		primitives.set(Command.TEXT,5);
		primitives.set(Command.IMAGE,6);
		primitives.set(Command.BEGIN,7);
		primitives.set(Command.CLOSE,8);
		primitives.set(Command.STROKE,9);
		primitives.set(Command.FILL,10);
		primitives.set(Command.STROKESTYLE,11);
		primitives.set(Command.FILLSTYLE,12);
		primitives.set(Command.LINE,13);
		primitives.set(Command.POLYLINE,14);
		primitives.set(Command.POLYGON,15);
		primitives.set(Command.TRANSLATE,16);
		primitives.set(Command.ROTATE,17);
		primitives.set(Command.SCALE,18);
	}
	
	public void setGraphics( Graphics g ){ this.g = (Graphics2D)g; }
	
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
	
	protected double real( JXON c, String TAG ) { return c.getReal(TAG); }
	protected double x( JXON c ) { return real(c, Command.X); }
	protected double y( JXON c ) { return real(c, Command.Y); }
	protected double[] array( JXON c, String TAG ) { return c.getRealArray(TAG); }
	protected double[] X( JXON c ) { return array(c, Command.X); }
	protected double[] Y( JXON c ) { return array(c, Command.Y); }
	
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
		String str = c.getString(Command.MESSAGE);
		g.drawString(str, (int)x, (int)y); 
	}

	public void image(JXON c) {
		double[] x = X(c);
		double[] y = Y(c);
		double rot = c.getReal(Command.R);
		// boolean reflex = c.getBool(Command.IMAGE_REF);
		String image_path = c.getString(Command.URL);
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
		return new Color(c.getJXON(TAG)); 
	    }catch(Exception e) {
		return new Color(255,255,255,255);
	    }
	}
	
	public void strokeStyle(JXON c) {
		if( c.valid(Color.TAG) ) g.setColor(color2awt(color(c))); 
		if( c.valid(Command.LINEWIDTH) ) g.setStroke(new BasicStroke(c.getInt(Command.LINEWIDTH)));
		fillStyle(c);
	}

	public void fillStyle(JXON c) {
		if( c.valid(Color.TAG) ){
			g.setPaint(color2awt(color(c)));
		}else{
			java.awt.Color sc = color2awt(color(c, Command.STARTCOLOR));
			java.awt.Color ec = color2awt(color(c, Command.ENDCOLOR));
			if( c.valid(Command.R) ){
				double x = x(c);
				double y = y(c);
				double r = real(c, Command.R);
				g.setPaint(new RadialGradientPaint((float)x, (float)y, (float)r, new float[]{0.0f,1.0f}, new java.awt.Color[]{sc,ec}) );
			}else{
				double[] x = X(c);
				double[] y = Y(c);
				g.setPaint(new LinearGradientPaint((float)x[0], (float)y[0], (float)x[1], (float)y[1], new float[]{0.0f,1.0f}, new java.awt.Color[]{sc,ec}));
			}
		}
	}
	
	public void compound( JXON c ){
		Object[] commands = c.getArray(Command.COMMANDS);
		for( Object v : commands ){ command((JXON)v); }
	}
	
	public void addCustomCommand(String id, JXON command) {
	    custom.set(id, command);
	}
	
	@Override
	public void draw( JXON c ) { command(init(new JXON(c))); }
	
	protected JXON init( JXON c ) {
	    JXON cc = custom.get(Command.type(c));  
	    if( cc != null ) {
		c = new JXON(cc);
		c.set(Command.COMMAND, Command.COMPOUND);
	    }
	    
	    if(c.get(Command.COMMANDS)!=null) {
		Object[] obj = c.getArray(Command.COMMANDS);
		for(int i=0; i<obj.length; i++)
		    obj[i] = init((JXON)obj[i]);
		c.set(Command.COMMANDS, obj);
	    }
	    return c;
	}
	
	protected void command( JXON c ){
		if( c==null ) return;
		String type = c.getString(Command.COMMAND);
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
				case 16: 
				    c = new JXON(c);
				    c.set(Command.COMMAND, Command.COMPOUND);
				    command(Command.translate(c, x(c), y(c))); 
				break; 
				case 17: 
				    c = new JXON(c);
				    c.set(Command.COMMAND, Command.COMPOUND);
				    command(Command.rotate(c, x(c), y(c), real(c,Command.R))); 
				break; 
				case 18: 
				    c = new JXON(c);
				    c.set(Command.COMMAND, Command.COMPOUND);
				    command(Command.scale(c, x(c))); 
				break; 
			}
		}catch(Exception e){}	
	}

	@Override
	public void config(JXON c) {
	    custom.clear();
	    Object[] commands = c.getArray(Command.COMMANDS);
	    for( int i=0; i<commands.length; i++) {
		JXON x = (JXON)commands[i];
		custom.set(Command.type(x), x);
	    }
	}
}