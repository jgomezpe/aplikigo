/**
 * <p>Copyright: Copyright (c) 2019</p>
 *
 * <h3>License</h3>
 *
 * Copyright (c) 2019 by Jonatan Gomez-Perdomo. <br>
 * All rights reserved. <br>
 *
 * <p>Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * <ul>
 * <li> Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 * <li> Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 * <li> Neither the name of the copyright owners, their employers, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * </ul>
 * <p>THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNERS OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 *
 *
 *
 * @author <A HREF="http://disi.unal.edu.co/profesores/jgomezpe"> Jonatan Gomez-Perdomo </A>
 * (E-mail: <A HREF="mailto:jgomezpe@unal.edu.co">jgomezpe@unal.edu.co</A> )
 * @version 1.0
 */
package nsgl.communication;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import nsgl.io.IOUtil;
import nsgl.json.JSON;
import nsgl.stringify.Stringifyable;

/**
 * <p>Title: Package</p>
 *
 * <p>Description: A package that can be sent through the WEB</p>
 *
 */
public class Package{
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

	/**
	 * Type of the content carried by the package
	 */
	public static final String CONTENT_TYPE = "ContentType";
	
	/**
	 * The content of the package is a String
	 */
	public static final String TXT = "txt"; 
	/**
	 * Header label of a package
	 */
	public static final String BLOB = "blob";

	
	/**
	 * Package information
	 */
	protected JSON header;
	
	/**
	 * Content of the package
	 */
	protected InputStream is;
	
	public Package( int size, InputStream is ) throws IOException{
	    byte[] buffer = new byte[size];
	    is.read(buffer);
	    header = new JSON( IOUtil.toString(IOUtil.toInputStream(buffer)) );
	    Object[] a = header.getArray(ARGS);
	    size = 0;
	    byte[][] b = new byte[a.length][0];
	    for( int i=0; i<a.length; i++) {
		if(a[i] instanceof JSON && ((JSON)a[i]).get(BLOB)!=null) {
		    int k = ((JSON)a[i]).getInt(BLOB);
		    b[i] = new byte[k];
		    is.read(b[i]);
		    size += b[i].length;
		}
	    }
	    if( size > 0 ) {
		buffer = new byte[size];
	    	size=0;
	    	for( int i=0; i<b.length; i++ ) {
	    	    System.arraycopy(b[i], 0, buffer, size, b[i].length);
	    	    size += b[i].length;
	    	}
	    	is = new ByteArrayInputStream(buffer);
	    	header.set(CONTENT_TYPE, BLOB);
	    }else {
	    	header.set(CONTENT_TYPE, TXT);
	    }
	}
	
	public Package( String object, String method, Object... args ){
	    args = args.clone();
	    header = new JSON();
	    header.set(OBJECT, object);
	    header.set(METHOD, method);
	    int i=0;
	    while(i<args.length && Stringifyable.cast(args[i])!=null) i++;
	    if(i<args.length) {
		int size = 0;
		byte[][] b = new byte[args.length][0];
		for(i=0; i<args.length; i++ ) {
		    if( Stringifyable.cast(args[i])==null ) {
			if( args[i] instanceof InputStream )
			    try { b[i] = IOUtil.toByteArray((InputStream)args[i]); }
			    catch (IOException e) { b[i] = new byte[0]; }
			else if( args[i] instanceof byte[] ) b[i] = (byte[])args[i];
			JSON j = new JSON();
			j.set(BLOB, b[i].length);
			args[i] = j;
			size += b[i].length;
		    }
		}
		byte[] buffer = new byte[size];
		size=0;
		for( i=0; i<b.length; i++ ) {
		    System.arraycopy(b[i], 0, buffer, size, b[i].length);
		    size += b[i].length;
		}
		is = new ByteArrayInputStream(buffer);
		header.set(CONTENT_TYPE, BLOB);
	    }else {
		is = null;
		header.set(CONTENT_TYPE, TXT);
	    }
	    header.set(ARGS, args);
	}
	
	/**
	 * Package constructor
	 * @param header Package information
	 * @param is Content of the package
	 */
	public Package( JSON header, InputStream is ) {
		this.header = header;
		this.is = is;
	}

	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	public String object() { return header.getString(OBJECT); }

	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	public String method() { return header.getString(METHOD); }
	
	public Object[] args() throws IOException{
	    Object[] a = header.getArray(ARGS).clone();
	    for( int i=0; i<a.length; i++) {
		if(a[i] instanceof JSON && ((JSON)a[i]).get(BLOB)!=null) {
		    int k = ((JSON)a[i]).getInt(BLOB);
		    byte[] b = new byte[k];
		    is.read(b);
		    a[i] = b;
		}
	    }
	    return a;
	}
	
	public byte[] headerbyte() throws IOException { return IOUtil.toByteArray(IOUtil.toInputStream(header.stringify())); }
	public InputStream is() { return is; }
	
}