package tasks;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.hardware.sensor.EV3UltrasonicSensor;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import music.MusicContainer;
import utils.Bomb;
import utils.MotorContainer;

/**
 * This behaviour is assumed if the bomb's countdown timer starts to run low.
 * The robot turns and runs away from the bomb before it "explodes"
 * @author Tom
 *
 */
public class Retreat implements Behavior{
	private MotorContainer motorContainer;
	private MusicContainer musicContainer;
	private Bomb bomb;
	private EV3UltrasonicSensor distanceSensor;
	private EV3ColorSensor colorSensor;
	
	public Retreat(MotorContainer motorContainer, MusicContainer musicContainer, Bomb bomb, EV3UltrasonicSensor distanceSensor, EV3ColorSensor colorSensor) {
		this.motorContainer = motorContainer;
		this.musicContainer = musicContainer;
		this.bomb = bomb;
		this.distanceSensor = distanceSensor;
		this.colorSensor = colorSensor;
	}
	
	
	@Override
	public boolean takeControl() {
		return bomb.getRetreat();
	}

	@Override
	public void action() {
		// Run from the bomb
		motorContainer.stop();
		motorContainer.turnLeft(180);
		motorContainer.forward();
		Delay.msDelay(5000);
		
		// Exit program
		musicContainer.stopMusic();
		distanceSensor.close();
		colorSensor.close();
		musicContainer.exitSound();
		System.exit(0);
		
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}
