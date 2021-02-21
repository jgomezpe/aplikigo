package speco.list;

/**
 * <p>Title: Queue</p>
 *
 * <p>Description: A queue </p>
 *
 * <p>Copyright: Copyright (c) 2009</p>
 *
 * <p>Company: Kunsamu</p>
 *
 * @author Jonatan Gomez
 * @version 1.0
 */
public class Queue<T> extends List<T>{
	public Queue() {}

	@Override
	public boolean del( T data ){ return del(); }

	public boolean del(){
		if( head!=null ){
			head = head.next;
			size--;
			if (head == null) last = null;
			return true;
		}
		return false;
	}
	
	public T peek() {
		T t = head.data;
		del();
		return t;
	}
}