package nsgl.gui;

public interface Drawable {
	PaintCommand draw();
	default void draw(Canvas canvas){
		canvas.command(draw());
	}
}