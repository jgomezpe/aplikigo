package nsgl.app;

public class VCComponent extends DefaultComponent{
	protected Component controller;
	protected Component view;

	public VCComponent(String id, Component controller, Component view) {
		super(id);
		this.controller = controller;
		this.controller.setContainer(this);
		this.view = view;
		this.view.setContainer(this);
	}

	@Override
	public void setApp( Application app ){ 
		super.setApp(app);
		controller.setApp(app);
		view.setApp(app);
	}
	
	public Component controller() { return controller; }
	public Component view() { return view; }
}