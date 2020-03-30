package drivearound;


import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import utils.Bomb;
import utils.MotorContainer;

public class TurnRight implements Behavior {

	private MotorContainer container;
	private EV3UltrasonicSensor sensor;
	private Bomb bomb;
	private float minDistance;
	private float maxDistance;
	
	public TurnRight(MotorContainer container, EV3UltrasonicSensor sensor, float minDistance, float maxDistance, Bomb bomb) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.bomb = bomb;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		// Added || sample[0] >= 2 * maxDistance to try and make it turn back if it loses sight of the bomb
		return !bomb.isTaskActive() && (sample[0] < minDistance || sample[0] >= 2 * maxDistance);
	}

	@Override
	public void action() {
		container.correctRight();
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
