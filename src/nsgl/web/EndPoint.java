package nsgl.web;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class EndPoint extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1845483864380178591L;
	public static final String CODE = "code";
	public static final String OPER = "oper";
	public static final String NOP = "nop";
	public static final String VALID = "valid";
	public static final String INVALID = "invalid";
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ doPost(request, response); }
}