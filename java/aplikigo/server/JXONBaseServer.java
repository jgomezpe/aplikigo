package aplikigo.server;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import aplikigo.stream.Util;
import jxon.JXON;
import speco.array.Array;
import speco.object.Named;

public abstract class JXONBaseServer extends Named implements Server{
    protected Array<JXON> queue = new Array<JXON>();
    
    public JXONBaseServer( String id ) { super(id); }
    
    public boolean add(JXON command) { return queue.add(command); }
    
    public void run(InputStream is, OutputStream os) throws Exception{
	JXON response = run(JXON.parse(new String(Util.toByteArray(is))));
	int n = queue.size();
	if(queue.size()>0) {
	    Object[] args = new Object[n+1];
	    for( int i=0; i<n; i++ ) args[i] = queue.get(i);
	    for( int i=n-1; i>=0; i-- ) queue.remove(i);
	    args[n] = response;
	    response = command(id(),MULTI,args);
	} 
	PrintWriter writer = new PrintWriter(os,true);
	writer.write(response.stringify());
	writer.flush();
    }
 }