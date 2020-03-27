package tasks;
import lejos.robotics.subsumption.Behavior;
import music.MusicContainer;
import utils.Bomb;
import utils.MotorContainer;

public class Retreat implements Behavior{
	private MotorContainer motorContainer;
	private MusicContainer musicContainer;
	private Bomb bomb;
	
	public Retreat(MotorContainer motorContainer, MusicContainer musicContainer, Bomb bomb) {
		this.motorContainer = motorContainer;
		this.musicContainer = musicContainer;
		this.bomb = bomb;
	}
	
	
	@Override
	public boolean takeControl() {
		return bomb.getRetreat();
	}

	@Override
	public void action() {
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
