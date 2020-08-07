package nsgl.app;

public interface Component {
	default Object run( String method, Object... args ) throws Exception{
		Class<?>[] types = new Class<?>[args.length];
		for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
		return this.getClass().getMethod(method, types).invoke(this, args);
	}
    
	/*
	Component container();
	void setContainer( Component container );

	Application app();
	void setApp( Application app );

	default Object addPackage( Package p ) {
		Header h = p.header(); 
		if( h.contentType()==Header.TXT ) {
			try{
				JSON json_vals = new JSON("{ vals:" + p.contentAsString() + "}");
				Object[] vals = json_vals.getArray("vals");
				return run( h.method(), vals );
			}catch(Exception e) { return null; }
		}else return run( h.method(), p.content() );
	}
	
	*/
}