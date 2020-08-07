package nsgl.app.file;

import java.io.InputStream;

import nsgl.app.user.User;

import java.io.IOException;

public interface Manager {
	final String NAME = "Name";
	final String STORE = "store";
	final String DELETE = "del";
	final String READ = "read";
	final String FOLDER = "folder";

	boolean authorized( User user );
	boolean exist(String fileName);
	
	boolean store(String fileName, InputStream is) throws IOException;
	InputStream read(String fileName);
	boolean delete(String fileName) throws IOException;
	String folder() throws IOException;
}