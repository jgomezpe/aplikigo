package utila;

import java.io.IOException;
import java.util.HashMap;

import jxon.JXON;


public class I18N {
    protected static HashMap<String, String> dictionary = new HashMap<String, String>();
    protected static final char c = '·';
    
    public static void clear() {
	dictionary = new HashMap<String, String>();
    }
    
    public static void set( String key, String value ) {
	dictionary.put(key,value); 
    }
    
    public static String process( String code ) { return Template.get(code, I18N.dictionary, c); }
    
    public static void set(String code) {
	clear();
	try {
	    JXON json = JXON.parse(code);
	    for(String key:json.keys()) {
		String value = json.string(key);
		if(value!=null) dictionary.put(key, value);
	    }
	} catch (IOException e) {}
    }
}