package org.firstinspires.ftc.teamcode.FTCLibClasses.Commands;

import com.arcrobotics.ftclib.command.CommandBase;
import com.arcrobotics.ftclib.gamepad.GamepadEx;

import org.firstinspires.ftc.teamcode.FTCLibClasses.Subsystems.DriveSubsystem;

public class TeleOpDriveCommand extends CommandBase {
    private DriveSubsystem drive;

    public TeleOpDriveCommand(DriveSubsystem drive){
        this.drive = drive;
    }

    @Override
    public void execute(){
        drive.driverControlDrive();
    }


}
