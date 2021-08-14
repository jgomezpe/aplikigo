package aplikigo.process;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import aplikigo.Component;
import aplikigo.server.JXONBaseServer;

public abstract class ProcessServer extends JXONBaseServer{
    protected ProcessRunner process = null;
    protected boolean starting;
    
    public ProcessServer(String name) {
	super(name);
    }
    
    public abstract ProcessRunner process();
    
    protected void input(String command) {
	if(process!=null && command.length()>0)
	    process.input(new ByteArrayInputStream(command.getBytes()));
    }
    
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
    
    public String pull(String command) {
	String response = "";
	if(process != null && !starting) response = inner_pull(command);
	return response;
    }
    
    @Override
    public Component get(String id) {
	if(id().equals(id)) return this;
	return null;
    }
}
