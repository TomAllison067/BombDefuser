import java.util.TimerTask;
import lejos.hardware.lcd.*;

public class Countdown extends TimerTask{
    private int count = 0;
    public void run(){
    	
        count ++;
        LCD.clear();
        if(count <= 230){
            LCD.drawString(("You have " + (240 - count) + " seconds remaining"), 2, 2);
        }
        else if(count < 240 && count > 230){
            LCD.drawString("Run away!!!", 2, 2);
        }
        else{
            LCD.drawString("Boom!", 2, 2);   
        }
    }
}
