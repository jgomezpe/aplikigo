package nsgl.io.file;
import java.awt.Image;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.imageio.ImageIO;

import nsgl.generic.hashmap.HashMap;

public class FileResource {
	// @TODO check Command, AWTCanvas, I18N, WebDictionaryController, DynWebServer
	protected HashMap<String, FileLoader> loader = new HashMap<String, FileLoader>();
	
	public void add( String name, FileLoader loader ) { this.loader.set(name,loader); }
	public void del( String name ) { this.loader.remove(name); }

	public byte[] toByteArray( InputStream is ) throws IOException{
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		int MAX = 1000000;
		int k=0;
		byte[] buffer = new byte[MAX];  
		do{
			k=is.read(buffer);
			if( k>0 ) os.write(buffer, 0, k);
		}while(k>0);
		return os.toByteArray();
	}
		
	public InputStream get(String loader, String file)  throws IOException{
			FileLoader fl = this.loader.get(loader);
			if( fl!=null ) return fl.get(file);
			throw new IOException("·File not found· "+file);
	}
	
	public InputStream get(String file)  throws IOException{
		if( file.startsWith("http://") || file.startsWith("https://") ) try{ return new URL(file).openStream(); }catch(IOException ex){}

		for( String fl : loader.keys() )
			try{ 				
				return get( fl, file ); 
			}catch(IOException ex){}				
		throw new IOException("·File not found· "+file);
	}
	
	public Image image(String loader, String name) throws IOException{ return ImageIO.read(get(loader, name)); }

	public Image image(String name) throws IOException{ return ImageIO.read( get(name) ); }
	
	public String txt_read( InputStream is ) throws IOException{
		StringBuilder sb = new StringBuilder();
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		String line = reader.readLine();
		while( line != null ){ 
			sb.append(line);
			line = reader.readLine();
			if( line != null ) sb.append('\n');
		}
		return sb.toString();
	}
	
	public String txt(String loader, String name ) throws IOException{ return txt_read( get(loader,name) );	}
	public String txt( String name ) throws IOException{ return txt_read(get(name)); }	
}