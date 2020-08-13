package nsgl.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.Application;
import nsgl.app.Command;
import nsgl.app.net.Channel;
import nsgl.app.net.Package;
import nsgl.generic.array.Vector;
import nsgl.generic.list.Queue;
import nsgl.json.JSON;
import nsgl.stream.Util;

public class EndPoint extends HttpServlet implements Channel{
	protected Queue<Command> queue = new Queue<Command>();
	protected Application server;
   	/**
   	 * 
   	 */
	private static final long serialVersionUID = 1914490939385194698L;
    
	public void set(Application server) { this.server = server; }

	@Override
	public Object send(Command pack) { return queue.add(pack); }

	@Override
	public Object receive(Command pack) throws Exception{
		return server.run(pack.object(), pack.method(), pack.args());
	}
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    InputStream is = request.getInputStream();
	    byte[] buffer = Util.toByteArray(is);
	    Package pack = new Package(buffer);
	    Object[] coms = pack.commands();
	    Vector<Command> commands = new Vector<Command>();
	    if( server.authorized(pack.credential()) ) {
		    for( int i=0; i<coms.length; i++ ) {
			Command c = new Command((JSON)coms[i]);
			try {
			    commands.add( new Command( c.object(), c.method(), receive(c) ) );
			} catch (Exception e) {
			    commands.add( new Command( c.object(), c.method(), e.getMessage() ) );
			}
		    }
		    while( !queue.isEmpty() ) commands.add(queue.peek());
		
	    }else {
		commands.add( new Command( "user", "unauthorized" ) );
	    }
	    
	    coms = new Object[commands.size()];
	    for( int i=0; i<coms.length; i++ ) coms[i] = commands.get(i);
	    pack = new Package(coms);
	    System.out.println("EndPoint...."+pack.stringify());
	    response.getWriter().write(pack.stringify());
	}   
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ doPost(request, response); }	
}