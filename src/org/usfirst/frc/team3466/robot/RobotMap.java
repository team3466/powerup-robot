package org.usfirst.frc.team3466.robot;

import edu.wpi.first.wpilibj.ADXL362;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Victor;
import edu.wpi.first.wpilibj.interfaces.Accelerometer;
import edu.wpi.first.wpilibj.interfaces.Gyro;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */

public class RobotMap {
    public static int joystick = 0;
    //public static int pitcherBtn = 2;

    public static int leftMotorPort = 0;
    public static int rightMotorPort = 1;
    //public static int pitcherPort = 5;s

    public static Spark leftMotor = new Spark(leftMotorPort);
    public static Spark rightMotor = new Spark(rightMotorPort);
    //public static Victor captainHook = new Victor(captainHookPort);

    public static Gyro gyro = new ADXRS450_Gyro();
        //public static Accelerometer accelerometer = new ADXL362(Accelerometer.Range.k4G);
        //public static Accelerometer accelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G); TEST
        //public static double kp = 0.03;
}
