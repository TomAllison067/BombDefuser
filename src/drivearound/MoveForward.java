package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import utils.Bomb;
import utils.MotorContainer;

/**
 * The Behaviour that makes the robot go forward when it's between the maximum
 * and minimum distance from the box
 * 
 * @author venkatesh
 *
 */
public class MoveForward implements Behavior {

	private MotorContainer container;
	private EV3UltrasonicSensor sensor;
	private Bomb bomb;
	private float minDistance;
	private float maxDistance;

	public MoveForward(MotorContainer container, EV3UltrasonicSensor sensor, float minDistance, float maxDistance,
			Bomb bomb) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.bomb = bomb;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;

	}

	/**
	 * Takes control of the behaviour if the condition is satisfied
	 */
	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];

		provider.fetchSample(sample, 0);

		return !bomb.isTaskActive() && sample[0] <= maxDistance && sample[0] >= minDistance;

	}

	@Override
	public void action() {
		container.forward();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
