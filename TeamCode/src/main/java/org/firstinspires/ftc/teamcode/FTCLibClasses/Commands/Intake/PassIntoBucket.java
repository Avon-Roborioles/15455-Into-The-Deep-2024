package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.SpinIntakeSubsystem;

import java.util.concurrent.TimeUnit;


public class PassIntoBucket extends CommandBase {

    private SpinIntakeSubsystem intake;
    private Timing.Timer timer;

    public PassIntoBucket(SpinIntakeSubsystem intake){
        this.intake = intake;
        timer = new Timing.Timer(1500, TimeUnit.MILLISECONDS);
    }

    @Override
    public void initialize(){
        timer.start();
    }

    @Override
    public void execute(){
        intake.spinWheelsDown();
    }

    @Override
    public void end(boolean b){
        intake.stopIntakeWheels();
    }

    @Override
    public boolean isFinished(){
        return timer.done();
    }

    public PassIntoBucket copy(){
        return new PassIntoBucket(intake);
    }
}
