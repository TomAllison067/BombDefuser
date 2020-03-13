

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

	public void correctLeft() {
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
