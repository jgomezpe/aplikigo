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
package aplikigo.remotegui;

import aplikigo.Component;
import aplikigo.server.JXONBaseServer;
import speco.object.Named;

/**
 * <p>A remote component</p>
 *
 */
public class RemoteComponent extends Named implements Component{
	protected JXONBaseServer server;

	/**
	 * Creates a remote component
	 * @param id Component's id
	 */
	public RemoteComponent(String id) { this(id, null); }
	
	/**
	 * Creates a remote component
	 * @param server Server
	 * @param id Component's id
	 */
	public RemoteComponent(String id, JXONBaseServer server) {
		super(id);
		this.server = server;
	}
	
	/**
	 * Sets remote server
	 * @param server Server
	 */
	public void setServer( JXONBaseServer server ) { this.server = server; }
	
	/**
	 * Runs the method of the component using the provided arguments
	 * @param method Method's name
	 * @param args Arguments for running the method
	 * @return Object resulting of running the method
	 * @throws Exception If some error occurs or the method is not available or accessible
	 */
	public Object run( String component, String method, Object... args ) throws Exception{
		return server.add(server.command(component, method, args));
	}

	/**
	 * Obtains a subcomponent
	 * @param component Subcomponent id
	 * @return <i>this</i> 
	 */
	@Override
	public Component get(String component) { return this; }
}