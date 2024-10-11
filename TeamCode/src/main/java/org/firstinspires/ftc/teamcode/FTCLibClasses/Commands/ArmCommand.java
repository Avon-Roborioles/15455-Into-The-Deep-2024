package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.command.ParallelCommandGroup;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;

public class ArmCommand extends CommandBase {

    private ArmSubsystem armSubsystem;

    public ArmCommand(ArmSubsystem armSubsystem){
        this.armSubsystem = armSubsystem;
        addRequirements(armSubsystem);
    }

    @Override
    public void execute(){
        armSubsystem.dunkHighPos();
    }

    @Override
    public void end(boolean b){
        armSubsystem.stop();
    }

    @Override
    public boolean isFinished(){
        return armSubsystem.hasDunked();
    }
}
