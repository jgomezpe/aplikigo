package nsgl.web.user;

public abstract class UserManager {
	public static final String LOGOUT = "logout";
	public static final String LOGIN = "login";
	public static final String REGISTER = "register";
	public static final String RECOVER = "recover";
	public static final String UPDATE = "update";
	public static final String UNREGISTER = "unregister";

	public static final String CREDENTIAL = "credential";
	
	/*
	 * Users
	 */
	public abstract User get( String id, String password );
	public abstract boolean remove( String id );
	public abstract boolean add( User user );
	public abstract boolean set( User user );
	public abstract boolean recover( String id );

	/*
	 * Credentials
	 */
	public abstract String issue(String id, String password);
	public abstract String id(String credential);
	public abstract boolean revoke(String credential);
	
	public User anonimizeUser(User user) {
		String credential = issue(user.id(),user.password());
		user = new User(user);		
		user.remove(User.ID);
		user.remove(User.PASSWORD);
		user.set(CREDENTIAL, credential);
		return user;
	}

	public User login( String id, String password ) {
		User user = get( id, password );
		if( user!=null ) user=anonimizeUser(user);
		return user;
	}
	
	public User register( User user ) {
	    if( !add(user) ) return null;
	    return anonimizeUser(user);
	}
	
	public boolean logout( String credential ) { return revoke(credential); }
	
	public boolean update( String credential, User user ) {
		String id = id(credential);
		return id==user.id() && set(user); 
	}
	
	public boolean del( String credential ) {
		String id = id(credential);
		return id!=null && remove(id) && revoke(credential);
	}
}