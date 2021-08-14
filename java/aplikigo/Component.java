package aplikigo;

import jxon.JXON;
import speco.object.Identifiable;

public interface Component extends Identifiable{
    static final String ID = "component";
    static final String MULTI = "multiple-run";
    static final String METHOD = "method";
    static final String ARGS = "args";
    static final String ERROR = "error";
    
    default boolean accessible(String method) {
	return method!=null && !method.equals("getClass"); 
    }
    
    Component get(String component);
    
    default Object run( String component, String method, Object... args ) throws Exception{
	if(component.equals(id())) return run(method, args);
	Component c = get(component);
	if( c!=null ) return c.run(method,args);
	throw new Exception("·Invalid component ·"+component);
    }
    
    default Object run( String method, Object... args ) throws Exception{
	if( accessible(method) ) {
	    Class<?>[] types = new Class<?>[args.length];
	    for( int i=0; i<args.length; i++ ) types[i] = args[i].getClass();
	    return this.getClass().getMethod(method, types).invoke(this, args);
	}
	throw new Exception("·Unaccessible method·" + method);
    }
		
    default boolean authorized(JXON command) { return true; }
    
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
    
    default JXON command(String component, String method, Object[] args) {
	JXON response = new JXON();
	response.set(ID, component);
	response.set(METHOD, method);
	response.set(ARGS, args);
	return response;	    
    }
    
    default JXON command(String method, Object[] args) {
	return this.command(id(),method, args);	    
    }
    
    default JXON exception(String msg) {
	return command(ERROR, new Object[] {msg});
    }
}