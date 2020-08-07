package nsgl.app;

import java.io.IOException;

import nsgl.json.JXON;
import nsgl.stringify.Stringifyable;

public class Command extends JXON{
	/**
	 * Header label of a package
	 */
	public static final String OBJECT = "object";

	/**
	 * Header label of a package
	 */
	public static final String METHOD = "method";

	/**
	 * Header label of a package
	 */
	public static final String ARGS = "args";

	public Command( String object, String method, Object... args ) throws IOException{
	    args = args.clone();
	    this.set(OBJECT, object);
	    this.set(METHOD, method);
	    int i=0;
	    while(i<args.length && Stringifyable.cast(args[i])!=null) i++;
	    if(i<args.length) throw new IOException("·Cannot send argument· " + i);
	    this.set(ARGS, args);
	}

	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	public String object() { return this.getString(OBJECT); }

	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	public String method() { return this.getString(METHOD); }
	
	public Object[] args() throws IOException{ return this.getArray(ARGS); }	
}
