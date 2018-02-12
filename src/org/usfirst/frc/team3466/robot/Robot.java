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
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

import org.usfirst.frc.team3466.robot.commands.DriveArcadeCommand;
import org.usfirst.frc.team3466.robot.subsystems.AutonomousDrive;
import org.usfirst.frc.team3466.robot.subsystems.Climber;
import org.usfirst.frc.team3466.robot.subsystems.CubeController;
import org.usfirst.frc.team3466.robot.subsystems.Drivetrain;
import org.usfirst.frc.team3466.robot.subsystems.Elevator;
import org.usfirst.frc.team3466.robot.subsystems.Extender;

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

    public static AutonomousDrive autonomousDrive = new AutonomousDrive();
    public static Climber climber = new Climber();
    public static CubeController cubeController = new CubeController();
    public static Drivetrain drivetrain = new Drivetrain();
    public static Elevator elevator = new Elevator();
    public static Extender extender = new Extender();

    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private static final String AUTONOMOUS_STOPPED = "Autonomous Stopped";
    private String autoSelected;
    private SendableChooser<String> chooser = new SendableChooser<>();
    //public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    //public static final CaptainHook captainHook = new CaptainHook();




    float totalTime = 3.85f;
    float speedError = 0;
    float speedAdjustment = 0;
    float maxSpeedAdjustment = .01f;
    float lastSpeed = 0;
    float minimumSpeed = 0;
    float KpSpeed = .025f;
    float maxSpeed = 1f;
    float speed = 0;
    double sampleRate = 0.01;

    float directionError = 0;
    float directionAdjustment = 0;
    float maxDirectionAdjustment = .1f;
    float lastDirection = 0;
    float minimumDirection = -1f;
    float KpDirection = .1f;
    float maxDirection = 1f;
    float direction = 0;
    float desiredDirection = 0;

    float newAngle = -51f;
    float turnError = 0;
    float turnAdjustment = 0;
    float maxTurnAdjustment = .01f;
    float lastTurn = 0;
    float minimumTurn = 0;
    float KpTurn = .025f;
    float maxTurn = 1f;
    float turn = 0;

    Command autonomousCommand;
    Command teleopCommand;
    CameraServer server;
    Timer timer;
    Timer samplingRateTimer;

    /**
     * This function is run when the robot is first started up and should be
     * used for any initialization code.
     */
    @Override
    public void robotInit()
    {

        teleopCommand = new DriveArcadeCommand();


        gyro.calibrate();

        timer = new Timer();
        samplingRateTimer = new Timer();

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

        timer.reset();
        timer.start();
        gyro.reset();
        samplingRateTimer.reset();
        samplingRateTimer.start();
        autoSelected = chooser.getSelected();
        // autoSelected = SmartDashboard.getString("Auto Selector",
        // defaultAuto);
        System.out.println("Auto selected: " + autoSelected);

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
                samplingRateTimer.reset();
                while (timer.get() < totalTime) {
                    //differentialDrive.arcadeDrive(0, (heading-angle)*Kp); //Drive forward .8 speed.
                    if (samplingRateTimer.get() > sampleRate) {
                        samplingRateTimer.reset();


                        speedError = (totalTime-(float)timer.get());

                        speedAdjustment = speedError*KpSpeed;
                        if (speedAdjustment > maxSpeedAdjustment){
                            speedAdjustment = maxSpeedAdjustment;
                        }
                        if (speedAdjustment < -maxSpeedAdjustment){
                            speedAdjustment = -maxSpeedAdjustment;
                        }

                        speed = lastSpeed + speedAdjustment;
                        if (speed > maxSpeed){
                            speed = maxSpeed;
                        }
                        if (speed < minimumSpeed){
                            speed = minimumSpeed;
                        }

                        lastSpeed = speed;

                        directionError = (desiredDirection - (float)gyro.getAngle());
                        //directionError = (-desiredDirection + (float)gyro.getAngle()); New Robot

                        directionAdjustment = directionError*KpDirection;
                     /*   if (directionAdjustment > maxDirectionAdjustment){
                            directionAdjustment = maxDirectionAdjustment;
                        }
                        if (directionAdjustment < -maxDirectionAdjustment){
                            directionAdjustment = -maxDirectionAdjustment;
                        }
                    */
                        direction = lastDirection + directionAdjustment;
                        if (direction > maxDirection){
                            direction = maxDirection;
                        }
                        if (direction < minimumDirection){
                            direction = minimumDirection;
                        }

                       //lastDirection = direction;
                        //System.out.println(direction);
                        System.out.println(gyro.getAngle());

                        drivetrain.autoDrive(speed, direction);
                    }

                }
                while (newAngle < gyro.getAngle()) {
                    if (samplingRateTimer.get() > sampleRate) {
                        samplingRateTimer.reset();


                        turnError = (-newAngle + (float)gyro.getAngle());

                        turnAdjustment = turnError * KpTurn;
                        if (turnAdjustment > maxTurnAdjustment) {
                            turnAdjustment = maxTurnAdjustment;
                        }
                        if (turnAdjustment < -maxTurnAdjustment) {
                            turnAdjustment = -maxTurnAdjustment;
                        }

                        turn = lastTurn + turnAdjustment;
                        if (turn > maxTurn) {
                            turn = maxTurn;
                        }
                        if (turn < minimumTurn) {
                            turn = minimumTurn;
                        }

                        lastTurn = turn;
                        drivetrain.autoDrive(turn, -1);
                    }

                }
                drivetrain.autoDrive(0, 0);
                timer.reset();
                while (timer.get() <.2);
                totalTime = 3;
                desiredDirection = -90;
                while (timer.get() < totalTime) {
                    //differentialDrive.arcadeDrive(0, (heading-angle)*Kp); //Drive forward .8 speed.
                    if (samplingRateTimer.get() > sampleRate) {
                        samplingRateTimer.reset();


                        speedError = (totalTime-(float)timer.get());

                        speedAdjustment = speedError*KpSpeed;
                        if (speedAdjustment > maxSpeedAdjustment){
                            speedAdjustment = maxSpeedAdjustment;
                        }
                        if (speedAdjustment < -maxSpeedAdjustment){
                            speedAdjustment = -maxSpeedAdjustment;
                        }

                        speed = lastSpeed + speedAdjustment;
                        if (speed > maxSpeed){
                            speed = maxSpeed;
                        }
                        if (speed < minimumSpeed){
                            speed = minimumSpeed;
                        }

                        lastSpeed = speed;

                        directionError = (desiredDirection - (float)gyro.getAngle());
                        //directionError = (-desiredDirection + (float)gyro.getAngle()); New Robot

                        directionAdjustment = directionError*KpDirection;
                     /*   if (directionAdjustment > maxDirectionAdjustment){
                            directionAdjustment = maxDirectionAdjustment;
                        }
                        if (directionAdjustment < -maxDirectionAdjustment){
                            directionAdjustment = -maxDirectionAdjustment;
                        }
                    */
                        direction = lastDirection + directionAdjustment;
                        if (direction > maxDirection){
                            direction = maxDirection;
                        }
                        if (direction < minimumDirection){
                            direction = minimumDirection;
                        }

                        //lastDirection = direction;
                        //System.out.println(direction);
                        //System.out.println(gyro.getAngle());

                        drivetrain.autoDrive(speed, direction);
                    }

                }
                drivetrain.autoDrive(0, 0);
                autoSelected = AUTONOMOUS_STOPPED;
                break;
            case AUTONOMOUS_STOPPED:
                drivetrain.autoDrive(0, 0);
                break;
        }
    }

    /**
     * This function is called once at the beginning of operator control.
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
