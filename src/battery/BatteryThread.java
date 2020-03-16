package battery;
import lejos.hardware.Battery;
import lejos.hardware.Sound;
import lejos.utility.Delay;
import synchronize.SynchronizedContainer;

/**
 * Class to monitor the state of the battery - as required in the project spec.
 * Continuously monitors the battery voltage, and if it falls below a certain level will flash warning beeps.
 * If it falls even further, the AtomicBoolean can be used to trigger the shutdown behaviour (not yet implemented) in the arbitrator's takeControl() method
 * @author Tom
 *
 */
public class BatteryThread extends Thread {
	final static float WARNING_VOLTAGE = 6.2f;
	final static float SHUTDOWN_VOLTAGE = 6.0f;
	private float currentVoltage;
	private SynchronizedContainer sync;

	public BatteryThread(SynchronizedContainer _sync) {
		this.sync = _sync;
		currentVoltage = Battery.getVoltage();
	}
	
	@Override
	public void run() {
		while(true) {
			currentVoltage = Battery.getVoltage();
			if (currentVoltage <= WARNING_VOLTAGE) {
				Sound.beep();
				Delay.msDelay(2000);
				if (currentVoltage <= SHUTDOWN_VOLTAGE) {
					sync.setShutdownFlag(true);
				} else if (sync.getShutdownFlag()){ // Only come here if shutdown needs to be changed (stops thread repeatedly setting it to false when it is already false)
					sync.setShutdownFlag(false);
				}
			}
		}
	}
}
