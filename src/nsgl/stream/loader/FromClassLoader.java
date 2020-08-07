package nsgl.stream.loader;

import java.io.IOException;
import java.io.InputStream;

import nsgl.stream.Loader;

public class FromClassLoader implements Loader{
	protected ClassLoader loader = FromClassLoader.class.getClassLoader();
	
	public FromClassLoader( ClassLoader loader ) { this.loader = loader; }
	
	@Override
	public InputStream get(String name) throws IOException { return loader.getResourceAsStream(name); }
}