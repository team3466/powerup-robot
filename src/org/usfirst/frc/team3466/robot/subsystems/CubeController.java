package org.usfirst.frc.team3466.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3466.robot.OI;
import org.usfirst.frc.team3466.robot.RobotMap;

public class CubeController extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Joystick stick = new Joystick(RobotMap.joystick);
    private DifferentialDrive differentialDrive;

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

    boolean alreadyExecuted = false;
    public void initDrive() {
        if (!alreadyExecuted) {
            differentialDrive = new DifferentialDrive(RobotMap.leftArm, RobotMap.rightArm);
            alreadyExecuted = true;
        }

    }

    public int currentPOVAngle() {
        int povAngle = stick.getPOV();
        return povAngle;
    }

    public void cubeIntake() {
        differentialDrive.arcadeDrive(0.85, 0);
    }

    public void cubeEject() {
        differentialDrive.arcadeDrive(-0.85, 0);
    }

    public void cubeLeftAdjust() {
        differentialDrive.arcadeDrive(1, 0.25);
    }

    public void cubeRightAdjust() {
        differentialDrive.arcadeDrive(1, -0.25);
    }

    public void cubeStop() {
        differentialDrive.arcadeDrive(0, 0);
    }
}

