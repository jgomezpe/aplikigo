package nsgl.app.web.servlet;

import nsgl.store.DataStore;
import javax.servlet.http.HttpServlet;

public class DataStoreServlet extends HttpServlet{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4702270191848858022L;

	protected DataStore store;
	
	public void setStore( DataStore store ){ this.store = store; };
	public DataStore store(){ return store; }	
}
