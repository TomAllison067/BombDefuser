package utils;
import java.util.Timer;
import java.util.TimerTask;

import countdown.Countdown;
import music.MusicContainer;


public class Bomb {
	private char[] defuseOrder = new char[4];
	private int index = 0;
	private boolean taskActive = false;
	private Timer timer = new Timer();
	private MusicContainer musicContainer;
	

	public Bomb(String bombType, MusicContainer musicContainer) {
		this.musicContainer = musicContainer;
		if(bombType.equals("QR: 1")) {
			defuseOrder[0] = 'R';
			defuseOrder[1] = 'G';
			defuseOrder[2] = 'B';
			defuseOrder[3] = 'F';
		}
		else if(bombType.equals("QR: 2")) {
			defuseOrder[0] = 'G';
			defuseOrder[1] = 'R';
			defuseOrder[2] = 'B';
			defuseOrder[3] = 'F';
		}
		else if(bombType.equals("QR: 3")) {
			defuseOrder[0] = 'B';
			defuseOrder[1] = 'G';
			defuseOrder[2] = 'R';
			defuseOrder[3] = 'F';
		}
    }

	/** NOT NEEDED - we have a behavior for this now **/
//     public void bombDefused(){
//         timer.cancel();
//         System.out.println("Bomb Defused!!!");
//     }

    public void startBomb(){
        TimerTask countdown = new Countdown(musicContainer);
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
    
    /**
     * Stops the countdown from running, eg if the bomb is defused.
     * Called by the DefusalComplete behavior.
     * @author Tom Allison
     */
    public void stopCountdown() {
    	timer.cancel();
    }
}
