package aplikigo.web;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import aplikigo.server.Server;

public class EndPoint extends HttpServlet{
    private static final long serialVersionUID = 1914490939385194698L;
	    
    /**
     * Do not forget to override the init method or define a constructor method and
     * instantiate a specific server object 
     */
    protected Server server = null;
	
    public EndPoint() {}
    
    public EndPoint(Server server) { this.server = server; }
	
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
	InputStream is = request.getInputStream();
	OutputStream os = response.getOutputStream();
	try { server.run(is, os); } 
	catch (Exception e) {
	    e.printStackTrace();
	    /*JXON r = server.exception(e.getMessage());
	    PrintWriter writer = response.getWriter();
	    writer.write(r.stringify());
	    writer.flush();*/
	}
    }   
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ doPost(request, response); }	
}