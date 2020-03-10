
public class AndroidSensor {
	private static ConnectionThread connectionThread;
	public AndroidSensor() {
		connectionThread = new ConnectionThread();
	}
	
	public void startThread() {
		connectionThread.start();
	}
	
	public void killThread() {
		connectionThread.interrupt();
	}
	
	public String getMessage() {
		String message = connectionThread.getMessage();
		return message;
	}
}
