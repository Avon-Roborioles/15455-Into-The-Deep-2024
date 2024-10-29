package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

public class SpinIntake extends CommandBase {

    private IntakeSubsystem intake;
    private boolean hasCorrectSample;


    public SpinIntake(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
    }

    @Override
    public void initialize(){
        hasCorrectSample = false;
    }
    @Override
    public void execute(){
        intake.spinWheelsUp();
        switch(intake.hasCorrectSample()){
            case YELLOW_SAMPLE:
            case CORRESPONDING_SAMPLE:
                hasCorrectSample = true;
                intake.stopIntakeWheels();
            case WRONG_SAMPLE:
                intake.spinWheelsDown();
            case NO_SAMPLE:
                intake.spinWheelsUp();
        }
    }

    @Override
    public boolean isFinished(){
        return true;
    }

    @Override
    public void end(boolean interrupted){
        intake.stopIntakeWheels();
    }
}
