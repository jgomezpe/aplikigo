package aplikigo.remote;

import aplikigo.server.JXONBaseServer;
import speco.object.Named;

public class Component extends Named implements aplikigo.Component{
	protected JXONBaseServer server;

	public Component(String id) { this(id, null); }
	
	public Component(String id, JXONBaseServer server) {
		super(id);
		this.server = server;
	}
	
	public void setServer( JXONBaseServer server ) { this.server = server; }
	
	public Object run( String component, String method, Object... args ) throws Exception{
	    return server.add(server.command(component, method, args));
	}

	@Override
	public aplikigo.Component get(String component) { return this; }
}