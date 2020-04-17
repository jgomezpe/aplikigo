package nsgl.web;

import nsgl.json.JSON;

public class Host {
	protected String www;
	protected String subdomain;
	protected String domain;
	protected String type;
	protected String country;
	public Host( JSON host ){
		try{
			this.www = host.getString("www");
			this.subdomain = host.getString("subdomain");
			this.domain = host.getString("domain");
			this.type = host.getString("type");
			this.country = host.getString("country");
		}catch(Exception e){} 
	}
	public Host( String www, String subdomain, String domain, String type, String country ){
		this.www = www;
		this.subdomain = subdomain;
		this.domain = domain;
		this.type = type;
		this.country = country;
	}

	public static String addC( String value ){ return (value!=null&&value.length()>0)?value+'.':""; }
	public static String cAdd( String value ){ return (value!=null&&value.length()>0)?'.'+value:""; }
	public String getDomain(){ return addC(www)+addC(subdomain)+domain+cAdd(type)+cAdd(country); }
}
