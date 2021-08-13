package aplikigo.java.reflect;

import java.io.ByteArrayInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLStreamHandler;
import java.util.Arrays;
import java.util.HashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class FromMemory {
	public static HashMap<String, byte[]> jar(byte[] bytes) throws Exception{
		JarInputStream jar = new JarInputStream(new ByteArrayInputStream(bytes));
		final HashMap<String, byte[]> map = new HashMap<>();
		JarEntry nextEntry = null;
		while ((nextEntry = jar.getNextJarEntry()) != null) {
			final int est = (int) nextEntry.getSize();
			byte[] data = new byte[est > 0 ? est : 1024];
			int real = 0;
			for (int r = jar.read(data); r > 0; r = jar.read(data, real, data.length - real)) {
				if (data.length == (real += r)) {
					data = Arrays.copyOf(data, data.length * 2);
				}
			}
			if (real != data.length) data = Arrays.copyOf(data, real);
			map.put("/" + nextEntry.getName(), data);
		}
		jar.close();
		return map;
	} 
	
	public static HashMap<String, byte[]> zip(byte[] bytes) throws Exception{
		ZipInputStream zip = new ZipInputStream(new ByteArrayInputStream(bytes));
		final HashMap<String, byte[]> map = new HashMap<>();
		ZipEntry nextEntry = null;
		while ((nextEntry = zip.getNextEntry()) != null) {
			final int est = (int) nextEntry.getSize();
			byte[] data = new byte[est > 0 ? est : 1024];
			int real = 0;
			for (int r = zip.read(data); r > 0; r = zip.read(data, real, data.length - real)) {
				if (data.length == (real += r)) {
					data = Arrays.copyOf(data, data.length * 2);
				}
			}
			if (real != data.length) data = Arrays.copyOf(data, real);
			map.put("/" + nextEntry.getName(), data);
		}
		zip.close();
		return map;
	} 
	
	public static URL url(HashMap<String, byte[]> map) throws Exception{
		return new URL("x-buffer", null, -1, "/", new URLStreamHandler() {
			@Override
			protected URLConnection openConnection(URL u) throws IOException {
				byte[] data;
				try{ data = map.get(u.getFile()); }catch(Exception e){ throw new FileNotFoundException(u.getFile()); }
				return new URLConnection(u) {
					@Override
					public void connect() throws IOException{}

					@Override
					public InputStream getInputStream() throws IOException{ return new ByteArrayInputStream(data); }
				};
			}
		});	
	}

	public static ClassLoader jarLoader(byte[] bytes){
		try { return new URLClassLoader(new URL[]{url(jar(bytes))}, FromMemory.class.getClassLoader());	} catch (Exception e) { e.printStackTrace(); }
		return null;
	}
}