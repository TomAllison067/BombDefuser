package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import utils.Bomb;
import utils.MotorContainer;

/**
 * Turns the robot towards the box if the distance between the robot and the box
 * is more than the maximum allowed distance, basically correcting the distance
 * between them or gradually turning the robot
 * 
 * @author venkatesh
 *
 */
public class TurnLeft implements Behavior {

	private MotorContainer container;
	private EV3UltrasonicSensor sensor;
	private Bomb bomb;
	private float maxDistance;

	public TurnLeft(MotorContainer container, EV3UltrasonicSensor sensor, float maxDistance, Bomb bomb) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.bomb = bomb;
		this.maxDistance = maxDistance;
	}

	/**
	 * Take control of the behaviour if the robot is farther than expected
	 */
	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		provider.fetchSample(sample, 0);
		return !bomb.isTaskActive() && sample[0] > maxDistance;

	}

	@Override
	public void action() {
		container.correctLeft();

	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
