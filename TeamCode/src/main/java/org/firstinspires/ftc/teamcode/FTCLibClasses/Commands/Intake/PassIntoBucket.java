package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.IntakeSubsystem;
import org.firstinspires.ftc.teamcode.RobotConfig;

import java.util.concurrent.TimeUnit;


public class PassIntoBucket extends CommandBase {

    private IntakeSubsystem intake;
    private Timing.Timer timer;

    public PassIntoBucket(IntakeSubsystem intake){
        this.intake = intake;
        timer = new Timing.Timer(2, TimeUnit.SECONDS);
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
}
