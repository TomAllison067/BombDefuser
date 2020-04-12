package qr;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import lejos.hardware.lcd.LCD;
/**
 * Thread to connect to a phone via Bluetooth and read for QR code updates
 * @author Tom Allison (for marking purposes) - actually 99% by Dave Cohen! (see https://github.com/cyclingProfessor/LejosExamples/blob/master/MainClass.java), this just has just been adapted slightly
 *
 */
public class ConnectionThread extends Thread {
	private static String IPaddress = "10.0.1.4";
	private static int port = 1234;
	public static Socket connection = null;
	public static DataInputStream dis;
	public static DataOutputStream dos;
	private static int MAX_READ = 30;
	private static BufferedInputStream in = null;
	private static String qrMessage = ""; // What does the QR code to be seen say?
	private static int TIMEOUT_TIME = 10000; // How long we have to connect before it times out - currently 10 seconds

	/**
	 * Connect to the phone via bluetooth and continously monitor for any updates to the QR message.
	 */
	@Override
	public void run() {
		try {
			LCD.clear();
			LCD.drawString("Connecting..", 0, 0);
			
			connection = getConnection(IPaddress, port, TIMEOUT_TIME); // Get connection
			if (connection != null) {
				LCD.drawString("Connected", 0, 6);
				in = new BufferedInputStream(connection.getInputStream());
				byte[] buffer = new byte[MAX_READ];
				
				// Read the QR code
				while (true) {
					String qr = readQR(in, buffer);
					if (qr != null) {
						qrMessage = qr; // Should only update qrMessage if a QR code has actually been seen and understood.
					}
				}
			}
		} catch (IOException e) {
			LCD.clear();
			LCD.drawString(e.getMessage(), 0, 6);
			connection = null;
		}
	}

	/**
	 * Returns the string given by the QR code.
	 * @return the string.
	 */
	public String getQR() {
		return qrMessage;
	}

	/**
	 * Create a new Socket connection
	 * @param IPaddress, the IP address to connect to
	 * @param port, the port to use
	 * @param timeout, how long to try before we timeout 
	 * @return the connection
	 * @throws IOException
	 */
	public Socket getConnection(String IPaddress, int port, int timeout) throws IOException {
		SocketAddress sa = new InetSocketAddress(IPaddress, port);
		Socket conn = new Socket();
		conn.connect(sa, timeout);
		return conn;
	}

	/**
	 * Given the thread's input stream and byte[] buffer, this method reads the input (given by the camera seeing a QR code), appends it to a StringBuilder
	 * and returns the string read.
	 * @param in the inputstream to read
	 * @param buffer the byte buffer size
	 * @throws IOException
	 * @return the QR code's string
	 */
	public String readQR(BufferedInputStream in, byte[] buffer) throws IOException {
		String message = null;
		if (in.available() > 0) {
			int read = in.read(buffer, 0, MAX_READ);
			StringBuilder str = new StringBuilder();
			for (int i = 0; i < read; i++) {
				str.append((char) buffer[i]);
			}
			if (!str.toString().equals("") && str.toString() != null) {
				message = str.toString();
				return message;
			}
		}
		return message; // If everything fails, just return null...
	}
}
