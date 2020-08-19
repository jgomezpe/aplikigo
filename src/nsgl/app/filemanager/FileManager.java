package nsgl.app.filemanager;

import java.io.InputStream;

import nsgl.app.Application;
import nsgl.app.user.Client;
import nsgl.app.user.Repository;
import nsgl.blob.Parse;
import nsgl.character.CharacterSequence;
import nsgl.generic.Named;
import nsgl.generic.hashmap.HashMap;
import nsgl.json.JSON;
import nsgl.stream.Util;

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
	    methods.set("folder","folder");
	    methods.set("logout","logout");
	    methods.set("delete","delete");
	    methods.set("store","store");
	    methods.set("read","read");
	}
	
	public abstract boolean exist(String fileName);
	public abstract boolean store(String fileName, byte[] is) throws IOException;
	public abstract InputStream getIS(String fileName);
	public abstract boolean delete(String fileName) throws IOException;
	public abstract JSON folder() throws IOException;
	
	@Override
	public boolean accessible(String object, String method) {
	    return id().equals(object) && methods.get(method) != null;
	}

	@Override
	public boolean authorized(JSON user, String object, String method) {
	    return accessible(object, method) && client.valid(Client.id(user), Client.credential(user));
	}
	
	public boolean store(String fileName, InputStream is) throws IOException{
	    return store(fileName, Util.toByteArray(is));
	}
	
	public boolean store(String fileName, String isBase64) throws IOException{
	    Parse p = new Parse();
	    return store(fileName, (byte[])p.parse(new CharacterSequence(isBase64)));
	}
	
	public byte[] read(String fileName) throws IOException{
	    return Util.toByteArray(getIS(fileName));
	}	
}