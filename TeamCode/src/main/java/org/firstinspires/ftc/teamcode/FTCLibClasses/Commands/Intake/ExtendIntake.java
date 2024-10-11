package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

public class ExtendIntake extends CommandBase {

    private IntakeSubsystem intake;

    public ExtendIntake(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void execute(){
        intake.extendMotorOutFully();
        if (intake.intakeClearedBucket()){
            //intake.moveIntakeDown();
        }
    }
    @Override
    public void end(boolean b){
        intake.stopExtend();
    }

    @Override
    public boolean isFinished(){
        return intake.extendFinished();
    }
}
