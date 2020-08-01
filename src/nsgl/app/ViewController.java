package nsgl.app;

public class ViewController extends Component{
	protected Object controller;
	protected Object view;

	public ViewController() { super(); }
	
	public ViewController(String id, Object controller, Object view) {
		super(id);
		this.controller = controller;
		this.view = view;
	}

	public Object controller() { return controller; }
	public Object view() { return view; }
}