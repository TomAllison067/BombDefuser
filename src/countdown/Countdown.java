package countdown;
import java.util.TimerTask;

import lejos.hardware.lcd.LCD;
import music.Music;
import music.MusicContainer;


public class Countdown extends TimerTask{
    private int count = 0;
    private MusicContainer music = new MusicContainer();
        
    public void run(){
    	
        count++;
        LCD.clear();
        if(count <= 230){
            LCD.drawString(("You have " + (240 - count) + " seconds remaining"), 2, 2);
            if (!music.getPlaying()) {
            	music.setMusic(Music.THEME_MUSIC);
            	music.playMusic(23000);
            }
        }
        else if(count < 240 && count > 230){
            LCD.drawString("Run away!!!", 2, 2);
            if (!music.getPlaying()) {
            	music.setMusic(Music.TENSE_MUSIC);
            	music.playMusic(10000);
            }
        }
        else{
            LCD.drawString("Boom!", 2, 2);   
            music.setMusic(Music.BOOM);
            music.playMusic(2000);
            music.stopMusic();
            System.exit(0);
        }
    }
}
