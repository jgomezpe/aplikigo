package nsgl.app;

import nsgl.generic.Named;

public interface Application extends Named{
	default Object run( String object, String method, Object... args ) throws Exception{ 
		if( id().equals(object) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);		    
		}else return get(object).run(method, args); 
	}
 
	Component get( String id );
/*
    protected FileResource files = new FileResource();

	public void addFileLoader( String id, FileLoader fl) { files.add(id,fl); }
	
	public boolean addComponent( Component c ) { return set(c.id(), c); }
	
	public Object addPackage( Package p ) {
		Header h = p.header();
		Component c = get(h.target());
		if( c!=null ){ return c.addPackage(p); }
		return null;
	} 
*/	
}
