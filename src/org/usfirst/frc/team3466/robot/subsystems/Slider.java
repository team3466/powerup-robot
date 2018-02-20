package org.usfirst.frc.team3466.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3466.robot.RobotMap;

public class Slider extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Joystick stick = new Joystick(RobotMap.joystick);
    DoubleSolenoid slider = new DoubleSolenoid(0, 1);


    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void slideInit() {
        slider.set(DoubleSolenoid.Value.kReverse);
        slider.set(DoubleSolenoid.Value.kOff);
    }

    public void slideForward() {
        slider.set(DoubleSolenoid.Value.kForward);
    }

    public void slideBackward() {
        slider.set(DoubleSolenoid.Value.kReverse);
    }

    public void slideStop() {
        slider.set(DoubleSolenoid.Value.kOff);
    }

    public double currentThrottleValue() {
        return stick.getThrottle();
    }
}

