package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

public class MoveIntakeDown extends CommandBase {

    private IntakeSubsystem intake;

    public MoveIntakeDown(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        intake.moveIntakeDown();
    }

    @Override
    public boolean isFinished(){
        return intake.isVerticalMotionDone();
    }


}
