

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class TurnRight implements Behavior {

	MotorContainer container;
	EV3UltrasonicSensor sensor;
	Bomb bomb;
	public TurnRight(MotorContainer container, EV3UltrasonicSensor sensor, Bomb bomb) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.bomb = bomb;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return !bomb.isTaskActive() && sample[0] < Variables.DISTANCE_MIN;
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
