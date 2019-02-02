package org.usfirst.frc.team1100.robot.commands;

import org.usfirst.frc.team1100.robot.subsystems.RightMotor;

import edu.wpi.first.wpilibj.command.Command;

/**
 *
 */
public class SpinRightMotorCounterclockwiseFast extends Command {

	RightMotor rmotor;
	
    public SpinRightMotorCounterclockwiseFast() {
        // Use requires() here to declare subsystem dependencies
        requires(RightMotor.getInstance());
        rmotor = RightMotor.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
    	rmotor.spinCCW();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {
    }

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
        return false;
    }

    // Called once after isFinished returns true
    protected void end() {
    	rmotor.spin(0);
    }

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {
    	rmotor.spin(0);
    }
}