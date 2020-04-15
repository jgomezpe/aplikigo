package nsgl.store;

public interface DataQuery {
	final static int EQUAL = 0;
	final static int GREATER_THAN = 1; 
	final static int GREATER_THAN_OR_EQUAL = 2; 
	final static int IN = 3;
	final static int LESS_THAN = 4; 
	final static int LESS_THAN_OR_EQUAL = 5; 
	final static int NOT_EQUAL  = 6;
	
	void addFilter( String attribute, int OPERATOR, Object value );
}
