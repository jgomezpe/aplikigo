package nsgl.store;
import java.util.Iterator;

import nsgl.generic.hashmap.HashMap;
import nsgl.generic.keymap.KeyMap;

public interface DataStore {
	static KeyMap<String,Object> entity( String name ){
		KeyMap<String,Object> entity = new HashMap<String,Object>();
		entity.set("entity", name);
		return entity;
	}
	DataQuery getQuery(String entity); 
	Iterator<KeyMap<String,Object>> query( DataQuery q ); 
	void put(KeyMap<String,Object> obj);
	
	default KeyMap<String,Object> get(String entity, String tag, Object value){
		DataQuery query = getQuery(entity);
		query.addFilter(tag, DataQuery.EQUAL, value);
		Iterator<KeyMap<String, Object>> col = query(query);
		if( col.hasNext() ) return col.next();
		return null;
	}
}