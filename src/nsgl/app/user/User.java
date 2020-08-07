package nsgl.app.user;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import nsgl.json.JSON;

public class User extends JSON{
	public static final String ID = "id";
	public static final String PASSWORD = "password";
	public static final String NICKNAME = "nickname";
	public static final String EMAIL = "email";
	public static final String AVATAR = "avatar";
	
	public User(String id, String password){
	    this.set(ID, id);
	    this.set(PASSWORD, password);
	}
	
	public User(User user) { super(user); }
	
	public User( String code )throws Exception{ super(code); }

	public String id(){ return getString(ID); }
	public String password(){ return getString(PASSWORD); }
	public String nickname(){ return getString(NICKNAME); }
	public String email(){ return getString(EMAIL); }
	public InputStream avatar() {
	    String avatar = getString(AVATAR);
	    if( avatar != null) {
		    try{ return new FileInputStream(avatar); }catch(FileNotFoundException e) {}
	    }
	    return null; 
	}
}