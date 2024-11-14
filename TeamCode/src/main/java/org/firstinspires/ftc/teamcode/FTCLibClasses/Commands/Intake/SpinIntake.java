package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.SpinIntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class SpinIntake extends CommandBase {

    private SpinIntakeSubsystem intake;
    private boolean detectsCorrectSample;
    private Timing.Timer timer;
    private boolean correctSampleInIntake=false;
    private boolean spinFinished = false;


    public SpinIntake(SpinIntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
        timer = new Timing.Timer(150 , TimeUnit.MILLISECONDS);
    }

    @Override
    public void initialize(){
        detectsCorrectSample = false;
        correctSampleInIntake =false;
    }






    @Override
    public void execute(){
        intake.spinWheelsUp();
        SpinIntakeSubsystem.SampleState state = intake.hasCorrectSample();
        detectsCorrectSample = state.correctSample;

        if (!correctSampleInIntake) {
            switch (state) {
                case YELLOW_SAMPLE:
                case CORRESPONDING_SAMPLE:
                    correctSampleInIntake = true;
                    break;
                case WRONG_SAMPLE:
                    intake.spinWheelsDown();
                    break;
                case NO_SAMPLE:
                    intake.spinWheelsUp();
                    break;
            }
        }
        if (correctSampleInIntake&&detectsCorrectSample){
            intake.stopIntakeWheels();
        }

    }

    @Override
    public boolean isFinished(){
        return detectsCorrectSample ;
    }

    @Override
    public void end(boolean interrupted){
        intake.stopIntakeWheels();
    }
}
