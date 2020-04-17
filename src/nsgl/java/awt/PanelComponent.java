package nsgl.java.awt;

import javax.swing.JPanel;

import nsgl.app.Application;
import nsgl.app.Component;

public class PanelComponent extends JPanel implements Component{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7478815705666938415L;
	protected String id; 
	protected Component container;
	protected Application app;
	
	public PanelComponent() {}
	
	public PanelComponent( String id ) { this.id = id; }

	@Override
	public void setId(String id) { this.id = id; }
	
	@Override
	public String id(){ return id; }

	@Override
	public Component container() { return container; }

	@Override
	public void setContainer(Component container) { this.container = container; }

	@Override
	public Application app() { return app; }

	@Override
	public void setApp(Application app) { this.app = app; }		
}
