package org.usfirst.frc.team3466.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.Button;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import org.usfirst.frc.team3466.robot.commands.climber.ForwardClimbCommand;
import org.usfirst.frc.team3466.robot.commands.climber.ReverseClimbCommand;
import org.usfirst.frc.team3466.robot.commands.drive.DriveArcadeInverseCommand;
import org.usfirst.frc.team3466.robot.commands.elevator.LowerElevatorCommand;
import org.usfirst.frc.team3466.robot.commands.elevator.RaiseElevatorCommand;
import org.usfirst.frc.team3466.robot.commands.extender.LowerExtenderCommand;
import org.usfirst.frc.team3466.robot.commands.extender.RaiseExtenderCommand;
import org.usfirst.frc.team3466.robot.commands.slider.SlideBackwardCommand;
import org.usfirst.frc.team3466.robot.commands.slider.SlideForwardCommand;
//import edu.wpi.first.wpilibj.command.Command;

public class OI {

    //// CREATING BUTTONS
    // One type of button is a joystick button which is any button on a joystick.
    // You create one by telling it which joystick it's on and which button
    // number it is.
    public Joystick stick = new Joystick(RobotMap.joystick);


    Button button1 = new JoystickButton(stick, 1);
    Button button2 = new JoystickButton(stick, 2);
    Button button3 = new JoystickButton(stick, 3);
    Button button4 = new JoystickButton(stick,4);
    Button button5 = new JoystickButton(stick, 5);
    Button button6 = new JoystickButton(stick, 6);
    Button button7 = new JoystickButton(stick, 7);
    Button button11 = new JoystickButton(stick, 11);
    Button button12 = new JoystickButton(stick, 12);
    //public int povAngle = stick.getPOV();
    //Button button14 = new JoystickButton(stick, 14);

        public OI(){

            button1.whileHeld(new ForwardClimbCommand());
            //button2.whileHeld(new ReverseClimbCommand());
            button3.whileHeld(new RaiseElevatorCommand());
            button4.whileHeld(new LowerElevatorCommand());
            button5.whileHeld(new RaiseExtenderCommand());
            button6.whileHeld(new LowerExtenderCommand());
            button2.whileHeld(new DriveArcadeInverseCommand());
            //button11.whileHeld(new SlideForwardCommand());
            //button12.whileHeld(new SlideBackwardCommand());
            //pitcherBtn.whenPressed(new FuelShoot());
      }

    // There are a few additional built in buttons you can use. Additionally,
    // by subclassing Button you can create custom triggers and bind those to
    // commands the same as any other Button.

    //// TRIGGERING COMMANDS WITH BUTTONS
    // Once you have a button, it's trivial to bind it to a button in one of
    // three ways:

    // Start the command when the button is pressed and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenPressed(new ExampleCommand());

    // Run the command while the button is being held down and interrupt it once
    // the button is released.
    // button.whileHeld(new ExampleCommand());

    // Start the command when the button is released  and let it run the command
    // until it is finished as determined by it's isFinished method.
    // button.whenReleased(new ExampleCommand());

}