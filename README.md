# BombDefuser
### Group 30 - CS1822 ###
 Thomas Allison - 100927288 <br>
 Harry Hughes - 100927137 <br>
 Venkatesh Kannan - 100929257 <br> 
 Abitha Mahabakkalage - 100913232 <br>

### Code Structure/Packages
**Default Package** <br>
*Driver.java* - the main class to run the robot. Instatiates motors and sensors, calibrates the distance sensor, constructs the behaviours and runs the arbitrator.

<br>

**Battery**<br>
*BatteryBehavior.java* - behaviour to shut down the robot when the battery is running low.

<br>

**Countdown**<br>
*Countdown.java* - a TimerTask that represents a timer for the *Bomb*. Informs the user of how much time is left, changes background music, and sets a *retreat* flag.

<br>

**Drivearound**<br>
*MoveForward.java* - behaviour to make the robot drive forwards.<br><br>
*TurnLeft.java* - behaviour to make the robot turn left.<br><br>
*TurnRight.java* - behaviour to make the robot turn right.

<br>

**Music**<br>
*Music.java* - an enum to represent the choice of music files to play.<br><br>
*MusicPlayer.java* - the code to play music samples in the background while the robot does its' task.

<br>

**Qr**<br>
*AndroidSensor.java* - class to represent the Android phone this robot is connected to.<br><br>
*ConnectionThread.java* - Connects the robot to the phone and waits for a QR code to be read in.<br><br>
*TestQRSensor.java* - Simple test class used to test the Android phone/QR-reading functionality.

<br>

**Tasks**<br>
*ButtonPress.java* - behaviour to make the robot press its' touch sensor against the bomb.<br><br>
*DefusalComplete.java* - behaviour to shut down the robot if it successfully defuses the bomb.<br><br>
*Flipper.java* - behaviour to make the robot flip the bomb over.<br><br>
*Retreat.java* - behaviour to make the robot run away from the bomb if its' timer runs out.<br><br>
*WireCut.java* - behaviour to make the robot cut a red wire on the bomb. Not implemented, lacking hardware due to Covid-19 lockdown.

<br>

**Utils**<br>
*Bomb.java* - class to represent the bomb itself.<br><br>
*EscapeButtonBehaviour.java* - behaviour to shut the robot down if the *escape* button is pressed.<br><br>
*MotorContainer.java* - class to allow access to the motors and provide methods to drive the robot around.
