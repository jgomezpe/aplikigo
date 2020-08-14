package nsgl.gui;

import nsgl.app.Component;
import nsgl.json.JSON;

public interface Render extends Component{
	static final String TAG = "render";

	void render( Object obj );

	void init();
	
	void config( JSON json );
}