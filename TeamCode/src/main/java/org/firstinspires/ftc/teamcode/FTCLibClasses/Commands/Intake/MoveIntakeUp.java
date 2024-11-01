package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class MoveIntakeUp extends CommandBase {

    private IntakeSubsystem intake;
    private boolean isRunning=false;
    private Timing.Timer timer;

    public MoveIntakeUp(IntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);

        timer = new Timing.Timer(1000, TimeUnit.MILLISECONDS);
    }

    public String getTelemetry(){
        if (isRunning){
            return "Move Intake Up is running";
        }
        return "Move Intake Up is not running";
    }

    @Override
    public void initialize(){
        intake.moveIntakeUp();
        isRunning = true;
        timer.start();
    }

    @Override
    public boolean isFinished(){
        isRunning = !timer.done();

        return timer.done();
    }
}
