package aplikigo.loader;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import aplikigo.stream.InputStreamLoader;

public class FromURL implements InputStreamLoader{
	protected String server_url;
	
	public FromURL( String server_url ) { this.server_url = server_url; }

	@Override
	public InputStream get(String name) throws IOException {
		return new URL(server_url+name).openStream();
	}	
}