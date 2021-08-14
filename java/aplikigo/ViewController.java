package aplikigo;

import speco.object.Named;

public class ViewController extends Named implements Component{
    protected Component controller;
    protected Component view;

    public ViewController() { super(); }
	
    public ViewController(String id, Component controller, Component view) {
	super(id);
	this.controller = controller;
	this.view = view;
    }

    public Object controller() { return controller; }
    public Object view() { return view; }

    @Override
    public Component get(String component) {
	// TODO Auto-generated method stub
	return null;
    }
}