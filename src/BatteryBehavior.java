
import lejos.robotics.subsumption.Behavior;
import synchronize.SynchronizedContainer;

public class BatteryBehavior implements Behavior {
	private SynchronizedContainer sync;
	
	public BatteryBehavior(SynchronizedContainer _sync) {
		this.sync = _sync;
	}
	
	public boolean takeControl() {
		return sync.getShutdownFlag();
	}
	
	public void suppress() {
		// Stop action() and go back to main behaviour - TODO (very hard)
	}
	
	public void action() {
		// Stop everything and exit the robot gracefully - TODO
	}
}
