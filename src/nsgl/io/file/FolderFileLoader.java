package nsgl.io.file;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

public class FolderFileLoader implements FileLoader{
	protected String path;
	public FolderFileLoader( String path ) { this.path = path; }
	@Override
	public InputStream get(String name) throws IOException { return new FileInputStream(path+name); }
}