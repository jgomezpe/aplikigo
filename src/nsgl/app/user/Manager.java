package nsgl.app.user;

import nsgl.app.Application;
import nsgl.json.JSON;

public abstract class Manager implements Application{
	public static final String USER = "user";
	public static final String PASSWORD = "password";
	public static final String CREDENTIAL = "credential";
	
	protected String id;
	
	public Manager( String id ) { this.id = id; }
	
	@Override
	public String id() { return id; }
	
	@Override
	public void id(String id) { this.id = id; }
	
	/*
	 * Users
	 */
	public abstract JSON get( String id, String password );
	public abstract boolean remove( String id );
	public abstract boolean add( JSON user );
	public abstract boolean update( JSON user );
	public abstract boolean recover( String id );

	/*
	 * Credentials
	 */
	public abstract String issue(String id, String password);
	public abstract String user(String credential);
	public abstract boolean revoke(String credential);
	
	public JSON anonimizeUser(JSON user) {
		String credential = issue(user.getString(USER),user.getString(PASSWORD));
		user.remove(PASSWORD);
		user.set(CREDENTIAL, credential);
		return user;
	}

	public JSON login( String id, String password ) {
		JSON user = get( id, password );
		if( user!=null ) user=anonimizeUser(user);
		return user;
	}
	
	public JSON register( JSON user ) {
	    if( !add(user) ) return null;
	    return anonimizeUser(user);
	}
	
	public boolean logout( String credential ) { return revoke(credential); }
	
	public boolean update( String credential, JSON user ) {
		String id = user(credential);
		return id==user.getString(ID) && update(user); 
	}
	
	public boolean del( String credential ) {
		String id = user(credential);
		return id!=null && remove(id) && revoke(credential);
	}
}