package nsgl.web;

import nsgl.json.JSON;

public class Page {
	protected String url;
	protected String file;
	protected String protocol;
	protected String port;
	protected String pack;
	protected Host host;
	protected JSON query;
	public Page(JSON json){
		query = json.getJSON("query");
		if(query==null) query = new JSON();
		host = new Host(json.getJSON("host"));
		url = json.getString("url");
		file = json.getString("file");
		protocol = json.getString("protocol");
		port = json.getString("port");
		pack = json.getString("pack");
	}
	
	public Page(String url){
		this.url = url;
		// Obtaining the query
		query = new JSON();
		int k = url.indexOf('?');
		if( k>=0 ){
			String q = url.substring( k+1 );
			url = url.substring( 0, k );
			String[] qs = q.split("&");
			for( int i=0; i<qs.length; i++ ) {
				String[] split = qs[i].split("=");
				query.set(split[0], split[1]);
			}
		}

		// Determining the requested html file
		if( url.endsWith("/") ) url = url.substring(0,url.length()-1);
		if( url.endsWith(".html") ){
			k = url.lastIndexOf('/');
			file = url.substring(k+1,url.length());
			url = url.substring(0,k);
		}else file = "index.html";	
		
		// Determining the protocol
		k = url.indexOf("://");
		if( k>=0 ){
			protocol = url.substring(0,k);
			url = url.substring(k+3);
		}else protocol="http";

		// Determining the port
		k = url.lastIndexOf(':');
		if( k >= 0 ){
			port = url.substring(k+1, url.length());
			url = url.substring(0,k);
		}else port = "";

		// Getting domain type and country
		String www;
		String[] match = url.split("[wW]{3}\\d?.");
		if (match.length==2 && match[0].length()==0) {
			www = url.substring(0,url.length()-match[1].length());
			url = match[1];
		}else www = "";

		String[] types = new String[]{"com", "edu", "net", "org", "gov", "mil"};
		String[] divide=null;
		int i=0;
		while( i<types.length && (divide = get( url, types[i] )) == null ){ i++; }
		if( divide==null ) divide = new String[]{url, "", ""};
		url = divide[0];
		String type = divide[1];
		String country = divide[2];

		// Domain and subdomain
		String domain, subdomain;
		k = url.lastIndexOf('.');
		if( k>0 ){
			domain = url.substring(k+1);
			subdomain = url.substring(0,k);
		}else{
			domain = url;
			subdomain = "";
		}
		
		host = new Host(www, subdomain, domain, type, country );
		pack = (host.subdomain!=null&&host.subdomain!="")?host.subdomain:"prion"; 
	}

	public String server(){ return protocol + "://" + host.getDomain() +((port!=null&&port.length()>0)?':'+port:"") + '/'; }

	protected String[] get( String domain, String type ){
		String ptype = '.'+type;
		int k = domain.lastIndexOf(ptype);
		if( k >= 0 ){
			if( k+ptype.length()==domain.length() ) return new String[]{domain.substring(0,k),type,""};
			if( domain.charAt(k+ptype.length())=='.' ) return new String[]{domain.substring(0,k),type,domain.substring(k+ptype.length()+1)};
		}
		return null;
	}
	
	public String protocol(){ return protocol; }
	public Host host(){ return host; }
	public String query( String key ){ try{ return (String)this.query.get(key); }catch (Exception e){ return null; } }

	public String packPath(){
		String pack = pack();
		return pack.length()>0?pack+'/':pack; 
	}

	public String pack(){ return pack; }

	public String vc(){ return "vc/"; }
	
	public String vcPack(){ return vc()+pack+'/'; }
	
	public String model(){
		String model = query("model");
		if( model==null || model.length()==0 ) model = "model.jar";
		return model;
	}
	public String mode(){
		String mode = query("mode");
		if( mode==null || mode.length()==0 ) mode = "servlet";
		return mode;
	}	
	public String language(){
		String lang = query("lang");
		if( lang==null || lang.length()==0 ) lang = "spanish";
		return lang;
	}	
	
	public static void main( String[] args ){
		Page page = new Page("https://prion.numtseng.com:8080/?lang=spah");
		System.out.println(page.server());
	}
}
