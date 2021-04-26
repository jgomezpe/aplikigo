package aplikigo.stream;

import java.io.IOException;
import java.io.InputStream;

public interface InputStreamLoader {
	public InputStream get(String name)  throws IOException ;
}
