package aplikigo.remote.gui;

import aplikigo.remote.Component;
import aplikigo.server.JXONBaseServer;

public class Editor extends Component implements aplikigo.gui.Editor{
    public Editor(String id, JXONBaseServer server){ super(id, server); }

    @Override
    public void highlight(int row){ try{ run(id(), "highlight", row); }catch(Exception e) {} }

    @Override
    public void locate(int row, int column){ try{ run(id(), "locateCursor", row, column); }catch(Exception e) {} }

    @Override
    public void setText(String txt){ try{ run(id(), "setText", txt); }catch(Exception e) {} }

    @SuppressWarnings("static-access")
    @Override
    public String getText() {
	txt = null;
	try{ run(id(), "getText"); }catch(Exception e) {}
	while( txt == null ) {
	    try{ Thread.currentThread().sleep(50); }catch (InterruptedException e) { e.printStackTrace(); }
	}
	return txt;
    }

    @SuppressWarnings("static-access")
    @Override
    public int[] getLocation() {
	loc = null;
	try{ run(id(), "getLocation"); }catch(Exception e) {}
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