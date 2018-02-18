package org.usfirst.frc.team3466.robot.subsystems;


import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3466.robot.RobotMap;

import static org.usfirst.frc.team3466.robot.RobotMap.climber;

public class Climber extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Spark climber = RobotMap.climber;

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void forwardClimb() {
        climber.set(0.8);
    }

    public void reverseClimb() {
        climber.set(-0.8);
    }

    public void stopClimb() {
        climber.set(0);
    }
}

