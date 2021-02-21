package aplikigo;

import aplikigo.net.Channel;
import jxon.JXON;
import speco.object.Identifiable;

public interface Application extends Identifiable{
	boolean accessible(String object, String method);
	
	default Object run( String object, String method, Object... args ) throws Exception{ 
		if( accessible(object, method) ) {
		    if( id().equals(object) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);		    
		    }else return get(object).run(method, args); 
		}
		throw new Exception("·Unaccessible call· " + object + '.' + method);
	}
 
	boolean authorized(JXON user, String object, String method);
	
	default Object run( JXON user, String object, String method, Object... args ) throws Exception{
		if( authorized(user,object,method) ) {
		    if( id().equals(object) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);		    
		    }else return get(object).run(method, args); 
		}
		throw new Exception("·Unaccessible call· " + object + '.' + method);
	}

	default Object run( JXON user, JXON command ) throws Exception{
	    return run(user, Channel.object(command), Channel.method(command), Channel.args(command));
	}

	default Component get( String id ) { return null; };	
}
