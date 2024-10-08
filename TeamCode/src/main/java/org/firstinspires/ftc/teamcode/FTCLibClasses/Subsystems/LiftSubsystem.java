package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
// ADD MORE COMMENTS FOR FUTURE REFERENCE

public class LiftSubsystem extends SubsystemBase {
    public static class LiftNames{
        public static final String armLiftname = "armLift";
        public static final String armOuttakeName = "armOuttake";
    }
    int startPos;
    MotorEx armLift;
    GamepadEx armPad;



    public LiftSubsystem(HardwareMap hMap){
        armLift = new MotorEx(hMap, LiftNames.armLiftname);
        armLift.setRunMode(Motor.RunMode.PositionControl);
        armLift.set(1);


        armLift.setInverted(false);
        armLift.setPositionTolerance(20);
        armLift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);






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