package nsgl.stream;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;

public class Util {
	/** 
	 * Default charset used by NSGL
	 */
	public static final String CHARSET = "UTF-8";

	public static ByteArrayOutputStream toByteArrayOS( InputStream is ) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		if( is==null ) return os;
		int MAX = 1000000;
		int k=0;
		byte[] buffer = new byte[MAX];  
		do{
			k=is.read(buffer);
			if( k>0 ) os.write(buffer, 0, k);
		}while(k>0);
		os.flush();
		return os;
	}
	
	public static int write(InputStream is, OutputStream os) throws java.io.IOException {
		if( is==null ) return 0;
		int c=0;
		byte[] buffer = new byte[100000];
		int s;
		while( (s=is.read(buffer))>=0 ) {
			c += s;
			os.write(buffer, 0, s);
		}
		os.flush();
		return c;		
	}	

	public static byte[] toByteArray( InputStream is ) throws IOException{ return toByteArrayOS(is).toByteArray(); }
		
	public static String toString( InputStream is, String charsetName ) throws IOException{ return toByteArrayOS(is).toString(charsetName); }

	public static String toString( InputStream is ) throws IOException{ return toString(is,CHARSET); }
	
	public static InputStream toInputStream( String str, String charsetName ) { return new ByteArrayInputStream(str.getBytes(Charset.forName(charsetName))); }

	public static InputStream toInputStream( String str ) { return toInputStream(str,CHARSET); }

	public static InputStream toInputStream( byte[] blob ) { return new ByteArrayInputStream(blob); }
}