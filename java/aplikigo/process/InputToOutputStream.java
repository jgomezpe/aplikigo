package aplikigo.process;

import java.io.InputStream;
import java.io.OutputStream;

public class InputToOutputStream implements Runnable{
    /**
     * InputStream
     */
    protected InputStream is;
    
    protected ProcessRunner process;

    /**
     * Thread used for reading the OutputStream while the External Process is running
     */
    protected Thread thread;

    /**
     * OutputStream associated to the OutputStream used by the External Process
     */
    protected OutputStream os = null;

    public InputToOutputStream(ProcessRunner process, InputStream is, OutputStream os) {
	this.is = is;
	this.os = os;
	this.process = process;
	start();
    }
    
    /**
     * Starts the OutputStream processing
     */
    public void start () {
	thread = new Thread(this);
	thread.start ();
    }

    /**
     * Process the OutputStream used by the External Process
     */
    public void run () {
	try {
	    if( is!=null) {
		while( process.isRunning() ) {
		    if( os != null ) {
			boolean flag = false;
			while(is.available()>0) {
			    os.write(is.read());
			    flag = true;
			}
			if(flag) os.flush();			
		    }else while(is.read()!=-1);
		}
		//is.close();
	    }
	}catch (Exception ex) { ex.printStackTrace (); }
    }
}