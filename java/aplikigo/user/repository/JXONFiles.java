package aplikigo.user.repository;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import aplikigo.stream.Resource;
import aplikigo.stream.loader.FromOS;
import aplikigo.user.Repository;
import jxon.JXON;

public class JXONFiles implements Repository{
    public static final String EXT = ".json";
    public static final String CREDS = ".credential";
    
    protected JXON authorized = null;
    protected String path;
    protected Resource resource = new Resource();
    
    public JXONFiles(String path) {
	if(path.length()>0 && !path.endsWith("/")) this.path = path + '/';
	else this.path = path;
	resource.add("local", new FromOS(path));
    }
    
    protected boolean invalid(String id) { return (id==null || id.length()==0 || id.endsWith("/")); }
    
    protected String issue() {
	StringBuilder sb = new StringBuilder();
	for(int i=0; i<20; i++) {
	    sb.append((char)(' '+(int)(Math.random()*97)));
	}
	return sb.toString();
    }
    
    @Override
    public JXON get(String id, String password) {
	if(invalid(id)) return null;
	try {
	    String txt = resource.txt(id+EXT);
	    JXON user = JXON.parse(txt);
	    if( user.string(PASSWORD).equals(password) ) {
		String credential = issue();
		FileWriter cred = new FileWriter(path+id+CREDS, true);
		cred.write(credential+"\n");
		cred.close();
		user.set(CREDENTIAL, credential);
		return user;
	    }
	} catch (IOException e) {}
	return null;
    }

    @Override
    public boolean update(String id, JXON user) {
	if( add(user) ) return remove(id);
	return false;
    }

    @Override
    public boolean remove(String id) {
	if(invalid(id)) return false;
	File file = new File(path+id+CREDS);
	if( file.exists() ) file.delete();
	file = new File(path+id+EXT);
	if( file.exists() ) return file.delete();
	return false;
    }

    @Override
    public boolean add(JXON user) {
	String id = user.string(ID);
	if(invalid(id)) return false;
	File file = new File(path+id+EXT);
	if( !file.exists() ) {
	    try {
		FileWriter out = new FileWriter(path+id+EXT);
		out.append(user.stringify());
		out.close();
		return true;
	    } catch (IOException e) {}
	}
	return false;
    }

    @Override
    public boolean recover(String id, String email) {
	return false;
    }

    @Override
    public JXON withCredential(String id, String credential) {
	try {
	    String txt = resource.txt(id+CREDS);
	    txt = txt.substring(0,txt.length()-1);
	    String[] creds = txt.split("\n");
	    int i=0;
	    while(i<creds.length && !credential.equals(creds[i])) i++;
	    if(i<creds.length) {
		JXON user = new JXON();
		user.set(ID,id);
		user.set(CREDENTIAL, credential);
		return user;
	    }
	} catch (IOException e) {}
	return null;
    }

    @Override
    public void removeCredential(String id, String credential) {
	try {
	    String txt = resource.txt(id+CREDS);
	    String[] creds = txt.split("\n");
		StringBuilder sb = new StringBuilder();	
	    int i=0;
	    while(i<creds.length && !credential.equals(creds[i])) {
		sb.append(creds[i]);
		sb.append('\n');
		i++;
	    }
	    if(i<creds.length) {
		i++;
		while(i<creds.length) {
		    sb.append(creds[i]);
		    sb.append('\n');
		    i++;
		}
		FileWriter file = new FileWriter(path+id+CREDS);
		file.write(sb.toString());
		file.close();
	    }
	} catch (IOException e) {}
    }
}