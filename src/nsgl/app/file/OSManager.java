package nsgl.app.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import nsgl.stream.Util;

public abstract class OSManager implements Manager{
	protected String realPath;
	protected String aliasPath;
	
	public OSManager(String realPath, String aliasPath) {
		this.realPath = realPath;
		if( !this.realPath.endsWith("/") )  this.realPath += '/';
		this.aliasPath = aliasPath;
		if( !this.aliasPath.endsWith("/") )  this.aliasPath += '/';
	}
	
	protected String alias2real(String fileName) {
		if(fileName.indexOf("..")>=0 || !fileName.startsWith(aliasPath)) return null;
		return realPath+fileName.substring(aliasPath.length());
	}
	
	protected String real2alias(String fileName) {
		if(fileName.indexOf("..")>=0 || !fileName.startsWith(realPath)) return null;
		return aliasPath+fileName.substring(realPath.length());
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
	public InputStream read(String fileName){
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

	public String folder(String realName, boolean isRoot) throws IOException {
	    	StringBuilder sb = new StringBuilder();
		File f = new File(realName);
		if(f.isDirectory()) {
			if( !realName.endsWith("/") ) realName += '/';
			sb.append("{");
			if( isRoot ){
				sb.append("\"plugin\":\"accordion\",");
			}
			String name = real2alias(realName);
			name = name.substring(0,name.length()-1);
			int idx = name.lastIndexOf('/');
			String caption;
			if( idx >=0 ) caption = name.substring(idx+1);
			else caption = name;
			sb.append("\"id\":\""+name+"\",");
			sb.append("\"caption\":\""+caption+"\",");
			sb.append("\"children\":[");
			char comma=' ';
			String[] list = f.list();
			for(int i=0; list!=null && i<list.length; i++) {
				sb.append(comma);
				sb.append(folder(realName+list[i],false));
				comma = ',';
			}
			sb.append("]}");
		}else{
			String name = real2alias(realName);
			int idx = name.lastIndexOf('/');
			String caption;
			if( idx >=0 ) caption = name.substring(idx+1);
			else caption = name;
			sb.append("{\"id\":\""+name+"\", \"caption\":\""+caption+"\"}");
		}
		return sb.toString();
	}

	@Override
	public String folder() throws IOException { return folder(realPath, true); }
}