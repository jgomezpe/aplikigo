package nsgl.app.filemanager;

import java.io.File;

import nsgl.app.user.Repository;
import nsgl.json.JSON;

public class OS4User extends OS{
	protected String user_id;
	
	public OS4User(String id, Repository clients, String realPath, String aliasPath) {
 		super(id, clients, realPath, aliasPath);
	}

	public String realPath(){ return super.realPath() + user_id + '/'; }   
	
	public boolean authorized(JSON user, String object, String method) {
		if( super.authorized(user, object, method) ) {
		    user_id = user.string(Repository.ID);
		    File folder = new File(realPath());
		    if( !folder.exists() ) folder.mkdir();
		    return true;
		}
		return false;
	}
}