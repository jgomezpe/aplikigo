package aplikigo.store;
import java.util.HashMap;
import java.util.Iterator;


public interface DataStore {
	static HashMap<String,Object> entity( String name ){
		HashMap<String,Object> entity = new HashMap<String,Object>();
		entity.put("entity", name);
		return entity;
	}
	DataQuery getQuery(String entity); 
	Iterator<HashMap<String,Object>> query( DataQuery q ); 
	void put(HashMap<String,Object> obj);
	
	default HashMap<String,Object> get(String entity, String tag, Object value){
		DataQuery query = getQuery(entity);
		query.addFilter(tag, DataQuery.EQUAL, value);
		Iterator<HashMap<String, Object>> col = query(query);
		if( col.hasNext() ) return col.next();
		return null;
	}
}