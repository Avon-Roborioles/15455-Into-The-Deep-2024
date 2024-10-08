package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.CommandBase;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;

public class DriveActionCommand extends CommandBase {
    private Action action;
    private DriveSubsystem drive;
    private boolean isFinished;
    private TelemetryPacket telemetryPacket;

    public DriveActionCommand(Action action, DriveSubsystem drive){
        addRequirements(drive);
    }


    @Override
    public void initialize(){
        isFinished = false;
        telemetryPacket = new TelemetryPacket();
    }
    @Override
    public void execute(){
        isFinished = action.run(telemetryPacket);
    }

    @Override
    public boolean isFinished(){
        return isFinished;
    }
}
