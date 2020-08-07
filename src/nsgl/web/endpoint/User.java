package nsgl.web.endpoint;

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.user.Manager;
import nsgl.stream.Util;
import nsgl.web.EndPoint;

public class User extends EndPoint{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2037994614085800331L;
	
	public static final String USER = "user";

	protected Manager manager;
	
	public void setManager(Manager manager) { this.manager = manager; }
	
	public void writeUser(nsgl.app.user.User user, HttpServletResponse response) throws ServletException, java.io.IOException{
		response.addHeader(USER, user.stringify());
		response.addHeader(CODE, VALID);
		response.setContentType("application/octet-stream");
		InputStream is = user.avatar();
		if( is != null ){
			response.setContentType("application/octet-stream");
			Util.write(is,response.getOutputStream());
		}	    
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException{
		try {
			nsgl.app.user.User user = new nsgl.app.user.User(request.getHeader(USER));
			String oper = request.getHeader(OPER);	
			if( oper.equals(Manager.LOGOUT) ){
				if(manager.logout(user.getString(Manager.CREDENTIAL))) response.addHeader(CODE, VALID);
				else response.addHeader(CODE, oper);
			}else if( oper.equals(Manager.LOGIN) ){
				user = manager.login(user.id(),user.password());
				if(user!=null) writeUser(user,response);
				else response.addHeader(CODE, oper);		
			}else if(oper.equals(Manager.REGISTER)){
				user = manager.register(user);    
				if(user!=null) writeUser(user,response);
				else response.addHeader(CODE, oper); 	
			}else if(oper.equals(Manager.UNREGISTER)){
				if( manager.del(user.getString(Manager.CREDENTIAL)) ) response.addHeader(CODE, VALID);
				else response.addHeader(CODE, oper);
			}else response.addHeader(CODE, NOP);
		} catch (Exception e1) {
			e1.printStackTrace();
			response.addHeader(CODE, USER); 
		}
	}	
}
