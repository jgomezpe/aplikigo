package nsgl.app.web;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

import nsgl.json.JSON;

public class Captcha {
	protected boolean verified = false;
	protected String key;

	public static final String url = "https://www.google.com/recaptcha/api/siteverify";
	
	public Captcha( String key ){ this.key = key; }

	public synchronized String verify(String response){
		verified = false;
		if(response == null || "".equals(response)) return "empty"; // false;
		
	    try {
	        String url = "https://www.google.com/recaptcha/api/siteverify?" + "secret=" + key + "&response=" + response;
	        InputStream res = new URL(url).openStream();
	        BufferedReader rd = new BufferedReader(new InputStreamReader(res, Charset.forName("UTF-8")));

	        StringBuilder sb = new StringBuilder();
	        int cp;
	        while ((cp = rd.read()) != -1) {
	            sb.append((char) cp);
	        }
	        String jsonText = sb.toString();
	        res.close();

	        JSON json = new JSON(jsonText);
	        verified = json.bool("success");
	        return jsonText;
	    } catch (Exception e) {
	        //e.printStackTrace();
	    	return e.getMessage();
	    }
	}    
	
	public boolean verified(){ return verified; } 
}