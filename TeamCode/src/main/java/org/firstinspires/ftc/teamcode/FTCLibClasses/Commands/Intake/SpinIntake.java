package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class SpinIntake extends CommandBase {

    private IntakeSubsystem intake;
    private boolean hasCorrectSample;
    private Timing.Timer timer;


    public SpinIntake(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
        timer = new Timing.Timer(150 , TimeUnit.MILLISECONDS);
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
                timer.start();
            case WRONG_SAMPLE:
                intake.spinWheelsDown();
            case NO_SAMPLE:
                intake.spinWheelsUp();
        }

    }

    @Override
    public boolean isFinished(){
        return hasCorrectSample&&timer.done();
    }

    @Override
    public void end(boolean interrupted){
        intake.stopIntakeWheels();
    }
}
