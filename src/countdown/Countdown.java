package countdown;
import java.util.TimerTask;

import lejos.hardware.lcd.LCD;
import music.Music;
import music.MusicContainer;


public class Countdown extends TimerTask{
    private int count = 0;
    private MusicContainer musicContainer;
    private boolean musicPlaying = false;
        
    public Countdown(MusicContainer container) {
    	this.musicContainer = container;
    }
    
    public void run(){
    	
        count++;
        LCD.clear();
        if(count <= 230){
            LCD.drawString(("You have " + (240 - count) + " seconds remaining"), 2, 2);
            if (!musicPlaying && count < 230) {
            	musicPlaying = true;
            	musicContainer.setMusic(Music.THEME_MUSIC);
            	musicContainer.playMusic(23000);
            }
            if (count == 230) {
            	musicContainer.stopMusic();
            	musicPlaying = false;
            }
        }
        else if(count < 240 && count > 230){
            LCD.drawString("Run away!!!", 2, 2);
            if (!musicPlaying && count < 240) {
            	musicPlaying = true;
            	musicContainer.setMusic(Music.TENSE_MUSIC);
            	//musicContainer.playMusic(10000);
            }
        }
        else{
        	musicContainer.stopMusic();
        	musicContainer.setMusic(Music.BOOM);
        	//musicContainer.playMusic(2000);
            LCD.drawString("Boom!", 2, 2);   
            music.setMusic(Music.BOOM);
            music.playMusic(2000);
            music.stopMusic();
            System.exit(0);
        }
    }
}
