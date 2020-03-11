package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class ForwardTest implements Behavior {

	private MotorContainer container;
	private EV3UltrasonicSensor sensor;
	
	public ForwardTest(MotorContainer container, EV3UltrasonicSensor sensor) {
		super();
		this.container = container;
		this.sensor = sensor;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return sample[0] < Driver.DISTANCE_MAX && sample[0] > Driver.DISTANCE_MIN;
		
	}

	@Override
	public void action() {
		container.stop();
		container.forward();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
