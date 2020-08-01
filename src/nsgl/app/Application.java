package nsgl.app;

public interface Application {
    default Object run( String object, String method, Object... args ) throws Exception
    { return get(object).run(method, args); }
    
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
