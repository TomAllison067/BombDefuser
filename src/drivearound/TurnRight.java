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
	
	public TurnRight(MotorContainer container, EV3UltrasonicSensor sensor, float minDistance, Bomb bomb) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.bomb = bomb;
		this.minDistance = minDistance;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		provider.fetchSample(sample, 0);
		return !bomb.isTaskActive() && (sample[0] < minDistance);
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
