package drivearound;

import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.SampleProvider;
import lejos.robotics.subsumption.Behavior;
import utils.MotorContainer;

public class TurnCorner implements Behavior {

	MotorContainer container;
	EV3UltrasonicSensor distanceSensor;
	
	
	@Override
	public boolean takeControl() {
		float[] sample = new float[1];
		SampleProvider distance = distanceSensor.getDistanceMode();
		distance.fetchSample(sample, 0);
		
		return sample[0] > 3.0f;
	}

	@Override
	public void action() {
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
	
	

}
