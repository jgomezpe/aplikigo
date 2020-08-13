package nsgl.gui;

import nsgl.app.Component;
import nsgl.json.JSON;

public interface Canvas extends Component{
    	static final String TAG = "canvas";
	void draw( JSON c );
	void config( JSON c );
	
	/**
	
	public abstract void drawArc(int x, int y, int width, int height, int startAngle, int endAngle); 

	public abstract void drawFillArc(int x, int y, int width, int height, int startAngle, int endAngle );

	public void drawOval( int x, int y, int width, int height ){ drawArc( x, y, width, height, 0, 360); }
	
	public void drawFillOval( int x, int y, int width, int height ){ drawFillArc( x, y, width, height, 0, 360); }
	*/	
}