package nsgl.web.servlet;

import java.io.IOException;
import java.util.Iterator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nsgl.generic.keymap.KeyMap;
import nsgl.store.DataQuery;
import nsgl.store.DataStore;
import nsgl.web.WebConstant;

public class MapServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9076973270503422633L;

	protected DataStore store;
	
	public void setStore( DataStore store ){ this.store = store; };
	public DataStore store(){ return store; }	
	
	public MapServlet() {}

	protected String urlmap(String u ){
		StringBuilder sb = new StringBuilder();
		sb.append("\t<url>\n");
		sb.append("\t\t<loc>");
		sb.append(u);
		sb.append("</loc>\n");
		sb.append("\t</url>\n");
		return sb.toString();
	} 
	
	protected String response(){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
		DataQuery query = store.getQuery(WebConstant.RESOURCES);
		Iterator<KeyMap<String, Object>> col = store.query(query);
		while( col.hasNext() ){ sb.append((String)col.next().get(WebConstant.URL));	}
		sb.append("</urlset>");
		System.out.println("[MapServlet]");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{ doPost(request, response); }

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getOutputStream().print(response());
	}
}