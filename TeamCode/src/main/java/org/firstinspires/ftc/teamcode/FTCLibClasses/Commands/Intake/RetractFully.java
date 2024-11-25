package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands.Intake;

import com.arcrobotics.ftclib.command.Command;
import com.arcrobotics.ftclib.command.CommandBase;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.Intake.ExtendMotorSubsystem;

public class RetractFully extends CommandBase {

    private ExtendMotorSubsystem extendMotorSubsystem;

    public RetractFully(ExtendMotorSubsystem extendMotorSubsystem){
        this.extendMotorSubsystem = extendMotorSubsystem;
    }



    @Override
    public void execute(){
        extendMotorSubsystem.moveFullyIn();
    }

    @Override
    public boolean isFinished(){
        return extendMotorSubsystem.isFullyIn();
    }

    @Override
    public void end(boolean b){
        extendMotorSubsystem.stopExtend();
    }

    public RetractFully copy(){
        return new RetractFully(extendMotorSubsystem);
    }
}
