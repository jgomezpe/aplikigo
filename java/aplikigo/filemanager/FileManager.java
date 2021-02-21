package aplikigo.filemanager;

import java.io.InputStream;
import java.util.HashMap;

import aplikigo.Application;
import aplikigo.stream.Util;
import aplikigo.user.Client;
import aplikigo.user.Repository;
import jxon.JXON;
import lifya.lexeme.BlobParser;
import speco.object.Named;

import java.io.IOException;

public abstract class FileManager extends Named implements Application{
	public static final String NAME = "Name";
	public static final String STORE = "store";
	public static final String DELETE = "del";
	public static final String READ = "read";
	public static final String FOLDER = "folder";

	protected Client client;
    	protected HashMap<String, String> methods = new HashMap<String, String>();
	
	public FileManager(String id, Repository clients) { 
	    super(id);
	    this.client = new Client("clients", false, clients); 
	    methods.put("folder","folder");
	    methods.put("logout","logout");
	    methods.put("delete","delete");
	    methods.put("store","store");
	    methods.put("read","read");
	}
	
	public abstract boolean exist(String fileName);
	public abstract boolean store(String fileName, byte[] is) throws IOException;
	public abstract InputStream getIS(String fileName);
	public abstract boolean delete(String fileName) throws IOException;
	public abstract JXON folder() throws IOException;
	
	@Override
	public boolean accessible(String object, String method) {
	    return id().equals(object) && methods.get(method) != null;
	}

	@Override
	public boolean authorized(JXON user, String object, String method) {
	    return accessible(object, method) && client.valid(Client.id(user), Client.credential(user));
	}
	
	public boolean store(String fileName, InputStream is) throws IOException{
	    return store(fileName, Util.toByteArray(is));
	}
	
	public boolean store(String fileName, String isBase64) throws IOException{
	    BlobParser p = new BlobParser();
	    return store(fileName, p.get(isBase64));
	}
	
	public byte[] read(String fileName) throws IOException{
	    return Util.toByteArray(getIS(fileName));
	}	
}