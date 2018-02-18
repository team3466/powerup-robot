package org.usfirst.frc.team3466.robot.subsystems;


import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.command.Subsystem;
import org.usfirst.frc.team3466.robot.RobotMap;
import static org.usfirst.frc.team3466.robot.RobotMap.elevator;

public class Elevator extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    Victor elevator = RobotMap.elevator;

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
    }

    public void raiseElevator() {
        elevator.set(0.8);
    }

    public void lowerElevator() {
        elevator.set(-0.5);
    }

    public void stopElevator() {
        elevator.set(0);
    }
}

