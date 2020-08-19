package nsgl.app.net;

import java.io.IOException;

import nsgl.json.JSON;
import nsgl.stringify.Stringifyable;

public interface Channel {
	Object send( JSON pack ) throws Exception;
	Object receive( JSON pack ) throws Exception;
	
	/**
	 * Set of commands in the package
	 */
	static final String COMMAND = "command";

	/**
	 * Credential of the user sending the package of commands
	 */
	static final String CREDENTIAL = "credential";
	
	/**
	 * Type of the content carried by the package
	 */
	static final String CONTENT_TYPE = "ContentType";
	
	/**
	 * The content of the package is a String
	 */
	static final String TXT = "txt"; 
	/**
	 * Header label of a package
	 */
	static final String BLOB = "blob";
	
	static JSON pack( byte[] buffer ) throws IOException{ 
	    String code = new String(buffer);
	    return new JSON(code); 
	}
	
	static JSON pack( Object[] commands ) throws IOException{
	    JSON pack = new JSON();
	    pack.set( COMMAND, commands ); 
	    return pack;
	}

	static Object[] commands(JSON json) { return json.array(COMMAND); }
	
	static JSON credential(JSON json) { return json.object(CREDENTIAL); }
	
	/**
	 * Header label of a package
	 */
	static final String OBJECT = "object";

	/**
	 * Header label of a package
	 */
	static final String METHOD = "method";

	/**
	 * Header label of a package
	 */
	static final String ARGS = "args";
	
	static boolean storable( Object... args ){
	    int i=0;
	    while(i<args.length && Stringifyable.cast(args[i])!=null) i++;
	    return(i==args.length);
	}
	
	static JSON command( String object, String method, Object... args ){
	    if( !storable(args) ) return null;
	    JSON c = new JSON();
	    c.set(OBJECT, object);
	    c.set(METHOD, method);
	    c.set(ARGS, args);
	    return c;
	}

	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	static String object(JSON json) { return json.string(OBJECT); }

	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	static String method(JSON json) { return json.string(METHOD); }
	
	static Object[] args(JSON json) throws IOException{ return json.array(ARGS); }	
}