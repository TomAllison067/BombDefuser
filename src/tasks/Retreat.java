package tasks;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;
import utils.MotorContainer;

public class Retreat implements Behavior{
	MotorContainer motorContainer;
	MusicContainer musicContainer;
	
	public Retreat(MotorContainer motorContainer, MusicContainer musicContainer) {
		this.motorContainer = motorContainer;
		this.musicContainer = musicContainer;
	}
	
	
	@Override
	public boolean takeControl() {
		// When countdown is low -- how?
	}

	@Override
	public void action() {
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
