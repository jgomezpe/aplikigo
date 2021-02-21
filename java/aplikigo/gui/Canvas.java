package aplikigo.gui;

import aplikigo.Component;
import jxon.Configurable;
import jxon.JXON;

public interface Canvas extends Component, Configurable{
    	static final String TAG = "canvas";
	void draw( JXON c );
	
	/**
	
	public abstract void drawArc(int x, int y, int width, int height, int startAngle, int endAngle); 

	public abstract void drawFillArc(int x, int y, int width, int height, int startAngle, int endAngle );

	public void drawOval( int x, int y, int width, int height ){ drawArc( x, y, width, height, 0, 360); }
	
	public void drawFillOval( int x, int y, int width, int height ){ drawFillArc( x, y, width, height, 0, 360); }
	*/	
}