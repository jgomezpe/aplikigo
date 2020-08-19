package nsgl.app.user;

import java.util.Date;

import nsgl.app.Application;
import nsgl.generic.Named;
import nsgl.generic.hashmap.HashMap;
import nsgl.json.JSON;

public class Client extends Named implements Application{
    	protected Repository repository;
    	protected HashMap<String, String> noUserMethods = new HashMap<String, String>();
    	protected HashMap<String, String> userMethods = new HashMap<String, String>();
    	protected JSON authorized = null;
    	
	public Client(String id, Repository repository) { this( id, false, repository ); }
	
	public Client(String id, boolean withRegister, Repository repository) {
	    super(id);
	    this.repository = repository;
	    noUserMethods.set("login","login");
	    noUserMethods.set("logout","logout");
	    noUserMethods.set("recover","recover");
	    if( withRegister ) noUserMethods.set("register","register");
	    userMethods.set("update","update");
	}
	
	public boolean recover( String id, String email ){
	    authorized = null;
	    return repository.recover(id, email); 
	}
	
	public boolean valid( String id, String credential ) {
	    return repository.withCredential(id, credential) != null;
	}
	
	public JSON login( String id, String password ) {
	    System.out.println("[Client] " + new Date(System.currentTimeMillis()));
	    JSON user = repository.get(id, password);
	    System.out.println("[Client] *" + new Date(System.currentTimeMillis()));
	    if( user != null ) {
		user.remove(Repository.PASSWORD);
		authorized = user;
	    }
	    return user;
	}
	
	public JSON register( JSON user ) {
	    if( repository.add(user) ) {
		user.remove(Repository.PASSWORD);
		authorized = user;
		return user;
	    }
	    return null;
	}
	
	public boolean logout( String caller ) {
	    authorized = null;
	    return true;
	}
	
	public boolean update(String old, JSON user) { 
	    return repository.update(authorized.string(Repository.ID), user); 
	}
	
	public static String id(JSON user) { return user.string(Repository.ID); }
	public static String password(JSON user) { return user.string(Repository.PASSWORD); }
	public static String credential(JSON user) { return user.string(Repository.CREDENTIAL); }
	
	public boolean accessible(String object, String method) {
	    return object.equals(id()) && (noUserMethods.get(method) != null || userMethods.get(method) != null);
	}

	@Override
	public boolean authorized(JSON user, String object, String method) {
	    return object.equals(id()) &&
		   (noUserMethods.get(method) != null ||
		    (userMethods.get(method) != null && authorized!=null && 
		    id(authorized).equals(id(user)) && credential(authorized).equals(credential(user))));
	}
}