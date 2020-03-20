package utils;
import java.util.Timer;
import java.util.TimerTask;

import countdown.Countdown;


public class Bomb {
	private char[] defuseOrder = new char[3];
	private int index = 0;
	private boolean taskActive = false;
	
	Timer timer = new Timer();
    TimerTask countdown = new Countdown();
	
	public Bomb(String bombType) {

		if(bombType.equals("QR: 1")) {
			defuseOrder[0] = 'R';
			defuseOrder[1] = 'G';
			defuseOrder[2] = 'B';
		}
		else if(bombType.equals("QR: 2")) {
			defuseOrder[0] = 'G';
			defuseOrder[1] = 'R';
			defuseOrder[2] = 'B';
		}
		else if(bombType.equals("QR: 3")) {
			defuseOrder[0] = 'B';
			defuseOrder[1] = 'G';
			defuseOrder[2] = 'R';
		}
    }
    
     public void bombDefused(){
         timer.cancel();
         System.out.println("Bomb Defused!!!");
     }

    public void startBomb(){
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
