package org.usfirst.frc.team3466.robot.subsystems;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3466.robot.RobotMap;

import static org.usfirst.frc.team3466.robot.RobotMap.extender;

public class Extender extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Spark extender = RobotMap.extender;
    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void raiseExtender() {
        extender.set(1.0);
    }

    public void lowerExtender() {
        extender.set(-0.5);
    }

    public void stopExtender() {
        extender.set(0);
    }
}

