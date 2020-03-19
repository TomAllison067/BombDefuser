package utils;
import java.util.Timer;
import java.util.TimerTask;

import countdown.Countdown;


public class Bomb {
	private char[] defuseOrder = new char[4];
	private int index = 0;
	private boolean taskActive = false;
	
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
    
    public synchronized char getNextColor() {
    	return defuseOrder[index];
    }
    public synchronized void increment() {
    	index++;
    }
    
    public synchronized boolean isTaskActive() {
    	return taskActive;
    } 
    
    public synchronized void startTask() {
    	taskActive = true;
    }
    
    public void taskFinished() {
    	taskActive = false;
    }
}
