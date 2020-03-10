import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

import lejos.hardware.lcd.LCD;

public class ConnectionThread extends Thread{
	private static String IPaddress = "10.0.1.4";
	private static int port = 1234;
	public static Socket connection = new Socket();
	public static DataInputStream dis;
	public static DataOutputStream dos;
	private static int MAX_READ = 30;
	private static BufferedInputStream in = null;
	private static OutputStream out = null; // Output - not currently used
	private static String message = "NONE";
	
	public void run() {
		try {
			LCD.clear();
			byte[] buffer = new byte[MAX_READ];
			SocketAddress sa = new InetSocketAddress(IPaddress, port);
			LCD.drawString("Connecting..", 0, 0);
			try {
				connection.connect(sa, 1500);
			} catch (Exception ex) {
				LCD.drawString(ex.getMessage(), 0, 6);
				connection = null;
			}
			if (connection != null) {
				in = new BufferedInputStream(connection.getInputStream());
				out = connection.getOutputStream();
			}
			LCD.drawString("Connected", 0, 6);
			
			// We are now connected and running!
			while (true) {
				if (in.available() > 0) {
					int read = in.read(buffer, 0, MAX_READ);
					StringBuilder str = new StringBuilder();
					for (int i = 0; i < read; i++) {
						str.append((char)buffer[i]);
					}
					if (!str.toString().equals("NONE") && str.toString() != null) {
						message = str.toString();
					}
				}
			}
		} catch (IOException e) {
			LCD.clear();
			LCD.drawString(e.getMessage(), 0, 6);
			connection = null;
		}
	}
	
	public String getMessage() {
		return message;
	}
}
