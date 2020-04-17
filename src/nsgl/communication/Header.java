package nsgl.communication;

import nsgl.json.JSON;

public class Header extends JSON{
	/**
	 * Type of the content carried by the package
	 */
	public static final String CONTENT_TYPE = "ContentType";
	
	/**
	 * The content of the package is a String
	 */
	public static final String TXT = "txt"; 
	
	/**
	 * The content of the package is an array of bytes
	 */
	public static final String BLOB = "blob";

	public static final String TARGET="target";
	public static final String METHOD="method";
	public static final String ARGS="args";
	
	public Header() { super(); }
	public Header( JSON header ) { super(header); }
	public String target() { return getString(TARGET); }
	public String method() { return getString(METHOD); }
	public Object[] args() { return getArray(ARGS); }	

	/**
	 * Gets the type of the package content
	 * @return Type of the package content
	 */
	public String contentType(){ return getString(CONTENT_TYPE); }
}