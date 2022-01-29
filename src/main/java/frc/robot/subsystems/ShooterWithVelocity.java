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

//with encoders
// shooter at 90% is -18000 rpm
private double velocitySpeed = 10000;
private double velocityToSpeedPercentage;
// if you want to know math here it is
// RPM / 180 = the percent of power being used
// 1250rpm = 6.25%
// 1250rpm * 16 = 20000
// 6.25% * 16 = 100%
// 100% = 20000
//starting from 0 you will be able to increase the speed 16 times
private double speedIntervalWithVelocity = 1250;
private double averageRPM;

//without encoders
private double shooterSpeed = 0.5;
private double shooterPercentage;
private double speedInterval = 0.05;

private double aimerSpeed = 0.5;
//make sure we have the right motors for falcon
private TalonFX leftShooterMotor;
private TalonFX rightShooterMotor;


private CANSparkMax neoAimer; /**maybe neo aimer*/

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
       
    //    leftShooterMotor.set(ControlMode.PercentOutput, -shooterSpeed);
    //    rightShooterMotor.set(ControlMode.PercentOutput, shooterSpeed);

    }

    public void increaseSpeed(){
        velocitySpeed += speedIntervalWithVelocity;
        if (velocitySpeed >= 20000){
            velocitySpeed = 20000;
        }

        // if (shooterSpeed >= 1){
        //     shooterSpeed = 1;
        // }
    }
    public void decreaseSpeed(){
        velocitySpeed -= speedIntervalWithVelocity;
        if (velocitySpeed <= -20000){
            velocitySpeed = -20000;
        }

        // if (shooterSpeed <= 0){
        //     shooterSpeed = -speedInterval;
        // }
    }


    public void reverseShooter(){
        // todo needs to be tested, might not work
        leftShooterMotor.set(ControlMode.Position, velocitySpeed);
        rightShooterMotor.set(ControlMode.Position, -velocitySpeed); 

        // leftShooterMotor.set(ControlMode.PercentOutput, shooterSpeed);
        // rightShooterMotor.set(ControlMode.PercentOutput, -shooterSpeed);
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

        // leftShooterMotor.set(ControlMode.PercentOutput, 0);
        // rightShooterMotor.set(ControlMode.PercentOutput, 0); 
    }

    public void stopAimer (){
        neoAimer.set(0);
    }

    @Override
    public void periodic() {
        averageRPM = (leftShooterMotor.getSelectedSensorVelocity() + rightShooterMotor.getSelectedSensorVelocity()) / 2;
        velocityToSpeedPercentage = averageRPM / 200;
        // should print out:
        entryShooterSpeed.setDouble(velocitySpeed); //motor RPM
        entryShooterPercentage.setDouble(velocityToSpeedPercentage); //motor in percent power
        entryLeftShooterVelocity.setDouble(leftShooterMotor.getSelectedSensorVelocity()); //left shooter RPM
        entryRightShooterVelocity.setDouble(rightShooterMotor.getSelectedSensorVelocity()); //right shooter RPM
        entryAverageShooterVelocity.setDouble(averageRPM); //average shooter RPM
        
        //without encoders
        shooterPercentage = shooterSpeed * 100;
        entryShooterPercentage.setDouble(shooterPercentage);

        
    }

    @Override
    public void register() {
        // TODO Auto-generated method stub
        super.register();
    }

    @Override
    public void simulationPeriodic() {
        averageRPM = (leftShooterMotor.getSelectedSensorVelocity() + rightShooterMotor.getSelectedSensorVelocity()) / 2;
        velocityToSpeedPercentage = averageRPM / 200;
        // should print out:
        entryShooterSpeed.setDouble(velocitySpeed); //motor RPM
        entryShooterPercentage.setDouble(velocityToSpeedPercentage); //motor in percent power
        entryLeftShooterVelocity.setDouble(leftShooterMotor.getSelectedSensorVelocity()); //left shooter RPM
        entryRightShooterVelocity.setDouble(rightShooterMotor.getSelectedSensorVelocity()); //right shooter RPM
        entryAverageShooterVelocity.setDouble(averageRPM); //average shooter RPM

        //without encoders
        shooterPercentage = shooterSpeed * 100;
        entryShooterPercentage.setDouble(shooterPercentage);
    }

} 
