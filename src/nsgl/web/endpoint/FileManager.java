package nsgl.web.endpoint;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.file.Manager;
import nsgl.stream.Util;
import nsgl.web.EndPoint;

public class FileManager extends EndPoint{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1542818041380350354L;

	protected Manager manager;
	
	public void setManager( Manager manager ){ this.manager = manager; }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException{
		nsgl.app.user.User user = null; 
		try { user = new nsgl.app.user.User(request.getHeader(User.USER)); } catch (Exception e) {}
		if( user==null || !manager.authorized(user) ) {
			response.addHeader(CODE, User.USER+':'+(user==null?"null":user.id()));
			return;
		}
		
		String oper = request.getHeader(OPER);
		
		if(oper.equals(Manager.FOLDER)){
			String folder = manager.folder();
			response.setHeader(CODE, VALID);
			response.setContentType("application/json");
			java.io.PrintWriter out = response.getWriter();
			out.println(folder);
		}else{
			String name = request.getHeader(Manager.NAME);
			if( oper.equals(Manager.STORE) ) {
				String code = Manager.STORE;
				try{ if( manager.store(name,request.getInputStream()) ) code = VALID; }
				catch(IOException e) {}
				response.addHeader(CODE, code); 
			}else if( oper.equals(Manager.READ) ){
				response.setContentType("application/octet-stream");
				int idx = name.lastIndexOf('/');
				String dName = name;
				if( idx>=0 ) dName = dName.substring(idx+1);
				response.setHeader("Content-disposition", "attachment; filename="+dName);
				String code = Manager.READ;
				InputStream is = manager.read(name);
				if(is!=null) {
					code = VALID;
					response.addHeader(CODE, code);
					Util.write(is, response.getOutputStream());
				}else {
					response.addHeader(CODE, code);
				}
			}else if( oper.equals(Manager.DELETE) ){
				response.setContentType("text/plain");
				String code = Manager.DELETE;
				if( manager.delete(name) ) code = VALID;
				response.addHeader(CODE, code);
			}else response.addHeader(CODE, NOP); 	
		}		
	}
	
}
