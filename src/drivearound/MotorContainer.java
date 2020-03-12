package drivearound;

import lejos.hardware.motor.BaseRegulatedMotor;
import lejos.utility.Delay;

public class MotorContainer {
	
	private BaseRegulatedMotor mLeft;
	private BaseRegulatedMotor mRight;
	
	public MotorContainer(BaseRegulatedMotor mLeft, BaseRegulatedMotor mRight) {
		this.mLeft = mLeft;
		this.mRight = mRight;
		
		this.mLeft.setSpeed(270);
		this.mRight.setSpeed(270);
		
	}

	public BaseRegulatedMotor getmLeft() {
		return mLeft;
	}

	public BaseRegulatedMotor getmRight() {
		return mRight;
	}

	public void correctLeft(float distance, float offset) {
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
	
	public void correctRight(float distance, float offset) {
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

}
