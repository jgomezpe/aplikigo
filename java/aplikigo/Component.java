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
package aplikigo;

import speco.jxon.JXON;
import speco.object.Identifiable;

/**
 * <p>Abstract application component</p>
 *
 */
public interface Component extends Identifiable{
	/**
	 * Component TAG
	 */
	static final String ID = "component";

	/**
	 * Multiple commands to execute TAG 
	 */
	static final String MULTI = "multiple-run";
    
	/**
	 * Method TAG (for sending the method in the component to run)
	 */
	static final String METHOD = "method";
  
	/**
	 * Method arguments TAG
	 */
	static final String ARGS = "args";
 
	/**
	 * Error TAG 
	 */
	static final String ERROR = "error";
    
	/**
	 * Determines if a method in the component can be run or not
	 * @param method Method's name
	 * @return <i>true</i> if the method of the component can be externally run, <i>false</i> otherwise
	 */
	default boolean accessible(String method) {
		return method!=null && !method.equals("getClass"); 
	}
    
	/**
	 * Obtains a subcomponent
	 * @param component Subcomponent id
	 * @return The subcomponent with the given id, <i>null</i> otherwise
	 */
	Component get(String component);
    
	/**
	 * Runs the method of the given component using the provided arguments
	 * @param component Component owning the method
	 * @param method Method's name
	 * @param args Arguments for running the method
	 * @return Object resulting of running the method
	 * @throws Exception If some error occurs or the method is not available or accessible
	 */
	default Object run( String component, String method, Object... args ) throws Exception{
		if(component.equals(id())) return run(method, args);
    	Component c = get(component);
    	if( c!=null ) return c.run(method,args);
    	throw new Exception("·Invalid component ·"+component);
	}
    
	/**
	 * Runs the method of the component using the provided arguments
	 * @param method Method's name
	 * @param args Arguments for running the method
	 * @return Object resulting of running the method
	 * @throws Exception If some error occurs or the method is not available or accessible
	 */
	default Object run( String method, Object... args ) throws Exception{
		if( accessible(method) ) {
			Class<?>[] types = new Class<?>[args.length];
			for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
			return this.getClass().getMethod(method, types).invoke(this, args);
		}
		throw new Exception("·Unaccessible method·" + method);
	}
		
	/**
	 * Determines if the method can be run according to the provided authorization information
	 * @param command Command information (includes authorization information)
	 * @return <i>true</i> if the method/command can be executed according to the provided authorization information, <i>false</i> otherwise
	 */
	default boolean authorized(JXON command) { return true; }
    
	/**
	 * Runs the command (if authorized and available)
	 * @param command Command information
	 * @return JXON object with the result of running the command
	 * @throws Exception If some error occurs or the method is not available or accessible or authorized
	 */
	default JXON run(JXON command)  throws Exception{
		String component = command.string(ID);
		String method = command.string(METHOD);
		Object[] args = command.array(ARGS);
		Object[] r = null;
		if(method.equals(MULTI)) {
			r = new Object[args.length];
			for( int i=0; i<args.length; i++ ) r[i] = run((JXON)args[i]);
		}else if(authorized(command)) r = new Object[] {run(component, method, args)};
		else throw new Exception("·Unauthorized method·" + method);
		return command(component,method,r);	    
	}
    
	/**
	 * Creates a JXON command configuration information
	 * @param component Component owning the method
	 * @param method Method's name
	 * @param args Arguments for running the method
	 * @return JXON configuration information
	 */
	default JXON command(String component, String method, Object[] args) {
		JXON response = new JXON();
		response.set(ID, component);
		response.set(METHOD, method);
		response.set(ARGS, args);
		return response;	    
	}
    
	/**
	 * Creates a JXON command configuration information
	 * @param method Method's name
	 * @param args Arguments for running the method
	 * @return JXON configuration information
	 */
	default JXON command(String method, Object[] args) {
		return this.command(id(),method, args);	    
	}
    
	/**
	 * Creates A JXON error information
	 * @param msg Error message
	 * @return JXON error information
	 */
	default JXON exception(String msg) {
		return command(ERROR, new Object[] {msg});
	}
}