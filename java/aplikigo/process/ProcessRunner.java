package aplikigo.process;
import java.io.*;

//
//Unalcol Service structure Pack 1.0 by Jonatan Gomez-Perdomo
//https://github.com/jgomezpe/unalcol/tree/master/services/
//
/**
*
* ExternalProcess  
* <p>A Class for Executing External Process (commands).</p>
*
* <P>
* <A HREF="https://github.com/jgomezpe/unalcol/blob/master/services/src/unalcol/reflect/process/ExternalProcess.java" target="_blank">
* Source code </A> is available.
*
* <h3>License</h3>
*
* Copyright (c) 2014 by Jonatan Gomez-Perdomo. <br>
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
public class ProcessRunner{

    protected Process process;
 
    /**
     * Stream used for sending inputs to the associated external process
     */
    protected InputStream is = null;

    protected InputToOutputStream input = null;

    /**
     * Created a External Process representing the command sent as parameter
     * @param command The external process (including arguments) to be executed
     */
    public ProcessRunner( String[] command ){ 
	try {
	    process = Runtime.getRuntime().exec(command);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }

    /**
     * Created a External Process representing the command sent as parameter
     * @param commandLine The external process (including arguments) to be executed
     */
    public ProcessRunner( String commandLine ){
	try {
	    process = Runtime.getRuntime().exec(commandLine);
	} catch (IOException e) {
	    e.printStackTrace();
	}
    }
    
    public void input( InputStream is ) {
	if( input != null ) input.is = is;
	else input = new InputToOutputStream(this, is, process.getOutputStream());
    }

    public InputStream output() {
	return process!=null?process.getInputStream():null;
    }
    
    public void output(OutputStream os) {
	InputStream out = output();
	if(out!=null)  new InputToOutputStream(this, out, os);
    }

    public InputStream error() {
	return process!=null?process.getErrorStream():null;
    }
    
    public void error(OutputStream os) {
	InputStream out = error();
	if(out!=null)  new InputToOutputStream(this, out, os);
    }
    
    public void streams(InputStream is, OutputStream out, OutputStream err) {
	input(is);
	output(out);
	error(err);
    }
    
    public boolean isRunning() { return process.isAlive(); }  
    
    public void end() { process.destroy(); }
    
    public static void main(String[] args) {
	String prog = "x=int(input('?'))\\nprint(x+3)\\n";
	String cmd = "exec(\""+prog+"\")\nquit()\n";
	System.out.println(cmd);
	ProcessRunner p = new ProcessRunner("python3 -i");
	InputStream is = new ByteArrayInputStream(cmd.getBytes());
	p.streams(is,System.out,System.err);
	try {
	    Thread.sleep(100);
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
	p.input(System.in);
    }
}