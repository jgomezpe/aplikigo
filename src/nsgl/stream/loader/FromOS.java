package nsgl.stream.loader;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import nsgl.stream.Loader;

public class FromOS implements Loader{
	protected String path;
	public FromOS( String path ) { this.path = path; }
	@Override
	public InputStream get(String name) throws IOException { return new FileInputStream(path+name); }
}