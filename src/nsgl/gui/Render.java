package nsgl.gui;

import nsgl.app.Component;
import nsgl.json.JXON;

public interface Render extends Component{
	static final String TAG = "render";

	void render();
	default void render( Object obj ){
		init();
		add( obj );
		render();
	}
	void add( Object obj );
	void init();
	
	void config( JXON json );
}