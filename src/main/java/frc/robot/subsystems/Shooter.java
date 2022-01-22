package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
//import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import com.ctre.phoenix.motorcontrol.*;
//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.motorcontrol.Spark;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
// Class Variables
// **********************************************

// **********************************************
// Instance Variables
// **********************************************
private double shooterSpeed = 0.5;
private double aimerSpeed = 0.5;
//make sure we have the right motors for falcon
private TalonFX rightShooterMotor, leftShooterMotor;
private CANSparkMax neoAimer; /**maybe neo aimer*/
private Encoder lEncoder, rEncoder;

// **********************************************
// Constructors
// **********************************************

public Shooter(){
    leftShooterMotor = new TalonFX(1); //need can id
    rightShooterMotor = new TalonFX(2); // need can id make make one backwards
    lEncoder = new Encoder(0, 1); // need channels from electrical
    rEncoder = new Encoder(2, 3); // need channels from electrical
    neoAimer = new CANSparkMax(0, MotorType.kBrushless); // need id from elecectrical 
}



// **********************************************
// Instance Methods
// **********************************************

//all three motions I think are needed for shooter so far
    public void activateShooter(){
        // todo:make speed variable
       leftShooterMotor.set(ControlMode.PercentOutput, shooterSpeed);
       rightShooterMotor.set(ControlMode.PercentOutput, -shooterSpeed); //check
      
    }

    public void reverseShooter(){
        leftShooterMotor.set(ControlMode.PercentOutput, -shooterSpeed);
        rightShooterMotor.set(ControlMode.PercentOutput, shooterSpeed); 
    }

    public void aimerDown(){
        neoAimer.set(-aimerSpeed);
    }

    public void aimerUp(){
        neoAimer.set(aimerSpeed);
    }

    public void stopShooter(){
        leftShooterMotor.set(ControlMode.PercentOutput, 0);
        rightShooterMotor.set(ControlMode.PercentOutput, 0); 
    }

    public void stopAimer (){
        neoAimer.set(0);
    }

    @Override
    public void periodic() {
        // TODO Auto-generated method stub
        super.periodic();
    }

    @Override
    public void register() {
        // TODO Auto-generated method stub
        super.register();
    }

    @Override
    public void simulationPeriodic() {
        // TODO Auto-generated method stub
        super.simulationPeriodic();
    }
    
} 
