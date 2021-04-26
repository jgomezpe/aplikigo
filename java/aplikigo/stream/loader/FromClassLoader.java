package aplikigo.stream.loader;

import java.io.IOException;
import java.io.InputStream;

import aplikigo.stream.InputStreamLoader;

public class FromClassLoader implements InputStreamLoader{
	protected ClassLoader loader = FromClassLoader.class.getClassLoader();
	
	public FromClassLoader( ClassLoader loader ) { this.loader = loader; }
	
	@Override
	public InputStream get(String name) throws IOException { 
	    InputStream is = loader.getResourceAsStream(name); 
	    if( is==null ) throw new IOException("·No such resource·");
	    return is;
	}
}