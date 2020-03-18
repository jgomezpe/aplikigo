package nsgl.gui;

import nsgl.app.Component;

public interface Render extends Component{
	void render();
	default void render( Object obj ){
		init();
		add( obj );
		render();
	}
	void add( Object obj );
	void init();
}