// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.commands.ExampleCommand;
// imports for commands
import frc.robot.commands.ShooterCommands.CommandActivateShooter;
import frc.robot.commands.ShooterCommands.CommandReverseShooter;
import frc.robot.commands.ShooterCommands.CommandStopShooter;
import frc.robot.commands.ShooterCommands.CommandAimerUp;
import frc.robot.commands.ShooterCommands.CommandStopAimer;
import frc.robot.commands.ShooterCommands.CommandAimerDown;
import frc.robot.commands.ShooterCommands.CommandIncreaseSpeed;
import frc.robot.commands.ShooterCommands.CommandDecreaseSpeed;

import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.Shooter;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...

  // button inputs
   private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();
  private final XboxController xboxController = new XboxController(0);
  private final JoystickButton rightBumper = new JoystickButton(xboxController, XboxController.Button.kRightBumper.value);
  private final JoystickButton aButton = new JoystickButton(xboxController, XboxController.Button.kA.value);
  private final JoystickButton yButton = new JoystickButton(xboxController, XboxController.Button.kY.value);
  // double check that these buttons don't overlap with other systems
  private final JoystickButton xButton = new JoystickButton(xboxController, XboxController.Button.kX.value);
  private final JoystickButton bButton = new JoystickButton(xboxController, XboxController.Button.kB.value);
  // triggers
  private final JoystickButton leftBumper = new JoystickButton(xboxController, XboxController.Button.kLeftBumper.value);
  //private final JoystickButton rightTrigger = new JoystickButton(xboxController, XboxController.Axis.kRightTrigger.value);
  private Shooter shooter = new Shooter();

  // commands
  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);
  private final CommandActivateShooter commandActivateShooter = new CommandActivateShooter(shooter);
  private final CommandStopShooter commandStopShooter = new CommandStopShooter(shooter);
  private final CommandReverseShooter commandReverseShooter = new CommandReverseShooter(shooter);
  private final CommandAimerUp commandAimerUp = new CommandAimerUp(shooter);
  private final CommandAimerDown commandAimerDown = new CommandAimerDown(shooter);
  private final CommandStopAimer commandStopAimer = new CommandStopAimer(shooter);
  private final CommandIncreaseSpeed commandIncreaseSpeed = new CommandIncreaseSpeed(shooter);
  private final CommandDecreaseSpeed CommandDecreaseSpeed = new CommandDecreaseSpeed(shooter);

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
  }


  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    // todo makesure variables dont overlap
    // Shooter:
      rightBumper.whileHeld(commandReverseShooter)
                 .whenReleased(commandStopShooter);
      leftBumper.whileHeld(commandActivateShooter)
                  .whenReleased(commandStopShooter);
    // Aimer
      aButton.whenPressed(commandAimerDown)
             .whenReleased(commandStopAimer);
      yButton.whenPressed(commandAimerUp)
             .whenReleased(commandStopAimer);
    // Speed for shooter
      xButton.whenPressed(commandIncreaseSpeed);
      bButton.whenPressed(CommandDecreaseSpeed);
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
