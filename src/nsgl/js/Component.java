package nsgl.js;

import nsgl.app.Command;
import nsgl.app.net.Channel;
import nsgl.generic.Named;

public class Component implements nsgl.app.Component, Named{
	protected Channel channel;
	protected String id;
	
	public Component(String id) { this.id = id; }
	
	public Component(String id,Channel channel) {
		this.id = id;
		this.channel = channel;
	}
	
	@Override 
	public void id(String id) { this.id = id; }
	
	@Override
	public String id() { return id; }
	
	public void setClient( Channel channel ) { this.channel = channel; }
	
	public Object run( String method, Object... args ) throws Exception{
	    return channel.send(new Command(id(), method, args)); 
	}
}