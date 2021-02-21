package aplikigo.net;

import java.io.IOException;

import jxon.JXON;

public interface Channel {
	Object send( JXON pack ) throws Exception;
	Object receive( JXON pack ) throws Exception;
	
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
	
	static JXON pack( byte[] buffer ) throws IOException{ 
	    return JXON.parse(new String(buffer));
	}
	
	static JXON pack( Object[] commands ) throws IOException{
	    JXON pack = new JXON();
	    pack.set( COMMAND, commands ); 
	    return pack;
	}

	static Object[] commands(JXON json) { return json.array(COMMAND); }
	
	static JXON credential(JXON json) { return json.object(CREDENTIAL); }
	
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
	
	static JXON command( String object, String method, Object... args ){
	    JXON c = new JXON();
	    if( !c.storable(args) ) return null;
	    c.set(OBJECT, object);
	    c.set(METHOD, method);
	    c.set(ARGS, args);
	    return c;
	}

	/**
	 * Gets the package information
	 * @return Information of the package (JXON object)
	 */
	static String object(JXON json) { return json.string(OBJECT); }

	/**
	 * Gets the package information
	 * @return Information of the package (JXON object)
	 */
	static String method(JXON json) { return json.string(METHOD); }
	
	static Object[] args(JXON json) throws IOException{ return json.array(ARGS); }	
}