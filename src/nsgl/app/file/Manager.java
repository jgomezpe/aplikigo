package nsgl.app.file;

import java.io.InputStream;

import nsgl.app.Application;
import nsgl.blob.Parse;
import nsgl.character.CharacterSequence;
import nsgl.json.JSON;
import nsgl.stream.Util;

import java.io.IOException;

public interface Manager extends Application{
	final String NAME = "Name";
	final String STORE = "store";
	final String DELETE = "del";
	final String READ = "read";
	final String FOLDER = "folder";

	boolean authorized( JSON user );
	boolean exist(String fileName);
	
	default boolean store(String fileName, InputStream is) throws IOException{
	    return store(fileName, Util.toByteArray(is));
	}
	
	boolean store(String fileName, byte[] is) throws IOException;

	default boolean store(String fileName, String isBase64) throws IOException{
	    System.out.println("Manager...."+isBase64);
	    Parse p = new Parse();
	    return store(fileName, (byte[])p.parse(new CharacterSequence(isBase64)));
	}
	
	InputStream getIS(String fileName);
	
	default byte[] read(String fileName) throws IOException{
	    return Util.toByteArray(getIS(fileName));
	}
	
	boolean delete(String fileName) throws IOException;
	String folder() throws IOException;
}