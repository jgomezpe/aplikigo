package kompari;

public interface Searchable {
    
    Comparator comparator();
    
    /**
     * Determines if the given object belongs to the collection
     * @param data Data object to be located
     * @return <i>true</i>If the object belongs to the collection, <i>false</i> otherwise
     */
    default boolean contains( Object data ) { return find(data)!=null; } 
    
    Object find(Object data);
}