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
package aplikigo.server;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import aplikigo.Component;
import kerno.process.ProcessRunner;

/**
 * <p>Remote server that runs a java process</p>
 *
 */
public abstract class ProcessServer extends JXONBaseServer{
	protected ProcessRunner process = null;
	protected boolean starting;
    
	/**
	 * Creates a Process server
	 * @param id Server id
	 */
	public ProcessServer(String id) { super(id); }
    
	/**
	 * Gets the associated process runner
	 * @return Process runner
	 */
	public abstract ProcessRunner process();
    
	/**
	 * Sets as input to the process the given string
	 * @param command Input stream for the process
	 */
	protected void input(String command) {
		if(process!=null && command.length()>0)
			process.input(new ByteArrayInputStream(command.getBytes()));
	}
    
	/**
	 * Initializes the process runner with the command 
	 * @param command Command to run
	 * @return Response of the initialization
	 */
	public abstract String init(String command);
    
	public String start(String command) {
		starting = true;
		queue.clear();
		if(process != null && process.isRunning()) process.end();
		process = process();
		String response = init(command);
		starting = false;
		return response;
	}
    
	/**
	 * Ends the process
	 * @param command Extra information for ending the process
	 * @return Response of the process ending
	 */
	public String end(String command) {
		if(process != null) process.end();
		return "";
	}
    
	protected String inner_pull(String command) {
		String response = "";
		StringBuilder sb = new StringBuilder();
		try {
			InputStream out = process.output();
			while(out.available()>0) {
				char c = (char)out.read();
				System.out.print(c);
				sb.append(c);
			}
			InputStream err = process.error();
			while(err.available()>0) {
				char c = (char)err.read();
				System.out.print(c);
				sb.append(c);
			}
			if(out.available()>0 || err.available()>0 || process.isRunning()) input(command);
		}catch(Exception e) { e.printStackTrace(); }
		response = sb.toString();
		if( response.length() == 0 && !process.isRunning()) response = null;
		return response;
	}
    
	/**
	 * Gets process outputs (checks the responses queue)
	 * @param command Pulling command
	 * @return Responses produced by the process
	 */
	public String pull(String command) {
		String response = "";
		if(process != null && !starting) response = inner_pull(command);
		return response;
	}
    
	/**
	 * Obtains a subcomponent
	 * @param id Subcomponent id
	 * @return The subcomponent with the given id, <i>null</i> otherwise
	 */
	@Override
	public Component get(String id) {
		if(id().equals(id)) return this;
		return null;
	}
}