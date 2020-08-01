package nsgl.io.file;

import java.io.IOException;
import java.io.InputStream;

public class FileLoaderFromClassLoader implements FileLoader{
	protected ClassLoader loader = FileLoaderFromClassLoader.class.getClassLoader();
	
	public FileLoaderFromClassLoader( ClassLoader loader ) { this.loader = loader; }
	
	@Override
	public InputStream get(String name) throws IOException { return loader.getResourceAsStream(name); }
}