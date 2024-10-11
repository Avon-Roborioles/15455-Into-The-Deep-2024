package org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems;

import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.hardware.motors.Motor;
import com.arcrobotics.ftclib.hardware.motors.MotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Test.TeleOpDriveCommand;
import org.firstinspires.ftc.teamcode.RobotConfig;
// ADD MORE COMMENTS FOR FUTURE REFERENCE

public class LiftSubsystem extends SubsystemBase {

    int startPos;
    MotorEx armLift;
    private Telemetry telemetry;


    public LiftSubsystem(HardwareMap hMap, Telemetry telemetry){
        armLift = new MotorEx(hMap, RobotConfig.OuttakeConstants.armLiftName);
        armLift.setRunMode(Motor.RunMode.PositionControl);
        armLift.set(1);


        armLift.setInverted(false);
        armLift.setPositionTolerance(20);
        //armLift.setZeroPowerBehavior(Motor.ZeroPowerBehavior.BRAKE);
        this.telemetry = telemetry;
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

    @Override
    public void periodic(){
        telemetry.addData("Lift Position", armLift.getCurrentPosition());
    }

    public void goToHighDunk(){
        armLift.setRunMode(Motor.RunMode.PositionControl);
        armLift.setTargetPosition(RobotConfig.OuttakeConstants.armLiftHighDunkPos);
        armLift.set(1);
    }

    public boolean finishedHighDunk(){
        return Math.abs(armLift.getCurrentPosition()-RobotConfig.OuttakeConstants.armLiftHighDunkPos)<150;
    }
    




}