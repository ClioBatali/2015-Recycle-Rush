// RobotBuilder Version: 1.5
//
// This file was generated by RobotBuilder. It contains sections of
// code that are automatically generated and assigned by robotbuilder.
// These sections will be updated in the future when you export to
// Java from RobotBuilder. Do not put any code or make any change in
// the blocks indicating autogenerated code or it will be lost on an
// update. Deleting the comments indicating the section will prevent
// it from being updated in the future.


package org.usfirst.frc4915.MecanumDrive.commands;

import java.util.Arrays;
import java.util.List;

import org.usfirst.frc4915.MecanumDrive.Robot;
import org.usfirst.frc4915.MecanumDrive.RobotMap;

import edu.wpi.first.wpilibj.CANTalon;
import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class  MoveStraightGivenDistanceCommand extends Command {
	public static List <CANTalon> motors;
	public double inputDistance;

    public MoveStraightGivenDistanceCommand(double inputDistance) {
    	requires(Robot.mecanumDriveControls1); 
    	this.inputDistance = inputDistance;
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);

        // BEGIN AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES

    // END AUTOGENERATED CODE, SOURCE=ROBOTBUILDER ID=REQUIRES
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	 motors = Arrays.asList(RobotMap.mecanumDriveControls1LeftFront1, 
    			RobotMap.mecanumDriveControls1LeftRear2, 
    			RobotMap.mecanumDriveControls1RightFront3,
    			RobotMap.mecanumDriveControls1RightRear4 );
    }
    
    double getDistanceForMotor(CANTalon motor, long elapsed){
    	int ticksPerRevolution = 1000;
    	double circumferenceOfWheel = 6*Math.PI;
    	int inchesPerFoot = 12;
    	return motor.getSpeed()*elapsed/ticksPerRevolution*circumferenceOfWheel/inchesPerFoot;
    }
    
    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    	if (inputDistance < 0)
    		Robot.mecanumDriveControls1.driveStraight(-0.2);
    	else 
    		Robot.mecanumDriveControls1.driveStraight(0.2);
    	long elapsed = 0;
    	long startTime = System.currentTimeMillis();
    	long endTime = 0;
    	double distance = 0;
    	while(Math.abs(distance) < Math.abs(inputDistance)){
    		try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    		endTime = System.currentTimeMillis();
    		elapsed = endTime - startTime;
    		double distanceSinceElapsed = 0;
    		for(CANTalon motor : motors){
    			double distanceForMotor = getDistanceForMotor (motor, elapsed);
    			distanceSinceElapsed = Math.max(distanceForMotor, distanceSinceElapsed);
    		}
    		distance+=distanceSinceElapsed;
    		startTime=endTime;
    	}
    	end();
    	
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	Robot.mecanumDriveControls1.getRobotDrive().stopMotor(); 
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	end();
    }
}
