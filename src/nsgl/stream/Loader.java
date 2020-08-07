package nsgl.stream;

import java.io.IOException;
import java.io.InputStream;

public interface Loader {
	public InputStream get(String name)  throws IOException ;
}
