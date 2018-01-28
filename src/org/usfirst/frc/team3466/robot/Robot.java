/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team3466.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;

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

    private static final String DEFAULT_AUTO = "Default";
    private static final String CUSTOM_AUTO = "My Auto";
    private String autoSelected;
    private SendableChooser<String> chooser = new SendableChooser<>();
    //public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
    //public static final CaptainHook captainHook = new CaptainHook();

    public static OI oi;

    double Kp = 0.1;
    float totalTime = 5;
    float currentTime = 0;
    float heading = 0;
    double loopCount = 0;

    float speedError = 0;
    float speedAdjustment = 0;
    float maxSpeedAdjustment = .01f;
    float lastSpeed = 0;
    float minimumSpeed = 0;
    float KpSpeed = .025f;
    float maxSpeed = 1f;
    float speed = 0;
    double sampleRate = 0.01;

    public static boolean InverseDriveOn = false;

    DifferentialDrive differentialDrive;

    Command autonomousCommand;
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


        oi = new OI();
        oi.stick = new Joystick(RobotMap.joystick);

        timer = new Timer();
        samplingRateTimer = new Timer();

        differentialDrive = new DifferentialDrive(RobotMap.leftMotor, RobotMap.rightMotor);

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
        RobotMap.gyro.calibrate();

        timer.reset();
        timer.start();
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
                double angle = RobotMap.gyro.getAngle();
                samplingRateTimer.reset();
                while (timer.get() < 8) {
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
                        differentialDrive.arcadeDrive(speed, 0);
                    }

                }
                differentialDrive.arcadeDrive(0, 0);
                break;
        }
    }

    /**
     * This function is called periodically during operator control.
     */
    @Override
    public void teleopPeriodic() 
    {
        double X = -oi.stick.getX();
        double Y = -oi.stick.getY();

        if (InverseDriveOn){
            X = -X;
            Y = -Y;
        }

        differentialDrive.arcadeDrive(Y, -X, true);
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
