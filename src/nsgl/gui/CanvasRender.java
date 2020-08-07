package nsgl.gui;

import nsgl.generic.Collection;
import nsgl.generic.collection.Growable;
import nsgl.gui.paint.Drawable;
import nsgl.json.JXON;
import nsgl.generic.Cleanable;

public interface CanvasRender extends nsgl.gui.Render{

	public Collection<Drawable> objects();
	
	public void setCanvas( Canvas canvas );
	
	public Canvas getCanvas();
	
	default void init(){ ((Cleanable)objects()).clear(); }
	
	default void add( Drawable obj ){ ((Growable<Drawable>)objects()).add(obj); }

	@Override
	default void add(Object obj){ add((Drawable)obj); }
	
	default void render(){
		Collection<Drawable> c = objects();
		for( Drawable d:c ) getCanvas().draw(d.draw()); 
	}
	
	default void render( Object obj ){ render((Drawable)obj); }
	
	@Override
	default void config( JXON json ) {
	    if( json != null && getCanvas()!=null ) getCanvas().config(json.getJXON(Canvas.TAG));
	}
}