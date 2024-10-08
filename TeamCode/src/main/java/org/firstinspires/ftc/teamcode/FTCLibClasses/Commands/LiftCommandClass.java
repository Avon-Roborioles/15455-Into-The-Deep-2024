package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;

public class LiftCommandClass extends ParallelCommandGroup {
    LiftSubsystem armLift;
    ArmSubsystem armSwing;
    GamepadEx armPad;
    int numTimes = 0;

    public LiftCommandClass(LiftSubsystem armLift,ArmSubsystem armSwing){
        this.armSwing = armSwing;
        this.armLift = armLift;
        addRequirements(armLift);
        addRequirements(armSwing);

    }

    @Override
    public void initialize(){
        armLift.stop();
        armSwing.stop();


    }

    @Override
    public void execute(){
//        armLift.run();
        armLift.buttonHold(5*(numTimes+1));
        armSwing.start(2*(numTimes+1));


    }
    @Override
    public void end(boolean b){
        armLift.stop();
        armSwing.stop();
    }

    @Override
    public boolean isFinished(){
        return Math.abs(armLift.getValue())>50;
        }

    }


