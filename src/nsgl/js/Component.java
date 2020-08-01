package nsgl.js;

import nsgl.communication.Channel;
import nsgl.communication.Package;

public class Component extends nsgl.app.Component{
	protected Channel channel;
	
	public Component() {}
	
	public Component(String id,Channel channel) {
		super(id);
		this.channel = channel;
	}
	
	public void setClient( Channel channel ) { this.channel = channel; }
	
	public Object run( String method, Object... args ) throws Exception{
	    return channel.send(new Package(id(), method, args)); 
	}
}