package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.LiftSubsystem;

public class LiftCommandClass extends ParallelCommandGroup {
    LiftSubsystem armLift;
    GamepadEx armPad;
    int numTimes = 0;

    public LiftCommandClass(LiftSubsystem armLift) {
        this.armLift = armLift;
        addRequirements(armLift);

    }

    @Override
    public void initialize() {


    }

    @Override
    public void execute() {
//        armLift.run();
        armLift.goToHighDunk();


    }

    @Override
    public void end(boolean b) {
        armLift.stop();

    }

    @Override
    public boolean isFinished() {

        return armLift.finishedHighDunk();

    }
}


