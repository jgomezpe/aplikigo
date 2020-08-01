package nsgl.gui;

public interface Render{
	void render();
	default void render( Object obj ){
		init();
		add( obj );
		render();
	}
	void add( Object obj );
	void init();
}