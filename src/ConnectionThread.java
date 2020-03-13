import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import lejos.hardware.lcd.LCD;
/**
 * Thread to connect to bluetooth and monitor the phone sensor (currently just reads QR code continously)
 * @author Dave Cohen!! (Tom just added a tiny bit to read the QR code and tried to refactor it a little)
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
	// private static OutputStream out = null; // Output - not currently used
	private static String qrMessage;
	private static int TIMEOUT_TIME = 10000; // How long we have to connect before it times out

	/**
	 * Connect to the phone via bluetooth and continously monitor for any updates to the QR message.
	 */
	@Override
	public void run() {
		try {
			LCD.clear();
			LCD.drawString("Connecting..", 0, 0);
			connection = getConnection();
			if (connection != null) {
				in = new BufferedInputStream(connection.getInputStream());
				// out = connection.getOutputStream();
				LCD.drawString("Connected", 0, 6);
				byte[] buffer = new byte[MAX_READ];
				while (true) {
					String qr = readQR(in, buffer);
					// Should only update message if a QR code has actually been seen and understood.
					if (!qr.equals("NONE")) qrMessage = qr;
				}
			} else {
				// Need to put some graceful failure here - although may be caught by exception handling??
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
	 * Simply creates a new socket address and socket, gets the connection and returns the connection
	 * @throws IOException
	 */
	public Socket getConnection() throws IOException {
		SocketAddress sa = new InetSocketAddress(IPaddress, port);
		Socket conn = new Socket();
		conn.connect(sa, TIMEOUT_TIME);
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
			if (!str.toString().equals("NONE") && str.toString() != null) {
				message = str.toString();
				return message;
			}
		}
		return message;
	}
}
