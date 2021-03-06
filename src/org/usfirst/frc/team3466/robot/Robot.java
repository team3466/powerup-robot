/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3466.robot;

import edu.wpi.first.wpilibj.*;

import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;

import org.usfirst.frc.team3466.robot.commands.cubeController.CubeEjectCommand;
import org.usfirst.frc.team3466.robot.commands.cubeController.CubeIntakeCommand;
import org.usfirst.frc.team3466.robot.commands.cubeController.CubeLeftAdjustCommand;
import org.usfirst.frc.team3466.robot.commands.cubeController.CubeRightAdjustCommand;
import org.usfirst.frc.team3466.robot.commands.drive.DriveArcadeCommand;
import org.usfirst.frc.team3466.robot.commands.drive.DriveAutonomousCommand;
import org.usfirst.frc.team3466.robot.commands.slider.SlideBackwardCommand;
import org.usfirst.frc.team3466.robot.commands.slider.SlideForwardCommand;
import org.usfirst.frc.team3466.robot.subsystems.Climber;
import org.usfirst.frc.team3466.robot.subsystems.CubeController;
import org.usfirst.frc.team3466.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3466.robot.subsystems.Elevator;
import org.usfirst.frc.team3466.robot.subsystems.Extender;
import org.usfirst.frc.team3466.robot.subsystems.Slider;

import static org.usfirst.frc.team3466.robot.RobotMap.gyro;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
// If you rename or move this class, update the build.properties file in the project root
public class Robot extends IterativeRobot
{

    public static Climber climber = new Climber();
    public static CubeController cubeController = new CubeController();
    public static Drivetrain drivetrain = new Drivetrain();
    public static Elevator elevator = new Elevator();
    public static Extender extender = new Extender();
    public static Slider slider = new Slider();

    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private static final String AUTONOMOUS_STOPPED = "Autonomous Stopped";
    private String autoSelected;
    private SendableChooser<String> chooser = new SendableChooser<>();
    //public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    //public static final CaptainHook captainHook = new CaptainHook();

    Command autonomousCommand;
    Command teleopCommand;
    Command cubeEjectCommand; //TEST
    Command cubeIntakeCommand;
    Command cubeLeftAdjustCommand;
    Command cubeRightAdjustCommand;
    Command slideForwardCommand;
    Command slideBackwardCommand;
    CameraServer server;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit()
    {

        autonomousCommand = new DriveAutonomousCommand();
        teleopCommand = new DriveArcadeCommand();
        cubeEjectCommand = new CubeEjectCommand();
        cubeIntakeCommand = new CubeIntakeCommand();
        cubeLeftAdjustCommand = new CubeLeftAdjustCommand();
        cubeRightAdjustCommand = new CubeRightAdjustCommand();
        slideForwardCommand = new SlideForwardCommand();
        slideBackwardCommand = new SlideBackwardCommand();


        gyro.calibrate();

        //Initialize camera
        CameraServer.getInstance().startAutomaticCapture();

        chooser.addDefault("Default Auto", DEFAULT_AUTO);
        //chooser.addObject("My Auto", CUSTOM_AUTO);
        SmartDashboard.putData("Auto choices", chooser);
    }

    /**
     * This autonomous (along with the chooser code above) shows how to select
     * between different autonomous modes using the dashboard. The sendable
     * chooser code works with the Java SmartDashboard. If you prefer the
     * LabVIEW Dashboard, remove all of the chooser code and uncomment the
     * getString line to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional comparisons to
     * the switch structure below with additional strings. If using the
     * SendableChooser make sure to add them to the chooser code above as well.
     */
    @Override
    public void autonomousInit() 
    {
        gyro.reset();
        autoSelected = chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + autoSelected);

        if (autonomousCommand != null) autonomousCommand.start();

        // Example plate assignment code from WPILib manual
        // Data can be faked in DS for practice when no FMS is present
        String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
		} else {
			//Put right auto code here
		}

    }

    /**
     * This function is called periodically during autonomous.
     */
    @Override
    public void autonomousPeriodic() 
    {
        switch (autoSelected) 
        {
            case CUSTOM_AUTO:
                // Put custom auto code here
                break;
            case DEFAULT_AUTO:
            default:
                // Put default auto code here
                //System.out.println(RobotMap.gyro.getAngle());
                //gyro.getAngle();
                //totalTime = 3;
                //desiredDirection = -90;
                //drivetrain.autoDrive(0, 0);
                //autoSelected = AUTONOMOUS_STOPPED;
                Scheduler.getInstance().run();
                break;
            case AUTONOMOUS_STOPPED:
                //drivetrain.autoDrive(0, 0);
                break;
        }
    }

    /**
     * This function is calle
     * d once at the beginning of operator control.
     */
    @Override
    public void teleopInit()
    {
        if (teleopCommand != null) teleopCommand.start();
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() 
    {
        //Joystick control for cube intake.
        int currentPOVAngle = Robot.cubeController.currentPOVAngle();
        if(currentPOVAngle == 0) {
            cubeEjectCommand.start();
        }
        else if(currentPOVAngle == 90) {
            cubeRightAdjustCommand.start();
        }
        else if(currentPOVAngle == 180) {
            cubeIntakeCommand.start();
        }
        else if(currentPOVAngle == 270) {
            cubeLeftAdjustCommand.start();
        }
        else if(currentPOVAngle == -1) {
            cubeLeftAdjustCommand.cancel();
            cubeRightAdjustCommand.cancel();
            cubeIntakeCommand.cancel();
            cubeEjectCommand.cancel();
        }

        double currentThrottleValue = Robot.slider.currentThrottleValue();
        if(currentThrottleValue <= -0.5) {
            slideBackwardCommand.start();
        }
        else if(currentThrottleValue >= 0.5) {
            slideForwardCommand.start();
        }
        Scheduler.getInstance().run();
    }

    /**
     * This function is called periodically during test mode.
     */
    @Override
    public void testPeriodic() 
    {
        //LiveWindow.run();
    }
}
