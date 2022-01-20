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

public class Shooter extends SubsystemBase {
// **********************************************
// Instance Variables
// **********************************************
private double shooterSpeed = 0.5;
private double aimerSpeed = 0.5;
//make sure we have the right motors for falcon
private TalonFX rightShooter, leftShooter;
private CANSparkMax neoAimer; /**maybe neo aimer*/
private Encoder lEncoder, rEncoder;

// **********************************************
// Constructors
// **********************************************

public Shooter(){
    leftShooter = new TalonFX(1); //need can id
    rightShooter = new TalonFX(2); // need can id make make one backwards
    lEncoder = new Encoder(0, 1); // need channels from electrical
    rEncoder = new Encoder(2, 3); // need channels from electrical
    neoAimer = new CANSparkMax(0, MotorType.kBrushless); // need id from elecectrical 
}



// **********************************************
// Instance Methods
// **********************************************

//all three motions I think are needed for shooter so far
    public void forward(){
        // todo:make speed variable
        leftShooter.set(ControlMode.PercentOutput, shooterSpeed);
      rightShooter.set(ControlMode.PercentOutput, -shooterSpeed); //check
      
    }

    public void backwards(){
        leftShooter.set(ControlMode.PercentOutput, -shooterSpeed);
        rightShooter.set(ControlMode.PercentOutput, shooterSpeed); 
    }

    public void aimerBack(){
        neoAimer.set(-aimerSpeed);
    }

    public void aimerForward(){
        neoAimer.set(aimerSpeed);
    }

    public void stop(){
        leftShooter.set(ControlMode.PercentOutput, 0);
        rightShooter.set(ControlMode.PercentOutput, 0); 
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
