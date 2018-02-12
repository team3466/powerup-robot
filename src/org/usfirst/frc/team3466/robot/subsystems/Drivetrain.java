package org.usfirst.frc.team3466.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3466.robot.OI;
import org.usfirst.frc.team3466.robot.RobotMap;
import org.usfirst.frc.team3466.robot.commands.DriveArcadeCommand;


public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static boolean InverseDriveOn = false;
    private DifferentialDrive differentialDrive;
    public static OI oi;

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveArcadeCommand());

    }

    public void initDrive() {
        oi = new OI();
        oi.stick = new Joystick(RobotMap.joystick);
        differentialDrive = new DifferentialDrive(RobotMap.leftMotor, RobotMap.rightMotor);
    }

    public void autoDrive(double speed, double direction) {
        differentialDrive.arcadeDrive(speed, direction, false);
    }

    public void teleopDrive(double Y, double X) {
        differentialDrive.arcadeDrive(Y, -X, true);
    }

    public void driveInverse(double Y, double X) {

        if (InverseDriveOn){
            X = -X;
            Y = -Y;
        }

        differentialDrive.arcadeDrive(Y, -X, true);
    }
}

