package nsgl.stream;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import nsgl.generic.hashmap.HashMap;

public class Resource {
	// @TODO check Command, AWTCanvas, I18N, WebDictionaryController, DynWebServer
	protected HashMap<String, Loader> loader = new HashMap<String, Loader>();
	
	public void add( String name, Loader loader ) { this.loader.set(name,loader); }
	public void del( String name ) { this.loader.remove(name); }

	public InputStream get(String loader, String file)  throws IOException{
			Loader fl = this.loader.get(loader);
			if( fl!=null ) return fl.get(file);
			throw new IOException("·File not found· "+file);
	}
	
	public InputStream get(String file)  throws IOException{
		if( file.startsWith("http://") || file.startsWith("https://") ) try{ return new URL(file).openStream(); }catch(IOException ex){}
		for( String fl : loader.keys() ) try{ return get( fl, file ); }catch(IOException ex){}				
		throw new IOException("·File not found· "+file);
	}
	
	public Image image(String loader, String name) throws IOException{ return ImageIO.read(get(loader, name)); }

	public Image image(String name) throws IOException{ return ImageIO.read( get(name) ); }
	
	public String txt( InputStream is ) throws IOException{ return new String(Util.toByteArray(is)); }
	
	public String txt(String loader, String name ) throws IOException{ return txt( get(loader,name) );	}
	public String txt( String name ) throws IOException{ return txt(get(name)); }	
}