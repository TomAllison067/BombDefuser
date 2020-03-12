package drivearound;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;

public class ForwardTest implements Behavior {

	private MotorContainer container;
	private EV3UltrasonicSensor sensor;
	private float minDistance;
	private float maxDistance;
	
	public ForwardTest(MotorContainer container, EV3UltrasonicSensor sensor, float minDistance, float maxDistance) {
		super();
		this.container = container;
		this.sensor = sensor;
		this.minDistance = minDistance;
		this.maxDistance = maxDistance;
	}

	@Override
	public boolean takeControl() {
		SampleProvider provider = sensor.getDistanceMode();
		float[] sample = new float[1];
		
		provider.fetchSample(sample, 0);
		
		return sample[0] < maxDistance && sample[0] > minDistance;
		
	}

	@Override
	public void action() {
		BaseRegulatedMotor mLeft = container.getmLeft();
		BaseRegulatedMotor mRight = container.getmRight();
		if (mLeft != mRight) {
			container.stop();
			container.forward();	
		}
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub

	}

}
