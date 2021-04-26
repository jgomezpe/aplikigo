package aplikigo.stream;

import java.io.IOException;
import java.io.InputStream;

import kerno.Service;

public interface Reader {
	/**
	 * Reads an object from the given stream
	 * @param is The input stream from which the object will be read
	 * @return An object, of the type the load service is attending, that is load from the input stream
	 * @throws IOException IOException
	 */
	Object read(InputStream is) throws IOException;

	static void set(Object owner, Reader reader) {
	    Service.set(owner, Reader.class, reader);
	}
	
	static Object read(Object obj, InputStream is)  throws IOException{
	    Reader r = (Reader)Service.get(obj, Reader.class);
	    if( r==null ) return null;
	    return r.read(is);
	}
}