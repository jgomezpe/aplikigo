package nsgl.app;

import nsgl.communication.Header;
import nsgl.communication.Package;
import nsgl.generic.hashmap.HashMap;
import nsgl.io.file.FileLoader;
import nsgl.io.file.FileResource;

public class Application extends HashMap<String,Component> {
	protected FileResource files = new FileResource();

	public void addFileLoader( String id, FileLoader fl) { files.add(id,fl); }
	
	public boolean addComponent( Component c ) { return set(c.id(), c); }
	
	public Object addPackage( Package p ) {
		Header h = p.header();
		Component c = get(h.target());
		if( c!=null ){ return c.addPackage(p); }
		return null;
	} 
}
