package nsgl.app;

import nsgl.generic.Named;
import nsgl.json.JSON;

public interface Application extends Named{
	default Object run( String object, String method, Object... args ) throws Exception{ 
		if( id().equals(object) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);		    
		}else return get(object).run(method, args); 
	}
 
	Component get( String id );
	
	default boolean authorized( JSON user ) { return true; }
}
