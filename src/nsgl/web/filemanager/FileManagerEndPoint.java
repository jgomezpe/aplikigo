package nsgl.web.filemanager;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.io.IOUtil;
import nsgl.web.EndPoint;
import nsgl.web.user.User;
import nsgl.web.user.UserEndPoint;

public class FileManagerEndPoint extends EndPoint{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1542818041380350354L;

	protected FileManager manager;
	
	public void setManager( FileManager manager ){ this.manager = manager; }
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException{
		User user = null; 
		try { user = new User(request.getHeader(UserEndPoint.USER)); } catch (Exception e) {}
		if( user==null || !manager.authorized(user) ) {
			response.addHeader(CODE, UserEndPoint.USER+':'+(user==null?"null":user.id()));
			return;
		}
		
		String oper = request.getHeader(OPER);
		
		if(oper.equals(FileManager.FOLDER)){
			String folder = manager.folder();
			response.setHeader(CODE, VALID);
			response.setContentType("application/json");
			java.io.PrintWriter out = response.getWriter();
			out.println(folder);
		}else{
			String name = request.getHeader(FileManager.NAME);
			if( oper.equals(FileManager.STORE) ) {
				String code = FileManager.STORE;
				try{ if( manager.store(name,request.getInputStream()) ) code = VALID; }
				catch(IOException e) {}
				response.addHeader(CODE, code); 
			}else if( oper.equals(FileManager.READ) ){
				response.setContentType("application/octet-stream");
				int idx = name.lastIndexOf('/');
				String dName = name;
				if( idx>=0 ) dName = dName.substring(idx+1);
				response.setHeader("Content-disposition", "attachment; filename="+dName);
				String code = FileManager.READ;
				InputStream is = manager.read(name);
				if(is!=null) {
					code = VALID;
					response.addHeader(CODE, code);
					IOUtil.write(is, response.getOutputStream());
				}else {
					response.addHeader(CODE, code);
				}
			}else if( oper.equals(FileManager.DELETE) ){
				response.setContentType("text/plain");
				String code = FileManager.DELETE;
				if( manager.delete(name) ) code = VALID;
				response.addHeader(CODE, code);
			}else response.addHeader(CODE, NOP); 	
		}		
	}
	
}
