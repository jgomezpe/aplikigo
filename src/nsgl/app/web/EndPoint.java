package nsgl.app.web;

import java.io.IOException;
import java.io.InputStream;
//import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.Application;
import nsgl.app.net.Channel;
import nsgl.generic.array.Vector;
import nsgl.generic.list.Queue;
import nsgl.json.JSON;
import nsgl.stream.Util;

public class EndPoint extends HttpServlet implements Channel{
	private static final long serialVersionUID = 1914490939385194698L;
	    
	protected Queue<JSON> queue = new Queue<JSON>();
	protected JSON user;
	
   	/**
   	 * Do not forget to override the init method or define a constructor method and
   	 * instantiate a specific server object 
   	 */
	protected Application server = null;
	
	public EndPoint() {}
	
	public EndPoint(Application server) { this.server = server; }
	
	@Override
	public Object send(JSON pack) { return queue.add(pack); }

	@Override
	public Object receive(JSON pack) throws Exception{ return server.run(user, pack); }
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    Object[] coms = null;
	    JSON pack;
	    if( server != null ) {
		    InputStream is = request.getInputStream();
        	    byte[] buffer = Util.toByteArray(is);
        	    pack = Channel.pack(buffer);
        	    coms = Channel.commands(pack);
        	    user = Channel.credential(pack);
        	    Vector<JSON> commands = new Vector<JSON>();
    		    for( int i=0; i<coms.length; i++ ) {
    			JSON c = (JSON)coms[i];
    			try {
    	        	    Object obj = receive(c);
    	        	    if( Channel.storable(obj) ) c.set(Channel.ARGS, new Object[] {obj});
    	        	    else c.set(Channel.ARGS, new Object[] {"·Cannot send answer·"});
    			} catch (Exception e) { c.set(Channel.ARGS, new Object[] {e.getMessage()}); }
    			commands.add(c);
    		    }
    		    while( !queue.isEmpty() ) commands.add(queue.peek());
        		
        	    coms = new Object[commands.size()];
        	    for( int i=0; i<coms.length; i++ ) coms[i] = commands.get(i);
	    }else {
		coms = new Object[1];
		coms[0] = Channel.command("console","log","·unrecognized command·");
	    }
	    pack = Channel.pack(coms);

	    response.getWriter().write(pack.stringify());
	    response.getWriter().flush();
//	    System.out.println("[EndPoint] " + new Date(System.currentTimeMillis()));
	}   
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ doPost(request, response); }	
}