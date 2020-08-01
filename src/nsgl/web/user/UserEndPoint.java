package nsgl.web.user;

import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.io.IOUtil;
import nsgl.web.EndPoint;

public class UserEndPoint extends EndPoint{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2037994614085800331L;
	
	public static final String USER = "user";

	protected UserManager manager;
	
	public void setManager(UserManager manager) { this.manager = manager; }
	
	public void writeUser(User user, HttpServletResponse response) throws ServletException, java.io.IOException{
		response.addHeader(USER, user.stringify());
		response.addHeader(CODE, VALID);
		response.setContentType("application/octet-stream");
		InputStream is = user.avatar();
		if( is != null ){
			response.setContentType("application/octet-stream");
			IOUtil.write(is,response.getOutputStream());
		}	    
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException{
		try {
			User user = new User(request.getHeader(USER));
			String oper = request.getHeader(OPER);	
			if( oper.equals(UserManager.LOGOUT) ){
				if(manager.logout(user.getString(UserManager.CREDENTIAL))) response.addHeader(CODE, VALID);
				else response.addHeader(CODE, oper);
			}else if( oper.equals(UserManager.LOGIN) ){
				user = manager.login(user.id(),user.password());
				if(user!=null) writeUser(user,response);
				else response.addHeader(CODE, oper);		
			}else if(oper.equals(UserManager.REGISTER)){
				user = manager.register(user);    
				if(user!=null) writeUser(user,response);
				else response.addHeader(CODE, oper); 	
			}else if(oper.equals(UserManager.UNREGISTER)){
				if( manager.del(user.getString(UserManager.CREDENTIAL)) ) response.addHeader(CODE, VALID);
				else response.addHeader(CODE, oper);
			}else response.addHeader(CODE, NOP);
		} catch (Exception e1) {
			e1.printStackTrace();
			response.addHeader(CODE, USER); 
		}
	}	
}
