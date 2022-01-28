package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.*;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.ctre.phoenix.motorcontrol.can.TalonFX;

public class ShooterWithVelocity extends SubsystemBase {

// **********************************************
// Class Variables
// **********************************************

// **********************************************
// Instance Variables
// **********************************************

// shooter at 90% is -18000 rpm
private double velocitySpeed = 9000;
private double aimerSpeed = 0.5;
//make sure we have the right motors for falcon
private TalonFX leftShooterMotor;
private TalonFX rightShooterMotor;
private double velocityToSpeedPercentage;
private CANSparkMax neoAimer; /**maybe neo aimer*/
//private Encoder lEncoder, rEncoder;
// if you want to know math here it is
// RPM / 180 = the percent of power being used
// 1250rpm = 6.25%
// 1250rpm * 16 = 20000
// 6.25% * 16 = 100%
// 100% = 20000
//starting from 0 you will be able to increase the speed 16 times
private double speedInterval = 1250;
private double averageRPM;
public NetworkTable shooterTable = NetworkTableInstance.getDefault().getTable(this.getClass().getSimpleName()); // shooter speed
    public NetworkTableEntry entryShooterPercentage = shooterTable.getEntry("Shooter percentage"); //shooter speed in % form
    public NetworkTableEntry entryShooterSpeed = shooterTable.getEntry("Shooter speed"); //shooter speed
    public NetworkTableEntry entryLeftShooterVelocity = shooterTable.getEntry("Left Shooter Velocity"); //shooter speed
    public NetworkTableEntry entryRightShooterVelocity = shooterTable.getEntry("Right Shooter Velocity"); //shooter speed
    public NetworkTableEntry entryAverageShooterVelocity = shooterTable.getEntry("Average Shooter Velocity"); //shooter velocity

// **********************************************
// Constructors
// **********************************************

public ShooterWithVelocity(){
    //todo change this depending on the robot
    leftShooterMotor = new TalonFX(5); //need can id
    rightShooterMotor = new TalonFX(2); // need can id make make one backwards
    //lEncoder = new Encoder(0, 1); // need channels from electrical
    //rEncoder = new Encoder(2, 3); // need channels from electrical
    neoAimer = new CANSparkMax(0, MotorType.kBrushless); // need id from elecectrical 

}




// **********************************************
// Instance Methods
// **********************************************

//all three motions I think are needed for shooter so far
    public void activateShooter(){
        // todo:make speed variable
       leftShooterMotor.set(ControlMode.Position, -velocitySpeed);
       rightShooterMotor.set(ControlMode.Position, velocitySpeed); //check

      
    }

    public void increaseSpeed(){
        velocitySpeed += speedInterval;
        if (velocitySpeed >= 20000){
            velocitySpeed = 20000;
        } else if (velocitySpeed <= 1250){
            velocitySpeed = speedInterval;
        }
    }
    public void decreaseSpeed(){
        velocitySpeed -= speedInterval;
        if (velocitySpeed <= -20000){
            velocitySpeed = -20000;
        } else if (velocitySpeed >= -1250){
            velocitySpeed = -speedInterval;
        }
    }


    public void reverseShooter(){
        averageRPM = (leftShooterMotor.getSelectedSensorVelocity() + rightShooterMotor.getSelectedSensorVelocity()) / 2;
        velocityToSpeedPercentage = averageRPM / 180;
        // todo needs to be tested, might not work
        leftShooterMotor.set(ControlMode.Position, velocitySpeed);
        rightShooterMotor.set(ControlMode.Position, -velocitySpeed); 
    }

    public void aimerDown(){
        neoAimer.set(-aimerSpeed);
    }

    public void aimerUp(){
        neoAimer.set(aimerSpeed);
    }

    public void stopShooter(){
        leftShooterMotor.set(ControlMode.Position, 0);
        rightShooterMotor.set(ControlMode.Position, 0); 
    }

    public void stopAimer (){
        neoAimer.set(0);
    }

    @Override
    public void periodic() {
        // should print out:
        entryShooterSpeed.setDouble(velocitySpeed); //motor RPM
        entryShooterPercentage.setDouble(velocityToSpeedPercentage); //motor in percent power
        entryLeftShooterVelocity.setDouble(leftShooterMotor.getSelectedSensorVelocity()); //left shooter RPM
        entryRightShooterVelocity.setDouble(rightShooterMotor.getSelectedSensorVelocity()); //right shooter RPM
        entryAverageShooterVelocity.setDouble(averageRPM); //average shooter RPM
    }

    @Override
    public void register() {
        // TODO Auto-generated method stub
        super.register();
    }

    @Override
    public void simulationPeriodic() {
        // should print out:
        entryShooterSpeed.setDouble(velocitySpeed); //motor RPM
        entryShooterPercentage.setDouble(velocityToSpeedPercentage); //motor in percent power
        entryLeftShooterVelocity.setDouble(leftShooterMotor.getSelectedSensorVelocity()); //left shooter RPM
        entryRightShooterVelocity.setDouble(rightShooterMotor.getSelectedSensorVelocity()); //right shooter RPM
        entryAverageShooterVelocity.setDouble(averageRPM); //average shooter RPM
    }

} 
