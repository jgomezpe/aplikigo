package nsgl.communication;

public interface Channel {
	Object send( Package pack ) throws Exception;
	Object receive( Package pack ) throws Exception;
}