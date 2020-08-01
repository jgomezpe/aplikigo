package nsgl.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.Application;
import nsgl.communication.Channel;
import nsgl.communication.Package;
import nsgl.generic.array.Vector;
import nsgl.generic.list.Queue;
import nsgl.io.IOUtil;
import nsgl.json.JSON;
import nsgl.stringify.Stringifier;

public class Service extends EndPoint implements Channel{
	protected Queue<Package> queue = new Queue<Package>();
	protected Application service;
   	/**
   	 * 
   	 */
	private static final long serialVersionUID = 1914490939385194698L;
    
	public void setService(Application service) { this.service = service; }

	@Override
	public Object send(Package pack) { return queue.add(pack); }

	@Override
	public Object receive(Package pack) throws Exception{
		return service.run(pack.object(), pack.method(), pack.args());
	}
    
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	    JSON meta = new JSON("{ \"meta\":" + request.getHeader(OPER) +"}");
	    Object[] counter = meta.getArray("meta");
	    InputStream is = request.getInputStream();
	    Vector<Package> pack = new Vector<Package>();
	    for( int i=0; i<counter.length; i++ ) {
		pack.add( new Package((int)counter[i], is) );
	    }
	    
	    Vector<byte[]> rmeta = new Vector<byte[]>();
	    for( int i=0; i<pack.size(); i++ ) {
		Object obj = null;
		Package p = pack.get(i);
		try{ receive( p ); }catch(Exception e){
		    JSON ex = new JSON();
		    ex.set("exception", e.getMessage());
		    obj = ex;
		}
		pack.set(i, new Package(p.object(), p.method(), obj));
		byte[] encode = new byte[0];
		try{ encode = pack.get(i).headerbyte(); }catch(IOException e) {}
		rmeta.add(encode);
	    }
	    while( !queue.isEmpty() ) {
		Package p = queue.peek();
		pack.add(p);
		byte[] encode = new byte[0];
		try{ encode = p.headerbyte(); }catch(IOException e) {}
		rmeta.add(encode);
	    }
	    
	    Integer[] length = new Integer[rmeta.size()];
	    for( int i=0; i<length.length; i++ ) length[i] = rmeta.get(i).length;
	    response.setHeader(CODE, Stringifier.apply(length));
	    
	    OutputStream os = response.getOutputStream();
	    for( int i=0; i<pack.size(); i++ ) {
		os.write(rmeta.get(i));
		IOUtil.write(pack.get(i).is(), os);
	    }
	}   
}