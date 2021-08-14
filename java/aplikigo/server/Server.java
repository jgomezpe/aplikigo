package aplikigo.server;

import java.io.InputStream;
import java.io.OutputStream;

import aplikigo.Component;

public interface Server extends Component{
    void run(InputStream is, OutputStream os) throws Exception;
}
