package utils;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.utility.Delay;

public class MotorContainer {

	/**
	 * time in millis for 90deg -> 1380
	 * 1deg -> 15
	 */
	
	private static final long MILLISECOND_1_DEGREE_INPLACE = 15;
	
	private BaseRegulatedMotor mLeft;
	private BaseRegulatedMotor mRight;
	
	public MotorContainer(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		
	}

	public BaseRegulatedMotor getmLeft() {
		return mLeft;
	}

	public BaseRegulatedMotor getmRight() {
		return mRight;
	}

	public void correctLeft() {
//		this.mLeft.setSpeed((distance < Float.MAX_VALUE ? distance / 3 : 0.05f) * 200);
		this.mLeft.setSpeed(70);
		this.mRight.setSpeed(200);
		mLeft.startSynchronization();

		//minor Wheel
		mLeft.forward();

		//major Wheel
		mRight.forward();
		mLeft.endSynchronization();
	}
	
	public void correctRight() {
		this.mLeft.setSpeed(200);
//		this.mRight.setSpeed((distance < Float.MAX_VALUE ? distance / 3 : 0.05f) * 200);
		this.mRight.setSpeed(70);
		mLeft.startSynchronization();
		
		//major Wheel
		mLeft.forward();

		// minor Wheel
		mRight.forward();
		mLeft.endSynchronization();
	}
	
	public void stop() {
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
	}
	
	public void forward() {
		this.mLeft.setSpeed(180);
		this.mRight.setSpeed(180);
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
	}
	
	public boolean isStalled() {
		Delay.msDelay(500);
		return mLeft.isStalled() && mRight.isStalled();
	}

	public boolean isMoving() {
		Delay.msDelay(500);
		return mLeft.isMoving() || mRight.isMoving();
	}
	
	public void setSpeed(float speed) {
		mLeft.setSpeed(speed);
		mRight.setSpeed(speed);
	}
	

	public void setSpeed(int speed) {
		mLeft.setSpeed(speed);
		mRight.setSpeed(speed);
	}
	
	public void turnLeft(int angle) {
		setSpeed(360);
		
		mLeft.startSynchronization();
		mLeft.backward();
		mRight.forward();
		mLeft.endSynchronization();
		
		Delay.msDelay(MILLISECOND_1_DEGREE_INPLACE * angle);
		
		stop();
	}
	

	public void turnRight(int angle) {
		setSpeed(360);
		
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.backward();
		mLeft.endSynchronization();
		
		Delay.msDelay(MILLISECOND_1_DEGREE_INPLACE * angle);
		
		stop();
	}

}