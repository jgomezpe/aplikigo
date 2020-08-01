package nsgl.js;

import nsgl.gui.Color;
import nsgl.gui.PaintCommand;

public class Canvas extends nsgl.gui.Canvas{
	protected Color color=null;
	protected CanvasRender render;
	
	public Canvas( CanvasRender render ){ this.render = render; }
	
	@Override
	public void command( PaintCommand json ){ try{ render.run("draw", json); }catch(Exception e) {} }

	@Override
	public void beginPath(){ command( PaintCommand.beginPath() ); }

	@Override
	public void closePath(){ command( PaintCommand.closePath() ); }

	@Override
	public void curveTo(PaintCommand c){ command(c); }

	@Override
	public void fill(){ command( PaintCommand.fill() ); }

	@Override
	public void fillStyle(PaintCommand c){ command(c); }

	@Override
	public void image(PaintCommand c){ command(c); }

	@Override
	public void lineTo(PaintCommand c){ command(c); }

	@Override
	public void moveTo(PaintCommand c){ command(c); }

	@Override
	public void quadTo(PaintCommand c){ command(c); }

	@Override
	public void stroke(){ command( PaintCommand.stroke()); }

	@Override
	public void strokeStyle(PaintCommand c){ command(c); }

	@Override
	public void text(PaintCommand c){ command(c); }
}