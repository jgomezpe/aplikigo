package aplikigo.java.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import speco.array.Array;
import aplikigo.stream.Reader;

public class NSGLClassLoader extends ClassLoader{
	protected Array<ClassLoader> loader = new Array<ClassLoader>();
	
	public void addLoader( ClassLoader cl ) { loader.add(cl); }
	public void delLoader( ClassLoader cl ) { 
		Integer i = loader.find(cl);
		if( i!=null ) loader.remove(i);
	}
	public void clear() { loader.clear(); }
	
	@Override
    public Class<?> loadClass(String name) throws ClassNotFoundException{
		ClassNotFoundException e=null;
		for(ClassLoader cl:loader) {
			try {
				return cl.loadClass(name);
			}catch(ClassNotFoundException x){ e = x; }
		}
		throw e;
    }
	
	public Object newInstance( String className, InputStream is ) throws Exception{
		Class<?> cl = loadClass(className);
		Object obj = cl.newInstance();
		return Reader.read(obj,is);
	}

	@Override
	public URL getResource(String name){
		for(ClassLoader cl:loader) {
			URL url = cl.getResource(name);
			if( url != null ) return url;
		}
		return null;
	}
	
	public Enumeration<URL> getResources(String name) throws IOException{
		IOException e=null;
		for(ClassLoader cl:loader) {
			try {
				return cl.getResources(name);
			}catch(IOException x){ e = x; }
		}
		throw e;
	}

	public InputStream getResourceAsStream(String name) {
		for(ClassLoader cl:loader) {
			InputStream is = cl.getResourceAsStream(name);
			if( is != null ) return is;
		}
		return null;
	}
	
	// protected Package getPackage(String name)
	// protected Package[] getPackages()	
	
	protected static NSGLClassLoader nsgl = new NSGLClassLoader();
	public static NSGLClassLoader get(){ return nsgl; }
}