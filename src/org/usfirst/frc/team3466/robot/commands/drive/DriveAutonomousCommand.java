package org.usfirst.frc.team3466.robot.commands.drive;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team3466.robot.Robot;


public class DriveAutonomousCommand extends Command {
    public DriveAutonomousCommand() {
        // Use requires() here to declare subsystem dependencies
        // eg. requires(chassis);
        requires(Robot.drivetrain);
    }


    /**
     * The initialize method is called just before the first time
     * this Command is run after being started.
     */
    @Override
    protected void initialize() {
        Robot.drivetrain.initDrive();
    }


    /**
     * The execute method is called repeatedly when this Command is
     * scheduled to run until this Command either finishes or is canceled.
     */
    @Override
    protected void execute() {
        Robot.drivetrain.driveDistanceForward(120-16, 0);
        //Timer.delay(2);
        //Robot.drivetrain.turnDirectionCCW(-90);
        //Robot.drivetrain.driveDistanceForward(120-16, 90);
        //Timer.delay(2);
        //Robot.drivetrain.driveDistanceForward(252-4, -90);
        //Timer.delay(2);
        //Robot.drivetrain.turnDirectionCCW(-180);
        //Timer.delay(2);
        //Robot.drivetrain.driveDistanceForward(58-4, -180);
        //Timer.delay(8);
        //Robot.drivetrain.turnDirectionCW(-90);
        //Timer.delay(2);
        //Robot.drivetrain.driveDistanceForward(72-4, -90);
        //Timer.delay(2);
        //Robot.drivetrain.turnDirectionCW(0);
        //Timer.delay(2);
        //Robot.drivetrain.driveDistanceForward(162-4, 0);
        //Timer.delay(4);
        //Robot.drivetrain.turnDirectionCW(45);
        //Timer.delay(4);
        //Robot.drivetrain.driveDistanceForward(27-4, 45);
        //Timer.delay(4);
        //Robot.drivetrain.turnDirectionCCW(0);
        //Timer.delay(4);
        //Robot.drivetrain.driveDistanceForward(48-4, 0);

        //Timer.delay(.2);
        //Robot.drivetrain.driveDistanceForward(120, -90);
        //Robot.drivetrain.autoDrive(0.5, -0.5);
        //while(true);
        //Timer.delay(3);
        //isFinished();
     }


     /**
     * <p>
     * Returns whether this command is finished. If it is, then the command will be removed and
     * {@link #end()} will be called.
     * </p><p>
     * It may be useful for a team to reference the {@link #isTimedOut()}
     * method for time-sensitive commands.
     * </p><p>
     * Returning false will result in the command never ending automatically. It may still be
     * cancelled manually or interrupted by another command. Returning true will result in the
     * command executing once and finishing immediately. It is recommended to use
     * {@link edu.wpi.first.wpilibj.command.InstantCommand} (added in 2017) for this.
     * </p>
     *
     * @return whether this command is finished.
     * @see Command#isTimedOut() isTimedOut()
     */
    @Override
    protected boolean isFinished() {
        // TODO: Make this return true when this Command no longer needs to run execute()
        return true;
    }


    /**
     * Called once when the command ended peacefully; that is it is called once
     * after {@link #isFinished()} returns true. This is where you may want to
     * wrap up loose ends, like shutting off a motor that was being used in the
     * command.
     */
    @Override
    protected void end() {
        Robot.drivetrain.autoDrive(0, 0) ;
    }


    /**
     * <p>
     * Called when the command ends because somebody called {@link #cancel()} or
     * another command shared the same requirements as this one, and booted it out. For example,
     * it is called when another command which requires one or more of the same
     * subsystems is scheduled to run.
     * </p><p>
     * This is where you may want to wrap up loose ends, like shutting off a motor that was being
     * used in the command.
     * </p><p>
     * Generally, it is useful to simply call the {@link #end()} method within this
     * method, as done here.
     * </p>
     */
    @Override
    protected void interrupted() {
        Robot.drivetrain.autoDrive(0, 0) ;
        super.interrupted();
    }
}
