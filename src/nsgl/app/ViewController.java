package nsgl.app;

public class ViewController implements Component{
	protected Object controller;
	protected Object view;

	public ViewController() { super(); }
	
	public ViewController(String id, Object controller, Object view) {
		this.controller = controller;
		this.view = view;
	}

	public Object controller() { return controller; }
	public Object view() { return view; }
}