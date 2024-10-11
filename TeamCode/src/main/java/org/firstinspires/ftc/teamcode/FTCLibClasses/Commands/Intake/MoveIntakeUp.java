package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

public class MoveIntakeUp extends CommandBase {

    private IntakeSubsystem intake;

    public MoveIntakeUp(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        intake.moveIntakeUp();
    }

    @Override
    public boolean isFinished(){
        return intake.isVerticalMotionDone();
    }
}
