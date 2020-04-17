package nsgl.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.communication.Header;
import nsgl.communication.Package;
import nsgl.generic.list.Queue;
import nsgl.io.file.FileLoaderFromServer;
import nsgl.json.JSON;
import nsgl.web.Session;

public class Application extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7070929784038920566L;
	
	protected nsgl.app.Application server;
	
	public static final String app_queue = "app.queue";
	
	protected Queue<Package> queue = new Queue<Package>();
	
	public void addPackage( Package p ) { queue.add(p); }
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ doPost(request, response); }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try{
			String url = request.getRequestURL().toString();
			String servlet = request.getServletPath().substring(1);
			url = url.substring(0,url.length()-servlet.length());
			System.out.println("URL:"+url);
			System.out.println("Servlet:"+servlet);
			server.addFileLoader("servlet", new FileLoaderFromServer(url));
			
			JSON header = new JSON(request.getHeader(Package.HEADER));
			header.set(Header.CONTENT_TYPE, request.getContentType());

			Session session = new Session(request.getSession(true));
			header.set(Session.SESSION, session);
			
			Package req = new Package(header, request.getInputStream());
			System.out.println("[WebServlet]"+header);
			Package resp;
			if(req.header().target().equals(app_queue)) resp = queue.get();
			else resp = (Package)server.addPackage( req );
			
			Header h = resp.header();
			response.setContentType(h.contentType());
			response.setHeader(Package.HEADER, h.toString());
			InputStream in = resp.content();
			OutputStream out = response.getOutputStream();
			byte[] buffer = new byte[1048576];
			int len = in.read(buffer);
			while (len != -1) {
			    out.write(buffer, 0, len);
			    len = in.read(buffer);
			}
		}catch(Exception e){
			e.printStackTrace();
			response.getOutputStream().print(e.getMessage());
		}
	}	
}