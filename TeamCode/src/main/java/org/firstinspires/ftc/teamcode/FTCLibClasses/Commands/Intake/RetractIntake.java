package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;


public class RetractIntake extends CommandBase {
    private IntakeSubsystem intake;

    public RetractIntake(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        intake.moveIntakeUp();
    }

    @Override
    public void execute(){
        if (intake.isVerticalMotionDone()) {
            intake.retractMotorFully();
        }else {
            intake.retractToClearPos();
        }
    }

    @Override
    public boolean isFinished(){
        return intake.extendFinished();
    }
}
