package aplikigo.stream;
import java.awt.Image;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.HashMap;

import javax.imageio.ImageIO;

import aplikigo.stream.loader.FromOS;
import jxon.JXON;

public class Resource {
	// @TODO check Command, AWTCanvas, I18N, WebDictionaryController, DynWebServer
	protected HashMap<String, InputStreamLoader> loader = new HashMap<String, InputStreamLoader>();
	
	public void add( String name, InputStreamLoader loader ) { this.loader.put(name,loader); }
	public void del( String name ) { this.loader.remove(name); }

	public InputStream get(String loader, String file)  throws IOException{
			InputStreamLoader fl = this.loader.get(loader);
			if( fl!=null ) return fl.get(file);
			throw new IOException("·File not found· "+file);
	}
	
	public InputStream get(String file)  throws IOException{
		if( file.startsWith("http://") || file.startsWith("https://") ) try{ return new URL(file).openStream(); }catch(IOException ex){}
		for( String fl : loader.keySet() ) try{ return get( fl, file ); }catch(IOException ex){}				
		throw new IOException("·File not found· "+file);
	}
	
	public Image image(String loader, String name) throws IOException{ return ImageIO.read(get(loader, name)); }

	public Image image(String name) throws IOException{ return ImageIO.read( get(name) ); }
	
	public String txt( InputStream is ) throws IOException{ return new String(Util.toByteArray(is)); }
	
	public String txt(String loader, String name ) throws IOException{ return txt( get(loader,name) );	}
	public String txt( String name ) throws IOException{ return txt(get(name)); }	
	
	public static void main(String[] args) {
	    try {
		System.out.println(System.currentTimeMillis());
		Resource resource = new Resource();
		resource.add("local", new FromOS("/Users/jgomez/demo/clients/"));
		JXON json = JXON.parse(resource.txt("admin.json"));
		System.out.println(System.currentTimeMillis());
		System.out.println(json.stringify());
			    }catch(Exception e) {e.printStackTrace();}
	}
	
}