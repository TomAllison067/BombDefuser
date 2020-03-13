import java.util.Timer;
import java.util.TimerTask;


public class Bomb {
	private char[] defuseOrder = new char[3];
	private int index = 0;
	private boolean taskActive = false;
	
	public Bomb(int bombType) {

		if(bombType == 1) {
			defuseOrder[0] = 'R';
			defuseOrder[1] = 'G';
			defuseOrder[2] = 'B';
		}
		else if(bombType == 2) {
			defuseOrder[0] = 'G';
			defuseOrder[1] = 'R';
			defuseOrder[2] = 'B';
		}
		else if(bombType == 3) {
			defuseOrder[0] = 'B';
			defuseOrder[1] = 'G';
			defuseOrder[2] = 'R';
		}
		else {
			// bad bar code read
		}
    }
    
    // public String bombDefused(Timer timer){
    //     timer.cancel();
    //     System.out.println("Bomb Defused!!!");
    // }

    public void startBomb(){
        Timer timer = new Timer();
        TimerTask countdown = new Countdown();
        timer.schedule(countdown, 1000, 1000);
    }

    public char[] getDefuseOrder() {
    	return defuseOrder;
    }
    
    public char getNextColor() {
    	return defuseOrder[index];
    }
    public void increment() {
    	index++;
    }
    
    public boolean isTaskActive() {
    	return taskActive;
    }
    
    public void startTask() {
    	taskActive = true;
    }
    
    public void taskFinished() {
    	taskActive = false;
    }
}
