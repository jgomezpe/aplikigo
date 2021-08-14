package aplikigo.gui;

import aplikigo.Component;
import jxon.Configurable;
import jxon.JXON;

public interface Canvas extends Component, Configurable{
    	static final String TAG = "canvas";
	void draw( JXON c );
}