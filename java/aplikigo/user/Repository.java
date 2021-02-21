package aplikigo.user;

import jxon.JXON;

public interface Repository {
	public static final String ID = "id";
	public static final String PASSWORD = "password";
	public static final String CREDENTIAL = "credential";
	public static final String AVATAR = "avatar";
	public static final String EMAIL = "email";
	public static final String NICK = "nick";
	
	JXON get( String id, String password );
	JXON withCredential( String id, String credential );
	void removeCredential( String id, String credential);
	boolean update( String id, JXON user );
	boolean remove( String id );
	boolean add( JXON user );
	boolean recover( String id, String email );
}