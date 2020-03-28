package countdown;
import java.util.TimerTask;
import java.util.concurrent.atomic.AtomicBoolean;

import lejos.hardware.lcd.LCD;
import music.Music;
import music.MusicPlayer;


public class Countdown extends TimerTask{
    private int count = 0;
    private AtomicBoolean retreat;
        
    public Countdown(AtomicBoolean retreat) {
    	this.retreat = retreat;
    }
    
    public void run(){
    	
        count++;
        LCD.clear();
        if(count <= 230){
            LCD.drawString(("You have " + (240 - count) + " seconds remaining"), 2, 2);
            if (count < 230) {
            	MusicPlayer.putMusicOn(Music.THEME_MUSIC);
            }
            if (count == 230) {
            	MusicPlayer.putMusicOn(null);
            }
        }
        else if(count < 240 && count > 230){
            LCD.drawString("Run away!!!", 2, 2);
            if (count < 240) {
            	MusicPlayer.putMusicOn(Music.TENSE_MUSIC);
            }
        } else if (count >= 240) {
        	MusicPlayer.putMusicOn(Music.BOOM);
        	setRetreat(true);
        }
    }
    
    public synchronized void setRetreat(boolean bool) {
    	retreat.set(bool);
    }
    
    public synchronized boolean getRetreat() {
    	return retreat.get();
    }
}
