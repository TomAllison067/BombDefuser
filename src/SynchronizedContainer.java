import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Container with synchronized methods to set the AtomicBoolean that monitors the battery state.
 * Required because we want to monitor the battery level in a BatteryThread and do things depending on the state of the shutdownFlag.
 * Firstly, the robot just beeps whilst still running (at low battery).
 * Secondly at CRITICALLY LOW battery, the BatteryThread sets the shutdownFlag to TRUE, which meets the criteria for takeControl in BatteryBehavior (this is why we need synchronized atomic boolean stuff!)
 * @author Tom
 *
 */
public class SynchronizedContainer {
	private AtomicBoolean shutdownFlag;
	
	public SynchronizedContainer() {
		this.shutdownFlag = new AtomicBoolean(false);
	}
	public synchronized boolean getShutdownFlag() {
		return shutdownFlag.get();
	}
	public synchronized void setShutdownFlag(boolean value) {
		shutdownFlag.set(value);
	}
}
