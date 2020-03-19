package tasks;
import lejos.robotics.subsumption.Behavior;
import lejos.utility.Delay;
import utils.Bomb;
import utils.MotorContainer;

public class ExitArea implements Behavior{


	Bomb bomb;
	MotorContainer container;
	
	public ExitArea(MotorContainer container, Bomb bomb) {
		this.container = container;
		this.bomb = bomb;

	}
	
	@Override
	public boolean takeControl() {
		
		return bomb.isFinished();
		
	}

	@Override
	public void action() {
		

		bomb.startTask();
		
		container.turnRight(90);
	
		
		container.forward();
		Delay.msDelay(10000);
		container.stop();
		
		if(bomb.getIndex() == 3) {
			bomb.finish();
		}
		
		bomb.taskFinished();
	}

	@Override
	public void suppress() {
		// TODO Auto-generated method stub
		
	}
		
}