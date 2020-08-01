package nsgl.gui;

import nsgl.generic.Collection;
import nsgl.generic.collection.Growable;
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
		for( Drawable d:c ) d.draw(getCanvas()); 
	}
	
	default void render( Object obj ){ render((Drawable)obj); }
}