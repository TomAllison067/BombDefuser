import java.util.concurrent.atomic.AtomicBoolean;

import lejos.robotics.subsumption.Behavior;

public class BatteryBehavior implements Behavior {
	private AtomicBoolean shutdown;
	
	public BatteryBehavior(AtomicBoolean _shutdown) {
		this.shutdown = _shutdown;
	}
	
	public boolean takeControl() {
		return shutdown.get();
	}
	
	public void suppress() {}
	
	public void action() {
		// Stop everything - TODO
	}
}
