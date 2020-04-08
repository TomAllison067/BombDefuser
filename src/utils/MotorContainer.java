package utils;
import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.utility.Delay;

public class MotorContainer {

	/**
	 * time in millis for 90deg -> 1380
	 * 1deg -> 15
	 */
	
	private static final long MILLISECOND_1_DEGREE_INPLACE = 15;
	static final int LEFTMOTOR_TURN_SPEED = 50;
	static final int RIGHTMOTOR_TURN_SPEED = 150;
	static final int FORWARD_SPEED = 70;
	
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
	

	/**
	 * Correcting the motors when moving around the box
	 * To be used in TurnLeft Behavior
	 */
	public void correctLeft() {
		mLeft.setSpeed(LEFTMOTOR_TURN_SPEED);
		mRight.setSpeed(RIGHTMOTOR_TURN_SPEED);
		mLeft.startSynchronization();

		mLeft.forward();

		mRight.forward();
		mLeft.endSynchronization();
	}
	
	/**
	 * Correcting the motors when moving around the box
	 * To be used in TurnRight Behavior
	 */
	public void correctRight() {
		mLeft.setSpeed(LEFTMOTOR_TURN_SPEED);
		mRight.setSpeed(RIGHTMOTOR_TURN_SPEED);
		mLeft.startSynchronization();
		
		mLeft.forward();

		mRight.forward();
		mLeft.endSynchronization();
	}
	
	/**
	 * Stop the motors
	 */
	public void stop() {
		mLeft.startSynchronization();
		mLeft.stop();
		mRight.stop();
		mLeft.endSynchronization();
	}
	
	/**
	 * Move the motors forward simultaneously at the current speed
	 */
	public void forward() {
		setSpeed(FORWARD_SPEED);
		mLeft.startSynchronization();
		mLeft.forward();
		mRight.forward();
		mLeft.endSynchronization();
	}

	/**
	 * Move the motors backwards simultaneously at the current speed
	 */
	public void backward() {
		mLeft.startSynchronization();
		mLeft.backward();
		mRight.backward();
		mLeft.endSynchronization();
	}
	
	/**
	 * 
	 * @return true if the motors are not moving and false if otherwise
	 */
	public boolean isStalled() {
		Delay.msDelay(500);
		return mLeft.isStalled() && mRight.isStalled();
	}

	/**
	 * 
	 * @return true if the motors are moving and false if otherwise
	 */
	public boolean isMoving() {
		Delay.msDelay(500);
		return mLeft.isMoving() || mRight.isMoving();
	}
	
	/**
	 * Set speed of the motors in deg/sec
	 * @param speed in deg/sec
	 */
	public void setSpeed(float speed) {
		mLeft.setSpeed(speed);
		mRight.setSpeed(speed);
	}
	
	/**
	 * Set speed of the motors in deg/sec
	 * @param speed in deg/sec
	 */
	public void setSpeed(int speed) {
		mLeft.setSpeed(speed);
		mRight.setSpeed(speed);
	}
	
	/**
	 * Turn the track chassis left at the speed of 360 deg/sec
	 * @param angle to turn in degrees
	 */
	public void turnLeft(int angle) {
		setSpeed(360);
		
		mLeft.startSynchronization();
		mLeft.backward();
		mRight.forward();
		mLeft.endSynchronization();
		
		Delay.msDelay(MILLISECOND_1_DEGREE_INPLACE * angle);
		
		stop();
	}
	
	/**
	 * Turn the track chassis left at the speed of 360 deg/sec
	 * @param angle to turn in degrees
	 */
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
