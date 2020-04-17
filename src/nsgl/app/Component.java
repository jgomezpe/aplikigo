package nsgl.app;

import nsgl.generic.Named;
import nsgl.json.JSON;

import java.lang.reflect.InvocationTargetException;

import nsgl.communication.Header;
import nsgl.communication.Package;

public interface Component extends Named{
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
	
	default Object run( String method, Object... args ) {
		Class<?>[] types = new Class<?>[args.length];
		for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
		try{ return this.getClass().getMethod(method, types).invoke(this, args); }
		catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) { return null; }
	}
}