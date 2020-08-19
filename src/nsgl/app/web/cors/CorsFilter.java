package nsgl.app.web.cors;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
/**
 * Servlet Filter implementation class CORSFilter
 */
// Enable it for Servlet 3.x implementations
/* @ WebFilter(asyncSupported = true, urlPatterns = { "/*" }) */
public class CorsFilter implements Filter {
    
    protected String origins;
    protected String methods;
    protected String headers;
    protected String exposed;
    
    /**
     * Default constructor.
     */
    public CorsFilter() {}
 
    /**
     * @see Filter#destroy()
     */
    public void destroy() {}
 
    /**
     * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
     */
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
 
        HttpServletRequest request = (HttpServletRequest) servletRequest;
 
        if(origins!=null) ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Origin", origins);
        if(methods!=null) ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Methods",methods);
        if(headers!=null) ((HttpServletResponse) servletResponse).addHeader("Access-Control-Allow-Headers", headers);
        if(exposed!=null) ((HttpServletResponse) servletResponse).addHeader("Access-Control-Expose-Headers", exposed);

        HttpServletResponse resp = (HttpServletResponse) servletResponse;
 
        // For HTTP OPTIONS verb/method reply with ACCEPTED status code -- per CORS handshake
        if (request.getMethod().equals("OPTIONS")) {
            resp.setStatus(HttpServletResponse.SC_ACCEPTED);
            return;
        }
 
        // pass the request along the filter chain
        chain.doFilter(request, servletResponse);
    }
 
    /**
     * @see Filter#init(FilterConfig)
     */
    public void init(FilterConfig fConfig) throws ServletException {
	  origins = fConfig.getInitParameter("cors.allowed.origins");
	  methods = fConfig.getInitParameter("cors.allowed.methods");
	  headers = fConfig.getInitParameter("cors.allowed.headers");
	  exposed = fConfig.getInitParameter("cors.exposed.headers");
    }
}