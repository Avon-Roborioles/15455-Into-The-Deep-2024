package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.util.Timing;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.VerticalIntakeSubsystem;

import java.util.concurrent.TimeUnit;

public class MoveIntakeDown extends CommandBase {

    private VerticalIntakeSubsystem intake;
    private Timing.Timer timer;

    public MoveIntakeDown(VerticalIntakeSubsystem intake){
        this.intake = intake;
        addRequirements(intake);
        timer = new Timing.Timer(500, TimeUnit.MILLISECONDS);


    }

    @Override
    public void initialize(){
        intake.moveIntakeDown();
        timer.start();
    }

    @Override
    public boolean isFinished(){
        return timer.done();

    }

    public MoveIntakeDown copy(){
        return new MoveIntakeDown(intake);
    }


}
