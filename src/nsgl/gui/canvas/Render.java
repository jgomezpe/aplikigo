package nsgl.gui.canvas;

import nsgl.gui.Canvas;
import nsgl.gui.Drawable;
import nsgl.json.JSON;

public interface Render extends nsgl.gui.Render{
	void setCanvas( Canvas canvas );	
	Canvas getCanvas();
	
	default void render( Drawable obj ){ if( obj!=null && getCanvas()!=null ) getCanvas().draw(obj.draw()); }
	
	default void render( Object obj ){ render((Drawable)obj); }
	
	@Override
	default void config( JSON json ) {
	    if( json != null && getCanvas()!=null ) getCanvas().config(json.getJSON(Canvas.TAG));
	}
}