package aplikigo.gui.canvas;

import aplikigo.gui.Canvas;
import jxon.Castable;
import jxon.JXON;

public interface Render extends aplikigo.gui.Render{
	void canvas( Canvas canvas );	
	Canvas canvas();
	
	default void render( JXON obj ){ if( obj!=null && canvas()!=null ) canvas().draw(obj); }
	
	default void render( Object obj ){
	    if( obj instanceof JXON ) render((JXON)obj);
	    else if( obj instanceof Castable ) render(((Castable)obj).jxon());
	}
	
	@Override
	default void config( JXON json ) {
	    if( json != null && canvas()!=null ) canvas().config(json);
	}
}