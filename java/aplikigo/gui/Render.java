package aplikigo.gui;

import aplikigo.Component;
import jxon.Configurable;

public interface Render extends Component, Configurable{
	static final String TAG = "render";

	void render( Object obj );

	void init();	
}