package nsgl.app.web.sitemap;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.app.web.EndPoint;

public abstract class SiteMapEndPoint extends EndPoint{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9076973270503422633L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getOutputStream().print(((SiteMap)server).response());
	}
}