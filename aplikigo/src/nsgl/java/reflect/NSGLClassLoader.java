package nsgl.java.reflect;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Enumeration;

import nsgl.generic.array.Vector;
import nsgl.io.reader.ByteReader;
import nsgl.iterator.Backable;
import nsgl.io.Readable;

public class NSGLClassLoader extends ClassLoader{
	protected Vector<ClassLoader> loader = new Vector<ClassLoader>();
	
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
		@SuppressWarnings("resource")
		ByteReader reader = new ByteReader(is);
		Readable r = Readable.cast(obj);
		r.read((Backable<Integer>)reader.iterator());
		return r;
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