package qr;
/**
 * Represents the phone sensor. Simply creates a new thread that connects the phone via bluetooth and then continously monitors the camera for a new QR code reading.
 * @author Tom
 *
 */

public class AndroidSensor {
	private static ConnectionThread connectionThread;
	public AndroidSensor() {
		connectionThread = new ConnectionThread();
	}
	
	/*
	 * Starts the connection thread.
	 */
	public void startThread() {
		connectionThread.start();
	}
	
	/*
	 * Gets the QR reading from the connection thread - call this method whenever you want to check the latest QR code read.
	 */
	public String getMessage() {
		String message = connectionThread.getQR();
		return message;
	}
}
