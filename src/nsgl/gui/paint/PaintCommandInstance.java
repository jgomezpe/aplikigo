package nsgl.gui.paint;

import nsgl.json.JSON;
import nsgl.json.JSON2Instance;

public class PaintCommandInstance implements JSON2Instance<PaintCommand>{
	public PaintCommandInstance() {}

	@Override
	public PaintCommand load(JSON json) { return new PaintCommand( json ); }

	@Override
	public JSON store(PaintCommand command){ return command; }
}