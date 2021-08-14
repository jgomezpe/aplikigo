package aplikigo.web.sitemap;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;

import aplikigo.server.Server;

public abstract class SiteMap implements Server{
    protected String urlmap(String u){
	StringBuilder sb = new StringBuilder();
	sb.append("\t<url>\n");
	sb.append("\t\t<loc>");
	sb.append(u);
	sb.append("</loc>\n");
	sb.append("\t</url>\n");
	return sb.toString();
    } 
	
    protected void response(OutputStream os) throws Exception{
	PrintWriter writer = new PrintWriter(os, true);
	writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
	writer.write("<urlset xmlns=\"http://www.sitemaps.org/schemas/sitemap/0.9\">\n");
	Iterator<String> col = sites();
	while( col.hasNext() ){ writer.write(urlmap(col.next())); }
	writer.write("</urlset>");
	writer.flush();
    }
	
    protected abstract Iterator<String> sites();

    public void run(InputStream is, OutputStream os) throws Exception{ response(os); }
}