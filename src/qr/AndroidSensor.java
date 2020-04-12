package qr;
/**
 * Container class to represent the Android phone sensor.
 * It contains a ConnectionThread, which when started will attempt to connect via Bluetooth to the Android phone and monitor for any new QR code readings.
 * @author Tom Allison
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
	

	/**
	 * Gets the latest QR code read by the connectionThread by calling connectionThread.getQR().
	 * Use this when in other code where you've got an AndroidSensor and want to get the QR Code.
	 * @return the latest QR code string
	 */
	public String getMessage() {
		String message = connectionThread.getQR();
		return message;
	}
}
