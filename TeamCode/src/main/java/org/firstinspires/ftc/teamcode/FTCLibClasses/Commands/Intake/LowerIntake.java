package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

public class LowerIntake extends CommandBase {


    private IntakeSubsystem intake;

    public LowerIntake(IntakeSubsystem intake){
        this.intake = intake;
    }


    @Override
    public void execute(){
        intake.moveIntakeDown();
    }

    @Override
    public boolean isFinished(){
        return intake.isVerticalMotionDone();
    }
}
