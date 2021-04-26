package aplikigo.stream.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import aplikigo.stream.InputStreamLoader;

public class FromOS implements InputStreamLoader{
	protected String path;
	public FromOS( String path ) { this.path = path; }
	@Override
	public InputStream get(String name) throws IOException { return new FileInputStream(path+name); }
}