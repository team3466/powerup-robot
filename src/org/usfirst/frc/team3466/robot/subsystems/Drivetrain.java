package org.usfirst.frc.team3466.robot.subsystems;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import org.usfirst.frc.team3466.robot.OI;
import org.usfirst.frc.team3466.robot.RobotMap;
import org.usfirst.frc.team3466.robot.commands.drive.DriveArcadeCommand;

import static org.usfirst.frc.team3466.robot.RobotMap.gyro;


public class Drivetrain extends Subsystem {

    // Put methods for controlling this subsystem
    // here. Call these from Commands.
    private static boolean InverseDriveOn = false;
    private DifferentialDrive differentialDrive;
    public static OI oi;
    public static Encoder enc = new Encoder(0, 1, false, Encoder.EncodingType.k4X);
    //Encoder enc;

    public void initDefaultCommand() {
        // TODO: Set the default command, if any, for a subsystem here. Example:
        //    setDefaultCommand(new MySpecialCommand());
        setDefaultCommand(new DriveArcadeCommand());
    }

    public static void setInverseDriveOn(boolean inverseDriveOn) {
        InverseDriveOn = inverseDriveOn;
    }

    boolean alreadyExecuted = false;
    public void initDrive() {
        if (!alreadyExecuted) {
            oi = new OI();
            oi.stick = new Joystick(RobotMap.joystick);
            differentialDrive = new DifferentialDrive(RobotMap.leftMotor, RobotMap.rightMotor);

            //double distance = enc.getDistance();
            enc.setDistancePerPulse(0.05235833); //6-inch
            //enc.setDistancePerPulse(0.0349); //4-inch with 360 degree accuracy
            enc.setReverseDirection(true);
            enc.reset();
            alreadyExecuted = true;
        }

    }

    public void autoDrive(double speed, double direction) {
        differentialDrive.arcadeDrive(speed, direction, false);
    }

    public void teleopDrive(double Y, double X) {
        if (InverseDriveOn){
            X = X;
            Y = -Y;
        }
        differentialDrive.arcadeDrive(Y, -X, true);
    }

    public void driveDistanceForward(float desiredDistance, float desiredDirection) {

        enc.reset();
        float maxSpeedAdjustment = .01f;
        float lastSpeed = 0;
        float minimumSpeed = .35f;
        float maxSpeed = 1f;
        float speed = 0;

        float directionError = 0;
        float directionAdjustment = 0;
        float lastDirection = 0;
        float KpDirection = .05f;
        float maxDirection = 0.4f;
        float direction = 0;
        float stoppingDistance = 42;

        //enc.reset();
        float currentDistance = 0;
        Timer sampleRateTimer;
        sampleRateTimer = new Timer();
        sampleRateTimer.reset();
        sampleRateTimer.start();
        double sampleRate = 0.01;


        while (currentDistance < desiredDistance-stoppingDistance) {

            if (sampleRateTimer.get() > sampleRate) {
                sampleRateTimer.reset();
                currentDistance = (float) enc.getDistance();

                speed = lastSpeed + maxSpeedAdjustment;
                if (speed > maxSpeed) {
                    speed = maxSpeed;
                }


                lastSpeed = speed;

                //directionError = (desiredDirection - (float)-gyro.getAngle()); Old Robot
                directionError = (-desiredDirection + (float)-gyro.getAngle()); //New Robot

                directionAdjustment = directionError * KpDirection;

                direction = lastDirection + directionAdjustment;
                if (direction > maxDirection) {
                    direction = maxDirection;
                }
                if (direction < -maxDirection) {
                    direction = -maxDirection;
                }

                autoDrive(speed, direction);

            }
        }
        while (currentDistance < desiredDistance) {
            if (sampleRateTimer.get() > sampleRate) {
                sampleRateTimer.reset();
                currentDistance = (float) enc.getDistance();
                //differentialDrive.arcadeDrive(0, (heading-angle)*Kp); //Drive forward .8 speed.

                speed = lastSpeed - (maxSpeedAdjustment*20);

                if (speed < minimumSpeed) {
                    speed = minimumSpeed;
                }

                lastSpeed = speed;

                //directionError = (desiredDirection - (float)-gyro.getAngle()); Old Robot
                directionError = (-desiredDirection + (float)-gyro.getAngle()); //New Robot

                directionAdjustment = directionError * KpDirection * speed;
                     /*   if (directionAdjustment > maxDirectionAdjustment){
                            directionAdjustment = maxDirectionAdjustment;
                        }
                        if (directionAdjustment < -maxDirectionAdjustment){
                            directionAdjustment = -maxDirectionAdjustment;
                        }
                    */
                direction = lastDirection + directionAdjustment;
                if (direction > maxDirection) {
                    direction = maxDirection;
                }
                if (direction < -maxDirection) {
                    direction = -maxDirection;
                }

                //lastDirection = direction;
                //System.out.println(direction);
                //System.out.println(gyro.getAngle());

                autoDrive(speed, direction);
                System.out.println(speed);

            }
        }
        autoDrive(0, 0);
        //enc.reset();
    }

    public void turnDirectionCCW(float newAngle){

        //enc.reset();
        float stoppingAngle = -27;
        //float stoppingAngle = -18; //Old Robot
        newAngle -= stoppingAngle;
        float turnError = 0;
        float turnAdjustment = 0;
        float maxTurnAdjustment = .01f;
        float lastTurn = 0;
        float minimumTurn = 0;
        float KpTurn = .025f;
        float maxTurn = 1f;
        float turn = 0;

        while (newAngle < -gyro.getAngle()) {

                turnError = (-newAngle + (float)-gyro.getAngle());

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
                autoDrive(0, -0.35);

        }
        autoDrive(0, 0);
    }

    public void turnDirectionCW(float newAngle){

        //enc.reset();
        float stoppingAngle = 40f; //This will change on new robot.
        newAngle -= stoppingAngle;
        float turnError = 0;
        float turnAdjustment = 0;
        float maxTurnAdjustment = .01f;
        float lastTurn = 0;
        float minimumTurn = 0;
        float KpTurn = .025f;
        float maxTurn = 1f;
        float turn = 0;

        while (newAngle > -gyro.getAngle()) {

            turnError = (-newAngle + (float)-gyro.getAngle());

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
            autoDrive(0, 0.5);

        }
        autoDrive(0, 0);
    }
}

