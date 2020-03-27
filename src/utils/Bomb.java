package utils;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import countdown.Countdown;
import music.MusicContainer;

/**
 * Bomb class is used to simulate the bomb virtually. allowing us to create a timer and defuse order for the bomb. 
 * 
 * Bomb class is also used to control which behaviours should currently be active
 * 
 * @author harry
 *
 */

public class Bomb {
	private char[] defuseOrder = new char[4];
	private int index = 0;
	private boolean taskActive = false;
	private AtomicBoolean retreat = new AtomicBoolean(false);
	private Timer timer = new Timer();
	private MusicContainer musicContainer;
	

	/**
	 * Constructor that creates the appropriate virtual bomb based off of the received QR code
	 * 
	 * @param bombType
	 * @param musicContainer
	 */
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

	/**
	 * 
	 * Starts the timer for the bomb and schedules the tasks
	 */
    public void startBomb(){
        TimerTask countdown = new Countdown(musicContainer, retreat);
    	timer.schedule(countdown, 1000, 1000);
    }

    public char[] getDefuseOrder() {
    	return defuseOrder;
    }
    
    public synchronized char getNextColor() {
    	return defuseOrder[index];
    }
    
    /** 
     * Increments the index of the defuseOrder array 
     */
    public synchronized void increment() {
    	index++;
    }
    
    /**
     * Used as a flag to prevent other behaviours from taking over during a defuse task.
     * @return taskActive
     */
    public synchronized boolean isTaskActive() {
    	return taskActive;
    } 
    
    public synchronized void startTask() {
    	taskActive = true;
    }
    
    public synchronized void setRetreat(boolean bool) {
    	retreat.set(bool);
    }
    
    public synchronized boolean getRetreat() {
    	return retreat.get();
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
