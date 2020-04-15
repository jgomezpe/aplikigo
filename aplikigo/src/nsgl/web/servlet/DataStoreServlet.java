package nsgl.web.servlet;

import nsgl.store.DataStore;

public abstract class DataStoreServlet /*extends Servlet*/{
	/**
	 * 
	 */
	private static final long serialVersionUID = -4702270191848858022L;

	protected DataStore store;
	
	public void setStore( DataStore store ){ this.store = store; };
	public DataStore store(){ return store; }	
}
