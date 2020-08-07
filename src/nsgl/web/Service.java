package nsgl.web;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.Application;
import nsgl.app.Command;
import nsgl.app.net.Channel;
import nsgl.app.net.Package;
import nsgl.generic.array.Vector;
import nsgl.generic.list.Queue;
import nsgl.json.JXON;
import nsgl.stream.Util;

public class Service extends EndPoint implements Channel{
	protected Queue<Command> queue = new Queue<Command>();
	protected Application service;
   	/**
   	 * 
   	 */
	private static final long serialVersionUID = 1914490939385194698L;
    
	public void setService(Application service) { this.service = service; }

	@Override
	public Object send(Command pack) { return queue.add(pack); }

	@Override
	public Object receive(Command pack) throws Exception{
		return service.run(pack.object(), pack.method(), pack.args());
	}
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    InputStream is = request.getInputStream();
	    byte[] buffer = Util.toByteArray(is);
	    Package pack = new Package(buffer);
	    System.out.println(pack.stringify());
	    Object[] coms = pack.getArray(Package.COMMAND);
	    Vector<Command> commands = new Vector<Command>();
	    for( int i=0; i<coms.length; i++ ) {
		Command c = new Command(((JXON)coms[i]).getString(Command.OBJECT), 
			((JXON)coms[i]).getString(Command.METHOD), ((JXON)coms[i]).getArray(Command.ARGS));
		try {
		    commands.add( new Command( c.object(), c.method(), receive(c) ) );
		} catch (Exception e) {
		    commands.add( new Command( c.object(), c.method(), e.getMessage() ) );
		}
	    }
	    while( !queue.isEmpty() ) commands.add(queue.peek());
	    coms = new Object[commands.size()];
	    for( int i=0; i<coms.length; i++ ) coms[i] = commands.get(i);
	    
	    pack = new Package(coms);
	    response.getWriter().write(pack.stringify());
	}   
}