package nsgl.app.net;

import nsgl.app.Command;

public interface Channel {
	Object send( Command pack ) throws Exception;
	Object receive( Command pack ) throws Exception;
}