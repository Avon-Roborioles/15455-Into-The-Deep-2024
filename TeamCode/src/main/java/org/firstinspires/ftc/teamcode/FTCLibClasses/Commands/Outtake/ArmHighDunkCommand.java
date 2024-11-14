package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Outtake;

import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.ArmSubsystem;

public class ArmHighDunkCommand extends CommandBase {

    private ArmSubsystem armSubsystem;

    public ArmHighDunkCommand(ArmSubsystem armSubsystem){
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
