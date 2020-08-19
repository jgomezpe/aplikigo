package nsgl.gui.canvas;

import nsgl.generic.JSONCastable;
import nsgl.gui.Canvas;
import nsgl.json.JSON;

public interface Render extends nsgl.gui.Render{
	void setCanvas( Canvas canvas );	
	Canvas getCanvas();
	
	default void render( JSON obj ){ if( obj!=null && getCanvas()!=null ) getCanvas().draw(obj); }
	
	default void render( Object obj ){
	    if( obj instanceof JSON ) render((JSON)obj);
	    else if( obj instanceof JSONCastable ) render(((JSONCastable)obj).json());
	}
	
	@Override
	default void config( JSON json ) {
	    if( json != null && getCanvas()!=null ) getCanvas().config(json.object(Canvas.TAG));
	}
}