package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.DcMotor;
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
        armSwing.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
        this.telemetry = telemetry;
    }


    public void dunkHighPos(){
        armSwing.setRunMode(Motor.RunMode.PositionControl);
        armSwing.setTargetPosition(RobotConfig.OuttakeConstants.armSwingHighDunkPos);
        armSwing.set(.005);
    }

    public void dunkLowPos(){
        armSwing.setRunMode(Motor.RunMode.PositionControl);
        armSwing.setTargetPosition(RobotConfig.OuttakeConstants.armSwingLowDunkPos);
        armSwing.set(.25);

    }
    public boolean hasLowDunked(){
        return Math.abs(armSwing.getCurrentPosition()-RobotConfig.OuttakeConstants.armSwingLowDunkPos)<150;
    }
    public boolean hasDunked(){
        return Math.abs(armSwing.getCurrentPosition()-RobotConfig.OuttakeConstants.armSwingHighDunkPos)<150;
    }


    public void stop(){
        armSwing.set(0);
    }

    public void goDown(){
        armSwing.setRunMode(Motor.RunMode.PositionControl);
        armSwing.setTargetPosition(RobotConfig.OuttakeConstants.armSwingDefaultPos);
        armSwing.set(.1);
    }
    public boolean isDown(){
        return Math.abs(armSwing.getCurrentPosition()-RobotConfig.OuttakeConstants.armSwingDefaultPos)<150;
    }
    public void setBrake(){
        armSwing.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
    }

    public void setFloat(){
        armSwing.setZeroPowerBehavior(Motor.ZeroPowerBehavior.FLOAT);
    }

    public void setLowPower(){
        armSwing.set(.12);
    }

    @Override
    public void periodic(){
        telemetry.addData("Dunk Pos", armSwing.getCurrentPosition());
    }



}
