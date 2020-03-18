package nsgl.js;

import java.io.InputStream;

import nsgl.app.DefaultComponent;
import nsgl.communication.Header;
import nsgl.communication.Package;
import nsgl.stringify.Stringifyable;
import nsgl.web.Servlet;

public class JSView extends DefaultComponent{
	protected Servlet servlet;
	
	public JSView(String id) {
		super(id);
		servlet = null;
	}
	
	public JSView(String id, Servlet servlet) {
		super(id);
		this.servlet = servlet;
	}
	
	public void setServlet( Servlet servlet ) { this.servlet = servlet; }
	
	protected Header header( String method ) {
		Header h = new Header();
		h.set(Header.TARGET, id());
		h.set(Header.METHOD, method);
		return h;
	}

	public Object run( String method, Object... args ) {
		Package p;
		Header h=header(method);
		if( args.length > 0 ){
			boolean[] type = new boolean[args.length];
			for( int i=0; i<args.length; i++ ) type[i] = (args[i] instanceof InputStream);		
			int start = 0;
			int end=0;
			do {
				for( ;end<args.length && !type[end]; end++ );
				if(start<end) { // There are simple arguments to send to client
					Object[] pargs = new Object[ end-start];
					System.arraycopy(args, start, pargs, 0, pargs.length);
					h = header(method);
					h.set(Header.ARGS, new Object[] {start, end});
					p = new Package(h, Stringifyable.cast(pargs).stringify());
					servlet.addPackage(p);
				}
				if( end<args.length ) { // There is a blob argument to send to client
					h = header(method);
					h.set(Header.ARGS, new Object[] {end, end+1});
					p = new Package(h, (InputStream)args[end]);
					servlet.addPackage(p);
					end++;
					start=end;
				}
			}while(end<args.length);
			
		}else {
			p = new Package(h,"");
			servlet.addPackage(p);
		}
		return null;
	}
}