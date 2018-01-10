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

    public static int leftMotorFrontPort = 1;
    public static int leftMotorRearPort = 2;
    public static int rightMotorFrontPort = 3;
    public static int rightMotorRearPort= 4;
    //public static int pitcherPort = 5;

    public static Spark frontLeftMotor = new Spark(leftMotorFrontPort);
    public static Spark rearLeftMotor = new Spark(leftMotorRearPort);
    public static Spark frontRightMotor = new Spark(rightMotorFrontPort);
    public static Spark rearRightMotor = new Spark(rightMotorRearPort);
    //public static Victor captainHook = new Victor(captainHookPort);

    public static Gyro gyro = new ADXRS450_Gyro();
        //public static Accelerometer accelerometer = new ADXL362(Accelerometer.Range.k4G);
        //public static Accelerometer accelerometer = new BuiltInAccelerometer(Accelerometer.Range.k4G); TEST
        //public static double kp = 0.03;
}
