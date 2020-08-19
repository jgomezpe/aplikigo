package nsgl.app.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nsgl.app.user.Repository;
import nsgl.json.JSON;
import nsgl.stream.Util;

public class OS extends FileManager{
	public static final String CAPTION = "caption";
	public static final String CHILDREN = "children";
	
	protected String realPath;
	protected String aliasPath;
	
	public OS(String id, Repository clients, String realPath, String aliasPath) {
		super(id, clients);
		this.id = id;		
		if(realPath.length()>0 && !realPath.endsWith("/") )  this.realPath = realPath + '/';
		else this.realPath = realPath;
		if( !aliasPath.endsWith("/") )  this.aliasPath = aliasPath + '/';
		else this.aliasPath = aliasPath;
	}
	
	protected String realPath() { return realPath; }
	protected String aliasPath() { return aliasPath; }
	
	protected String alias2real(String fileName) {
		String ap = aliasPath();
		if(fileName.indexOf("..")>=0 || !fileName.startsWith(ap)) return null;
		return realPath()+fileName.substring(ap.length());
	}
	
	protected String real2alias(String fileName) {
		String rp = realPath();
		if(fileName.indexOf("..")>=0 || !fileName.startsWith(rp)) return null;
		return aliasPath()+fileName.substring(rp.length());
	}
	
	@Override
	public boolean exist(String fileName) { return new File(fileName).exists(); }
	
	@Override
	public boolean store(String fileName, InputStream is) throws IOException{
		fileName = alias2real(fileName);
		if( fileName==null ) return false;
		if( fileName.endsWith("/") ) {
			File theDir = new File(fileName);
			// 	if the directory does not exist, create it
			if (!theDir.exists()) {
			    try{
			        theDir.mkdir();
				theDir.setReadable(true, true);
			        return true;
			    }catch(SecurityException se){
			        return false;
			    }        
			}
			return true;
		}else {
			File file = new File(fileName);  
			file.createNewFile();
			FileOutputStream os = new FileOutputStream(file);
			Util.write(is,os);
			os.close();
			return true;
		}
	}

	@Override
	public InputStream getIS(String fileName){
		fileName = alias2real(fileName);
		if( fileName==null || fileName.endsWith("/")) return null;
		try{ return new FileInputStream(fileName); }catch(Exception e) { return null; }
	}

	public boolean deleteDir(File dir) {
	    File[] files = dir.listFiles();
	    if(files != null) {
	        for (final File file : files) {
	            deleteDir(file);
	        }
	    }
	    return dir.delete();
	}

	@Override
	public boolean delete(String fileName) throws IOException {
		fileName = alias2real(fileName);
		if( fileName==null ) return false;
		return deleteDir(new File(fileName));
	}

	public JSON folder(String realName) throws IOException {
		File f = new File(realName);
		JSON json = new JSON();
		if(f.isDirectory()) {
			if( !realName.endsWith("/") ) realName += '/';
			String name = real2alias(realName);
			name = name.substring(0,name.length()-1);
			int idx = name.lastIndexOf('/');
			String caption;
			if( idx >=0 ) caption = name.substring(idx+1);
			else caption = name;
			json.set(ID, name);
			json.set(CAPTION, caption);
			String[] list = f.list();
			Object[] children = new Object[list.length];
			for(int i=0; i<list.length; i++) {
				if( list[i] != null )  children[i] = folder(realName+list[i]);
				else children[i] = null;
			}
			json.set(CHILDREN, children);
		}else{
			String name = real2alias(realName);
			int idx = name.lastIndexOf('/');
			String caption;
			if( idx >=0 ) caption = name.substring(idx+1);
			else caption = name;
			json.set(ID, name);
			json.set(CAPTION, caption);
		}
		return json;
	}

	@Override
	public JSON folder() throws IOException { return folder(realPath); }

	@Override
	public boolean store(String fileName, byte[] is) throws IOException {
	    	return this.store(fileName, Util.toInputStream(is));
	}
}