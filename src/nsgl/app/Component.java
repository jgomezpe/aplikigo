package nsgl.app;

import nsgl.json.JSON;

public interface Component {
	default boolean accessible(String method) { return method!=null && !method.equals("getClass"); }
	
	default Object run( String method, Object... args ) throws Exception{
		if( accessible(method) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);
		}
		throw new Exception("·Unaccessible method·" + method);
	}
    
	default boolean authorized(JSON user, String method) { return accessible(method); }
	
	default Object run( JSON user, String method, Object... args ) throws Exception{
		if( authorized(user,method) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);
		}
		throw new Exception("·Unaccessible method·" + method);
	}
}