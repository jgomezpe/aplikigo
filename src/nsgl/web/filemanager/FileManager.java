package nsgl.web.filemanager;

import java.io.InputStream;
import java.io.IOException;

import nsgl.web.user.User;

public interface FileManager {
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