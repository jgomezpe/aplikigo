package nsgl.web.sitemap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.web.EndPoint;

public class SiteMapEndPoint extends EndPoint{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9076973270503422633L;

	protected SiteMap map;
	
	public void setSiteMap( SiteMap map ){ this.map = map; };

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getOutputStream().print(map.response());
	}
}