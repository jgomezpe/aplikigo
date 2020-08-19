package nsgl.app.user;

import nsgl.json.JSON;

public interface Repository {
	public static final String ID = "id";
	public static final String PASSWORD = "password";
	public static final String CREDENTIAL = "credential";
	public static final String AVATAR = "avatar";
	public static final String EMAIL = "email";
	public static final String NICK = "nick";
	
	JSON get( String id, String password );
	JSON withCredential( String id, String credential );
	void removeCredential( String id, String credential);
	boolean update( String id, JSON user );
	boolean remove( String id );
	boolean add( JSON user );
	boolean recover( String id, String email );
}