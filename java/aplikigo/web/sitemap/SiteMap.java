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
package aplikigo.web.sitemap;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import aplikigo.server.Server;

/**
 * <p>SiteMap server</p>
 *
 */
public abstract class SiteMap implements Server{
	protected String urlmap(String u){
		StringBuilder sb = new StringBuilder();
		sb.append("\t<url>\n");
		sb.append("\t\t<loc>");
		sb.append(u);
		sb.append("</loc>\n");
		sb.append("\t</url>\n");
		return sb.toString();
	} 
	
	protected void response(OutputStream os) throws Exception{
		PrintWriter writer = new PrintWriter(os, true);
		writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		writer.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
		Iterator<String> col = sites();
		while( col.hasNext() ){ writer.write(urlmap(col.next())); }
		writer.write("</urlset>");
		writer.flush();
	}
	
	protected abstract Iterator<String> sites();

	/**
	 * Runs a server's command
	 * @param is input stream used by the command server
	 * @param os output stream used by the command server
	 * @throws Exception If I/O or command error occurs
	 */
	public void run(InputStream is, OutputStream os) throws Exception{ response(os); }
}