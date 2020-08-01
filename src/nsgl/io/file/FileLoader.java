package nsgl.io.file;

import java.io.IOException;
import java.io.InputStream;

public interface FileLoader {
	public InputStream get(String name)  throws IOException ;
}
