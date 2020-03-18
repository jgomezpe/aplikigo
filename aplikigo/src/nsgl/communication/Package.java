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

import java.io.IOException;
import java.io.InputStream;

import nsgl.io.IOUtil;
import nsgl.json.JSON;

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
	public static final String HEADER = "header";

	/**
	 * Package information
	 */
	protected Header header;
	
	/**
	 * Content of the package
	 */
	protected InputStream is=null;
	
	/**
	 * Package constructor
	 * @param header Package information
	 * @param is Content of the package
	 */
	public Package( JSON header, InputStream is ) {
		this.header = new Header(header);
		this.is = is;
	}
	
	/**
	 * Package constructor when sending an String
	 * @param header Package information
	 * @param str Content of the package
	 */
	public Package( JSON header, String str ) {
		this( header, IOUtil.toInputStream(str) );
		this.header.set(Header.CONTENT_TYPE, Header.TXT);
	}
	
	/**
	 * Package constructor when sending a blob
	 * @param header Package information
	 * @param blob Content of the package
	 */
	public Package( JSON header, byte[] blob ) {
		this( header, IOUtil.toInputStream(blob) );
		this.header.set(Header.CONTENT_TYPE, Header.BLOB);
	}
	
	/**
	 * Gets the package information
	 * @return Information of the package (JSON object)
	 */
	public Header header() { return header; }

	/**
	 * Gets the package content
	 * @return Package content
	 */
	public InputStream content() { return is; }	
	
	public String contentAsString()  throws IOException { return IOUtil.toString(is); }
	
	public byte[] contentAsByteArray()  throws IOException { return IOUtil.toByteArray(is); }	
}