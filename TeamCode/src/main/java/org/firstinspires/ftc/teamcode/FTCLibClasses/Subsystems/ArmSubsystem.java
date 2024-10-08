package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class ArmSubsystem extends SubsystemBase {
    int startPos;
    MotorEx armSwing;


    public ArmSubsystem(HardwareMap hMap){
        armSwing = new MotorEx(hMap, LiftSubsystem.LiftNames.armOuttakeName);
        armSwing.setRunMode(Motor.RunMode.PositionControl);
        armSwing.set(1);


        armSwing.setInverted(false);
        armSwing.setPositionTolerance(20);
        armSwing.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }


    public void start(int value){
        armSwing.set(1);
        armSwing.setTargetPosition(value+startPos);

    }
    public void stop(){
        armSwing.set(0);
    }



}
