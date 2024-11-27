package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.RobotConfig;
import org.opencv.core.Mat;

public class ArmSubsystem extends SubsystemBase {
    int startPos;
    MotorEx armSwing;
    private Telemetry telemetry;


    public ArmSubsystem(HardwareMap hMap, Telemetry telemetry){
        armSwing = new MotorEx(hMap, RobotConfig.OuttakeConstants.armOuttakeName);
        armSwing.setRunMode(Motor.RunMode.PositionControl);


        armSwing.setInverted(false);
        armSwing.setPositionTolerance(20);
        armSwing.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.telemetry = telemetry;
    }


    public void dunkHighPos(){
        armSwing.setRunMode(Motor.RunMode.PositionControl);
        armSwing.setTargetPosition(RobotConfig.OuttakeConstants.armSwingHighDunkPos);
        armSwing.set(.2);
    }

    public boolean hasDunked(){
        return Math.abs(armSwing.getCurrentPosition()-RobotConfig.OuttakeConstants.armSwingHighDunkPos)<150;
    }

    public void start(int value){
        armSwing.set(1);
        armSwing.setTargetPosition(value+startPos);

    }
    public void stop(){
        armSwing.set(0);
    }

    public void goDown(){
        armSwing.setRunMode(Motor.RunMode.PositionControl);
        armSwing.setTargetPosition(RobotConfig.OuttakeConstants.armSwingDefaultPos);
        armSwing.set(.25);
    }
    public boolean isDown(){
        return Math.abs(armSwing.getCurrentPosition()-RobotConfig.OuttakeConstants.armSwingDefaultPos)<50;
    }

    @Override
    public void periodic(){
        telemetry.addData("Dunk Pos", armSwing.getCurrentPosition());
    }



}
