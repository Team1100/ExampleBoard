/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team1100.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team1100.robot.commands.Beat;
import org.usfirst.frc.team1100.robot.commands.Beat2;
import org.usfirst.frc.team1100.robot.commands.Beat3;
import org.usfirst.frc.team1100.robot.commands.ChangeHeading;
import org.usfirst.frc.team1100.robot.commands.CloseValve;
import org.usfirst.frc.team1100.robot.commands.DefaultLightSwitch;
import org.usfirst.frc.team1100.robot.commands.ExampleCommand;
import org.usfirst.frc.team1100.robot.commands.OpenValve;
import org.usfirst.frc.team1100.robot.commands.SpinLeftMotorClockwiseFast;
import org.usfirst.frc.team1100.robot.commands.SpinLeftMotorCounterclockwiseFast;
import org.usfirst.frc.team1100.robot.commands.SpinRightMotorClockwiseFast;
import org.usfirst.frc.team1100.robot.commands.SpinRightMotorCounterclockwiseFast;
import org.usfirst.frc.team1100.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team1100.robot.subsystems.LeftMotor;
import org.usfirst.frc.team1100.robot.subsystems.LightSwitch;
import org.usfirst.frc.team1100.robot.subsystems.Navigation;
import org.usfirst.frc.team1100.robot.subsystems.Piston;
import org.usfirst.frc.team1100.robot.subsystems.RightMotor;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.  
 */
public class Robot extends TimedRobot {
	public static ExampleSubsystem m_subsystem = new ExampleSubsystem();
	public static OI m_oi;
	public NetworkTable table;
	LightSwitch lswitch;

	Command m_autonomousCommand;
	SendableChooser<Command> m_chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_oi = OI.getInstance();
		
		LeftMotor lmotor = LeftMotor.getInstance();
		RightMotor rmotor = RightMotor.getInstance();
		Piston piston = Piston.getInstance();
		lswitch = LightSwitch.getInstance();
		
		SmartDashboard.putData("SpinLeftMCFast", new SpinLeftMotorClockwiseFast());
		SmartDashboard.putData("SpinRightMCFast", new SpinRightMotorClockwiseFast());
		SmartDashboard.putData("SpinLeftMCCFast", new SpinLeftMotorCounterclockwiseFast());
		SmartDashboard.putData("SpinRightMCCFast", new SpinRightMotorCounterclockwiseFast());
		SmartDashboard.putData("OpenValve", new OpenValve());
		SmartDashboard.putData("CloseValve", new CloseValve());
		SmartDashboard.putData("DefaultLightSwitch", new DefaultLightSwitch());
		SmartDashboard.putData("Left Encoder", lmotor.getEncoder());
		SmartDashboard.putData("Right Encoder", rmotor.getEncoder());
		SmartDashboard.putData("Beat", new Beat());
		SmartDashboard.putData("Beat2", new Beat2());
		SmartDashboard.putData("Beat3", new Beat3());
		SmartDashboard.putData("ChangeHeading", new ChangeHeading(0,0.5));
		SmartDashboard.putData("Navigation", Navigation.getInstance().getNavX());
		SmartDashboard.putNumber("num_beats", 5);
		SmartDashboard.putNumber("yaw", Navigation.getInstance().getNavX().getYaw());
		SmartDashboard.putNumber("l_motor_speed", 0.5);

		table = NetworkTableInstance.getDefault().getTable("limelight");
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		m_autonomousCommand = m_chooser.getSelected();

		/*
		 * String autoSelected = SmartDashboard.getString("Auto Selector",
		 * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
		 * = new MyAutoCommand(); break; case "Default Auto": default:
		 * autonomousCommand = new ExampleCommand(); break; }
		 */

		// schedule the autonomous command (example)
		if (m_autonomousCommand != null) {
			m_autonomousCommand.start();
		}
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Scheduler.getInstance().run();
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (m_autonomousCommand != null) {
			m_autonomousCommand.cancel();
		}
		System.out.println("Entering Teleop.");
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		// Network Tables for vision processing
		NetworkTableEntry tx = table.getEntry("tx");
		NetworkTableEntry ty = table.getEntry("ty");
		NetworkTableEntry ta = table.getEntry("ta");
		SmartDashboard.putNumber("Tx", tx.getDouble(0));
		SmartDashboard.putNumber("Ty", ty.getDouble(0));
		SmartDashboard.putNumber("Ta", ta.getDouble(0));
		SmartDashboard.putBoolean("Lswitch", lswitch.get());
		SmartDashboard.putNumber("yaw", Navigation.getInstance().getNavX().getYaw());
		Scheduler.getInstance().run();
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
