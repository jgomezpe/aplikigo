package aplikigo.gui.canvas;

import speco.jxon.JXON;

/**
 * Extracts information from a JXON representing a Canvas draw command
 */
public interface DrawProcessor {
	/**
	 * Gets the canvas command TAG
	 * @param c JXON canvas command information 
	 * @return Canvas command TAG
	 */
	default String type( JXON c ) { return c.string(CanvasConstants.COMMAND); }
	
	/**
	 * Gets a real value associated to a tag
	 * @param c JXON canvas command information 
	 * @param TAG Tag to analyze
	 * @return Real value associated to the TAG
	 */
	default double real( JXON c, String TAG ) { return c.real(TAG); }
	
	/**
	 * Gets an array associated to a tag
	 * @param c JXON canvas command information 
	 * @param TAG Tag to analyze
	 * @return Array associated to the TAG
	 */	
	default double[] array( JXON c, String TAG ) { return c.reals_array(TAG);  }
	
	/**
	 * Gets the x coordinate of the command
	 * @param c JXON canvas command information 
	 * @return x coordinate of the command
	 */		
	default double x( JXON c ) { return real(c, CanvasConstants.X); }
	
	/**
	 * Gets the y coordinate of the command
	 * @param c JXON canvas command information 
	 * @return y coordinate of the command
	 */			
	default double y( JXON c ) { return real(c, CanvasConstants.Y); }
	
	/**
	 * Gets the array of x coordinates of the command
	 * @param c JXON canvas command information 
	 * @return array with the x coordinates of the command
	 */		
	default double[] X( JXON c ) { return array(c, CanvasConstants.X); }

	/**
	 * Gets the array of y coordinates of the command
	 * @param c JXON canvas command information 
	 * @return array with the y coordinates of the command
	 */		
	default double[] Y( JXON c ) { return array(c, CanvasConstants.Y); }
	
	/**
	 * Gets the collection of canvas commands composing a multi-command
	 * @param c JXON canvas command information 
	 * @return Collection of canvas commands composing a multi-command
	 */
	default Object[] commands(JXON c) { return c.array(CanvasConstants.COMMANDS); }

	/**
	 * Sets the x coordinate of the command
	 * @param c JXON canvas command information 
	 * @param x coordinate of the command
	 */		
	default void x( JXON c, double x ) { c.set(CanvasConstants.X, x); }
		
	/**
	 * Sets the y coordinate of the command
	 * @param c JXON canvas command information 
	 * @param y coordinate of the command
	 */		
	default void y( JXON c, double y ) { c.set(CanvasConstants.Y, y);  }
	
	/**
	 * Sets the x coordinates of the command
	 * @param c JXON canvas command information 
	 * @param x coordinates of the command
	 */		
	default void X( JXON c, double[] x ) { c.set(CanvasConstants.X, x); }

	/**
	 * Sets the y coordinate of the command
	 * @param c JXON canvas command information 
	 * @param y coordinates of the command
	 */		
	default void Y( JXON c, double[] y ) { c.set(CanvasConstants.Y, y); }
	
	/**
	 * Sets the set of commands for a multi-command
	 * @param c JXON canvas multi-command information 
	 * @param obj Set of commands
	 */			
	default void commands(JXON c, Object[] obj) { c.set(CanvasConstants.COMMANDS, obj); }
	
	/**
	 * Translates the draw ( JXON command representing it) 
	 * @param command Draw to translate
	 * @param dx Delta of movement in the x coordinate
	 * @param dy Delta of movement in the y coordinate
	 * @return Draw (JXON command  representing it) after translating it
	 */
	default JXON translate( JXON command, double dx, double dy ){
		Object[] commands = commands(command);
		if( commands!= null ){
			for(int i=0; i<commands.length; i++ ) commands[i] = translate((JXON)commands[i],dx, dy);
		}
		if( command.get(CanvasConstants.X)==null ) return command;
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
	
	/**
	 * Computes the angle defined by two points
	 * @param x1 x coordinate of the 'center'
	 * @param y1 y coordinate of the 'center'
	 * @param x2 x coordinate of the point in the 'circumference'
	 * @param y2 y coordinate of the point in the 'circumference'
	 * @return Angle defined by two points
	 */
	default double angle( double x1, double y1, double x2, double y2 ){
		double a = (x2-x1); 
		double b = (y2-y1);
		double r = Math.sqrt(a*a+b*b);
		if( r>1e-6 ){
			double alpha = Math.acos(a/r);
			if( b<0 ) alpha = 2.0*Math.PI - alpha;
			return alpha;
		}else return 0.0;
	}
	
	/**
	 * Rotates a point around a center point the given angle
	 * @param cx x coordinate of the 'center'
	 * @param cy y coordinate of the 'center'
	 * @param px x coordinate of the point to rotate
	 * @param py y coordinate of the point to rotate
	 * @param angle Rotation's angle
	 * @return Rotated point coordinates [x,y]
	 */
	default double[] rotate( double cx, double cy, double px, double py, double angle ){
		double alpha = angle( cx, cy, px, py ) + angle;
		if( alpha>1e-6 ){
			double a = (px-cx); 
			double b = (py-cy);
			double r = Math.sqrt(a*a+b*b);
			return new double[]{ cx + r*Math.cos(alpha), cy + r*Math.sin(alpha) };
		}else return new double[]{px,py};			
	}
	
	/**
	 * Rotates a draw (represented by its JXON configuration information), around a center point the given angle
	 * @param command Draw to rotate
	 * @param cx x coordinate of the 'center'
	 * @param cy y coordinate of the 'center'
	 * @param angle Rotation's angle
	 * @return Rotated draw (represented by its JXON configuration information)
	 */
	default JXON rotate( JXON command, double cx, double cy, double angle ){
		Object[] commands = commands(command);
		if( commands != null ){
			for(int i=0; i<commands.length; i++ ) commands[i] = rotate((JXON)commands[i],cx,cy,angle);
		}
		if( type(command).equals(CanvasConstants.IMAGE) ) {
			command = (JXON)command.copy();
			command.set(CanvasConstants.R, command.real(CanvasConstants.R) + angle);
			return command;
		}
		if( command.get(CanvasConstants.X)==null ) return command;
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
	
	/**
	 * Scales an array of values 
	 * @param value Values to scale
	 * @param s Scale factor
	 * @return Scaled values
	 */
	default double[] scale( double[] value, double s ){
		if( value == null ) return null;
		double[] svalue = new double[value.length];
		for( int i=0; i<svalue.length; i++ ) svalue[i] = value[i] * s;
		return svalue;
	}	
	
	/**
	 * Scales a draw (represented by its JXON configuration information) 
	 * @param command Draw to scale
	 * @param sx Scaling factor in the x coordinate
	 * @param sy Scaling factor in the y coordinate
	 * @return Scaled draw (represented by its JXON configuration information)
	 */
	default JXON scale( JXON command, double sx, double sy ) {
		Object[] commands = commands(command);
		if( commands != null ){
			for(int i=0; i<commands.length; i++ ) commands[i] = scale((JXON)commands[i],sx,sy);
		}
		if( command.get(CanvasConstants.X)==null ) return command;
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
}