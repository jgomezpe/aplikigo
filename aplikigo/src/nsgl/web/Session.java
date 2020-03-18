package nsgl.web;

import javax.servlet.http.HttpSession;

import nsgl.json.JSON;

public class Session extends JSON{
	public static final String ID="id";
	public static final String START="start";
	public static final String LAST="last";
	public static final String SESSION="session";
	 
	public Session( HttpSession session ) {
		set(ID, session.getId());
		set(START, ""+session.getCreationTime());
		set(LAST, ""+session.getLastAccessedTime());
	}
}
