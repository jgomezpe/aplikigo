package nsgl.app.web.sitemap;

import java.util.Iterator;

import nsgl.app.Application;

public interface SiteMap extends Application{
	default String urlmap(String u){
		StringBuilder sb = new StringBuilder();
		sb.append("\t<url>\n");
		sb.append("\t\t<loc>");
		sb.append(u);
		sb.append("</loc>\n");
		sb.append("\t</url>\n");
		return sb.toString();
	} 
	
	default String response(){
		StringBuilder sb = new StringBuilder();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
		sb.append("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
		Iterator<String> col = sites();
		while( col.hasNext() ){ sb.append(urlmap(col.next())); }
		sb.append("</urlset>");
		System.out.println("[MapServlet]");
		System.out.println(sb.toString());
		return sb.toString();
	}
	
	Iterator<String> sites();

}
