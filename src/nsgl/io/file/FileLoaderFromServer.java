package nsgl.io.file;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class FileLoaderFromServer implements FileLoader{
	protected String server_url;
	
	public FileLoaderFromServer( String server_url ) { this.server_url = server_url; }

	@Override
	public InputStream get(String name) throws IOException {
		return new URL(server_url+name).openStream();
	}	
}