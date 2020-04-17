package nsgl.gui.paint;

import nsgl.gui.Canvas;

public interface Drawable {
	PaintCommand draw();
	default void draw(Canvas canvas){
		canvas.command(draw());
	}
}