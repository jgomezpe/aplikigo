package nsgl.app;

import nsgl.generic.Thing;

public class DefaultComponent extends Thing implements Component{

	protected Application app;
	protected Component container;
	
	public DefaultComponent(String id){ super(id); }

	@Override
	public void setApp( Application app ){ this.app = app; }

	@Override
	public Application app() { return app; }
	
	@Override
	public void setContainer(Component container){ this.container = container; }

	@Override
	public Component container() { return container; }
}