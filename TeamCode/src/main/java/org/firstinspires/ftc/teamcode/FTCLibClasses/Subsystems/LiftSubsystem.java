package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.RobotConfig;
// ADD MORE COMMENTS FOR FUTURE REFERENCE

public class LiftSubsystem extends SubsystemBase {

    int startPos;
    MotorEx armLift;



    public LiftSubsystem(HardwareMap hMap){
        armLift = new MotorEx(hMap, RobotConfig.OuttakeConstants.armLiftName);
        armLift.setRunMode(Motor.RunMode.PositionControl);
        armLift.set(1);


        armLift.setInverted(false);
        armLift.setPositionTolerance(20);
        //armLift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }


   public void buttonHold(int value){
       armLift.set(1);
       armLift.setTargetPosition(value+startPos);

   }

   public int getValue(){return armLift.getCurrentPosition()-startPos;}

    public void run(){
        armLift.set(1);
    }

    public void stop(){
        armLift.set(0);
    }
    public void reset(){
        startPos = armLift.getCurrentPosition();
    }
    




}