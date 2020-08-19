package nsgl.app.remote;

import nsgl.app.net.Channel;

public class Editor extends Component implements nsgl.gui.Editor{
	public Editor(String id, Channel client){ super(id, client); }

	@Override
	public void highlight(int row){ try{ run("highlight",row); }catch(Exception e) {} }

	@Override
	public void locate(int row, int column){ try{ run("locateCursor",row,column); }catch(Exception e) {} }

	@Override
	public void setText(String txt){ try{ run("setText",txt); }catch(Exception e) {} }

	@SuppressWarnings("static-access")
	@Override
	public String getText() {
	    txt = null;
	    try{ run("getText"); }catch(Exception e) {}
	    while( txt == null ) {
		try{ Thread.currentThread().sleep(50); }catch (InterruptedException e) { e.printStackTrace(); }
	    }
	    return txt;
	}

	@SuppressWarnings("static-access")
	@Override
	public int[] getLocation() {
	    loc = null;
	    try{ run("getLocation"); }catch(Exception e) {}
	    while( loc == null ) {
		try{ Thread.currentThread().sleep(50); }catch (InterruptedException e) { e.printStackTrace(); }
	    }
	    return loc;
	}
	
	protected String txt;
	protected int[] loc; 
	
	protected Object updateText( String txt ) {
	    this.txt = txt;
	    return null;
	}
	
	protected Object updateLocation( int row, int column ) {
	    loc = new int[] {row, column};
	    return null;
	}
}