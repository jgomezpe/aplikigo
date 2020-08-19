package nsgl.app.user;

import nsgl.json.JSON;

public class Manager extends Client{
    protected Repository client;
    public Manager(String id, Repository admin, Repository client) {
	super(id, true, admin);
	this.client = client;
    }
   
    public Manager(String id, Repository repository) {
	this(id,repository,repository);
    }

    public boolean remove( String id ) { return client.remove(id); }
    
    public boolean update(String old, JSON user) { 
	return repository.update(old, user) || client.update(old, user);
    }    
}