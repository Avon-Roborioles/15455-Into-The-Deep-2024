package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;


public class RetractIntake extends CommandBase {
    private final IntakeSubsystem intake;

    public RetractIntake(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        //intake.moveIntakeUp();
    }

    @Override
    public void execute(){
       intake.retractMotorFully();
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
